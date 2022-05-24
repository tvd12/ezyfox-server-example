package com.tvd12.ezyfoxserver.app.controller;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfoxserver.app.data.GamePlayer;
import com.tvd12.ezyfoxserver.app.data.GameRoom;
import com.tvd12.ezyfoxserver.app.service.GameService;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractAppEventController;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.event.EzyUserRemovedEvent;
import com.tvd12.gamebox.manager.PlayerManager;
import lombok.AllArgsConstructor;

import static com.tvd12.ezyfoxserver.constant.EzyEventNames.USER_REMOVED;

@EzySingleton
@AllArgsConstructor
@EzyEventHandler(USER_REMOVED) // refer EzyEventType
public class UserExitRoomController
    extends EzyAbstractAppEventController<EzyUserRemovedEvent> {

    private final GameService gameService;

    @Override
    public void handle(EzyAppContext ctx, EzyUserRemovedEvent event) {
        EzyUser user = event.getUser();
        logger.info("one-two-three app: user: {} removed from room", user);
        GamePlayer player = gameService.removePlayerByName(user.getName());
        if (player == null || player.getCurrentRoomId() <= 0) {
            return;
        }
        GameRoom room = gameService.getRoomById(player.getCurrentRoomId());
        if (room == null) {
            return;
        }
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (room) {
            room.reset();
        }
        PlayerManager<GamePlayer> playerManager = room.getPlayerManager();
        playerManager.removePlayer(player);

        gameService.cancelStartNewGameSchedule(room);
        logger.info("cancel start new game schedule of room: {}", room);
    }
}
