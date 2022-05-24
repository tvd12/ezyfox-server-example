package com.tvd12.ezyfoxserver.app.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.app.data.GamePlayer;
import com.tvd12.ezyfoxserver.app.data.GameRoom;
import com.tvd12.gamebox.manager.PlayerManager;
import com.tvd12.gamebox.manager.RoomManager;
import com.tvd12.gamebox.manager.SynchronizedPlayerManager;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.concurrent.*;

@EzySingleton
@AllArgsConstructor
public class GameService {

    private final RoomManager<GameRoom> roomRoomManager;
    private final PlayerManager<GamePlayer> playerManager;
    private final Map<GameRoom, ScheduledFuture<?>> scheduledFutureByRoom =
        new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(
        Runtime.getRuntime().availableProcessors() * 2
    );
    private static final int NEW_GAME_DELAY_TIME = 15;

    public GamePlayer removePlayerByName(String name) {
        synchronized (playerManager) {
            return playerManager.removePlayer(name);
        }
    }

    public GamePlayer getPlayerByName(String name) {
        GamePlayer player;
        synchronized (playerManager) {
            player = playerManager.getPlayer(name);
            if (player == null) {
                player = new GamePlayer(name);
                playerManager.addPlayer(player);
            }
        }
        return player;
    }

    public GameRoom getAvailableRoom() {
        GameRoom room;
        synchronized (roomRoomManager) {
            room = (GameRoom) roomRoomManager.getRoom(r ->
                r.getPlayerManager().available()
            );
            if (room == null) {
                room = GameRoom.builder()
                    .playerManager(new SynchronizedPlayerManager<>(2))
                    .build();
                roomRoomManager.addRoom(room);
            }
        }
        return room;
    }

    public GameRoom getRoomById(long roomId) {
        synchronized (roomRoomManager) {
            return (GameRoom) roomRoomManager.getRoom(roomId);
        }
    }

    public void cancelStartNewGameSchedule(GameRoom room) {
        ScheduledFuture<?> future = scheduledFutureByRoom.remove(room);
        if (future != null) {
            future.cancel(false);
        }
    }

    public void scheduleToStartNewGame(GameRoom room, Runnable callback) {
        ScheduledFuture<?> future = scheduler.schedule(
            () -> {
                synchronized (room) {
                    room.reset();
                }
                callback.run();
            },
            NEW_GAME_DELAY_TIME,
            TimeUnit.SECONDS
        );
        scheduledFutureByRoom.put(room, future);
    }
}
