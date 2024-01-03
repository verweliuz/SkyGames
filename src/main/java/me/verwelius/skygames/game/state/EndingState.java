package me.verwelius.skygames.game.state;

import me.verwelius.skygames.config.Config;
import me.verwelius.skygames.game.GameController;
import org.bukkit.entity.Player;

public class EndingState extends GameState {

    private final Player winner;

    protected EndingState(GameController controller, Config config, Player winner) {
        super(controller, config);
        this.winner = winner;
    }



}
