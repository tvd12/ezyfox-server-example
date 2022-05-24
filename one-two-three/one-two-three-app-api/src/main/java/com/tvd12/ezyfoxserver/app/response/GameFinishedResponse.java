package com.tvd12.ezyfoxserver.app.response;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfoxserver.app.data.GameAnswer;
import com.tvd12.ezyfoxserver.app.data.GamePlayer;
import com.tvd12.gamebox.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@EzyObjectBinding(read = false)
public class GameFinishedResponse {
    private String winner;
    private Map<String, GameAnswer> answerByPlayer;

    public GameFinishedResponse(
        GamePlayer winner,
        List<GamePlayer> players
    ) {
        this.winner = winner.getName();
        this.answerByPlayer = players
            .stream()
            .collect(
                Collectors.toMap(
                    Player::getName,
                    GamePlayer::getAnswer
                )
            );
    }
}
