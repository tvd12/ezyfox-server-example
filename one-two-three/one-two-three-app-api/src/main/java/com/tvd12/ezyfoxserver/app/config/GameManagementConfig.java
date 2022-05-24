package com.tvd12.ezyfoxserver.app.config;

import com.tvd12.ezyfox.bean.annotation.EzyConfigurationBefore;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.app.data.GameAnswer;
import com.tvd12.ezyfoxserver.app.data.GamePlayer;
import com.tvd12.gamebox.entity.NormalRoom;
import com.tvd12.gamebox.manager.PlayerManager;
import com.tvd12.gamebox.manager.RoomManager;
import com.tvd12.gamebox.manager.SimplePlayerManager;
import com.tvd12.gamebox.manager.SimpleRoomManager;

import java.util.Comparator;

@EzyConfigurationBefore
public class GameManagementConfig {

    @EzySingleton
    public PlayerManager<GamePlayer> playerManger() {
        return new SimplePlayerManager<>();
    }

    @EzySingleton
    public RoomManager<NormalRoom> roomRoomManager() {
        return new SimpleRoomManager<>();
    }

    @EzySingleton
    public Comparator<GameAnswer> gameAnswerComparator() {
        return new GameAnswer.ValueComparator();
    }
}
