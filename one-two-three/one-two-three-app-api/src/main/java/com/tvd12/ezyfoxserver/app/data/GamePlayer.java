package com.tvd12.ezyfoxserver.app.data;

import com.tvd12.gamebox.entity.Player;
import lombok.Getter;
import lombok.Setter;

public class GamePlayer extends Player {

    @Setter
    @Getter
    private GameAnswer answer;

    public GamePlayer(String name) {
        super(name);
    }
}
