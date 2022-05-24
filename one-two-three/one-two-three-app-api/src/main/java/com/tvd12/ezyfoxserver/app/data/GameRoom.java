package com.tvd12.ezyfoxserver.app.data;

import com.tvd12.gamebox.constant.RoomStatus;
import com.tvd12.gamebox.entity.NormalRoom;
import com.tvd12.gamebox.entity.Player;
import lombok.Getter;
import lombok.Setter;

public class GameRoom extends NormalRoom {

    @Setter
    @Getter
    private GamePlayer winner;

    public void reset() {
        this.winner = null;
        this.status = RoomStatus.WAITING;
    }

    public GameRoom(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends NormalRoom.Builder<Builder> {
        @Override
        protected GameRoom newProduct() {
            return new GameRoom(this);
        }

        @Override
        public GameRoom build() {
            return (GameRoom) super.build();
        }
    }
}
