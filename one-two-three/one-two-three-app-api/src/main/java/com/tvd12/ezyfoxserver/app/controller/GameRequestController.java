package com.tvd12.ezyfoxserver.app.controller;

import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.app.constant.Commands;
import com.tvd12.ezyfoxserver.app.constant.Errors;
import com.tvd12.ezyfoxserver.app.data.GameAnswer;
import com.tvd12.ezyfoxserver.app.data.GamePlayer;
import com.tvd12.ezyfoxserver.app.data.GameRoom;
import com.tvd12.ezyfoxserver.app.request.AnswerRequest;
import com.tvd12.ezyfoxserver.app.response.GameFinishedResponse;
import com.tvd12.ezyfoxserver.app.response.JoinGameResponse;
import com.tvd12.ezyfoxserver.app.service.GameService;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import com.tvd12.gamebox.constant.IRoomStatus;
import com.tvd12.gamebox.constant.RoomStatus;
import com.tvd12.gamebox.manager.PlayerManager;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@EzyRequestController
public class GameRequestController extends EzyLoggable {

    private final GameService gameService;
    private final EzyResponseFactory responseFactory;
    private final Comparator<GameAnswer> gameAnswerComparator;

    @EzyDoHandle(Commands.JOIN_GAME)
    public void joinGame(EzyUser user) {
        GamePlayer player = gameService.getPlayerByName(user.getName());
        if (player.getCurrentRoomId() > 0) {
            throw new EzyBadRequestException(
                Errors.PLAYER_HAS_JOIN_A_ROOM,
                "player has join a room"
            );
        }
        GameRoom room = gameService.getAvailableRoom();

        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (room) {
            room.addPlayer(player);
        }
        player.setCurrentRoomId(room.getId());
        joinGameResponse(room, user);
    }

    private void joinGameResponse(GameRoom room, EzyUser user) {
        PlayerManager<GamePlayer> roomPlayerManager = room.getPlayerManager();
        List<String> playerNames = roomPlayerManager.getPlayerNames();
        responseFactory.newObjectResponse()
            .command(Commands.JOIN_GAME)
            .data(new JoinGameResponse(room.getId(), playerNames))
            .user(user)
            .execute();
        responseFactory.newObjectResponse()
            .command(Commands.ANOTHER_PLAYER_JOIN_GAME)
            .param("otherPlayer", user.getName())
            .usernames(playerNames)
            .user(user, true)
            .execute();

        if (!roomPlayerManager.available()) {
            responseFactory.newObjectResponse()
                .command(Commands.GAME_STARTED)
                .usernames(playerNames)
                .execute();
        }
    }

    @EzyDoHandle(Commands.ANSWER)
    public void gamePlayerAnswer(EzyUser user, AnswerRequest request) {
        GamePlayer player = gameService.getPlayerByName(user.getName());
        if (player == null || player.getCurrentRoomId() <= 0) {
            throw new EzyBadRequestException(
                Errors.PLAYER_HAS_NOT_JOIN_A_ROOM,
                "player has not join a room"
            );
        }
        if (player.getAnswer() != null) {
            throw new EzyBadRequestException(
                Errors.PLAYER_HAS_ALREADY_ANSWER,
                "player has already answer"
            );
        }
        player.setAnswer(request.getAnswer());
        logger.info("player: {} answer: {}", player, request.getAnswer());

        GameRoom room = gameService.getRoomById(player.getCurrentRoomId());
        IRoomStatus roomStatus;
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (room) {
            roomStatus = room.getStatus();
        }
        if (roomStatus == RoomStatus.FINISHED) {
            throw new EzyBadRequestException(
                Errors.GAME_HAS_ALREADY_FINISHED,
                "game has already finished"
            );
        }

        PlayerManager<GamePlayer> roomPlayerManager = room.getPlayerManager();
        List<GamePlayer> answeredPlayers = roomPlayerManager
            .getPlayerList(p -> p.getAnswer() != null);
        if (answeredPlayers.size() == roomPlayerManager.getMaxPlayer()) {
            finishGame(room, answeredPlayers);
        }
    }

    private void finishGame(
        GameRoom room,
        List<GamePlayer> answeredPlayers
    ) {
        synchronized (room) {
            // fix concurrent issue
            if (room.getStatus() == RoomStatus.FINISHED) {
                return;
            }
            room.setStatus(RoomStatus.FINISHED);
        }
        GamePlayer winner = Collections.max(
            answeredPlayers,
            (a, b) -> gameAnswerComparator.compare(a.getAnswer(), b.getAnswer())
        );
        synchronized (room) {
            room.setWinner(winner);
        }
        PlayerManager<GamePlayer> roomPlayerManager = room.getPlayerManager();
        responseFactory.newObjectResponse()
            .command(Commands.GAME_FINISHED)
            .data(new GameFinishedResponse(winner, answeredPlayers))
            .usernames(roomPlayerManager.getPlayerNames())
            .execute();
        gameService.scheduleToStartNewGame(room, () -> {
            answeredPlayers.forEach(it -> it.setAnswer(null));
            responseFactory.newObjectResponse()
                .command(Commands.GAME_STARTED)
                .usernames(roomPlayerManager.getPlayerNames())
                .execute();
            logger.info("new game of room: {} has started", room);
        });
        logger.info("game of room: {} has finished", room);
    }
}
