package me.verwelius.skygames.game.state;

import me.verwelius.skygames.config.Config;
import me.verwelius.skygames.game.GameController;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class EndingState extends GameState {

    private final Player winner;

    protected EndingState(GameController controller, Config config, Player winner) {
        super(controller, config);
        this.winner = winner;

        winner.setGameMode(GameMode.CREATIVE);
        winner.playSound(winner, Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1.0f, 1.0f);
        winner.playSound(winner, Sound.ENTITY_FIREWORK_ROCKET_TWINKLE_FAR, 1.0f, 1.0f);

        controller.schedule(() -> {
            controller.updateState(new WaitingState(controller, config));
        }, config.endingConfig.ticksUntilNewGame);
    }



}
