package me.verwelius.skygames.game.state;

import me.verwelius.skygames.config.Config;
import me.verwelius.skygames.game.GameController;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WaitingState extends GameState {

    private final Set<Location> availableCapsules;
    private final Map<Player, Location> usingCapsule;

    private BukkitTask countdownTask;
    private Integer secondsLeft;

    public WaitingState(GameController controller, Config config) {
        super(controller, config);
        this.availableCapsules = config.waitingConfig.spawnCapsules;
        this.usingCapsule = new HashMap<>();
    }

    @Override
    public void addPlayer(Player player) {
        super.addPlayer(player);
        player.setGameMode(GameMode.SURVIVAL);

        Location capsule = availableCapsules.iterator().next();

        availableCapsules.remove(capsule);
        usingCapsule.put(player, capsule);

        buildCapsule(capsule, Material.GLASS);

        capsule.setWorld(config.gameWorld);
        player.teleport(capsule);

        int playersAmount = getPlayers().size();
        config.waitingConfig.countdown.forEach((required, time) -> {
            if(required <= playersAmount) {
                secondsLeft = Math.min(
                        secondsLeft == null ?
                        Integer.MAX_VALUE : secondsLeft,
                        time
                );
            }
        });

        if((countdownTask == null || countdownTask.isCancelled()) && secondsLeft != null) {
            countdownTask = controller.schedule(() -> {

                if(secondsLeft == null) {
                    countdownTask.cancel();
                    return;
                }

                secondsLeft--;
                makeSound();
                updateLevel();

                if(secondsLeft == 0) {
                    countdownTask.cancel();
                    PlayingState nextState = new PlayingState(controller, config, getPlayers());
                    controller.updateState(nextState);
                }

            }, 20, 20);
        }

        updateLevel();
    }

    private void makeSound() {
        getPlayers().forEach(p -> {
            if(secondsLeft == null) return;
            p.playSound(
                    p, secondsLeft == 0 ?
                    Sound.BLOCK_NOTE_BLOCK_BELL : Sound.BLOCK_NOTE_BLOCK_BASS,
                    1.0f, 1.2f
            );
        });
    }

    private void updateLevel() {
        getPlayers().forEach(p -> {
            if(secondsLeft == null || secondsLeft == 0) {
                p.setLevel(0);
                p.setExp(0);
            } else {
                p.setLevel(secondsLeft);
                p.setExp(0.999f);
            }
        });
    }

    @Override
    public void removePlayer(Player player) {
        super.removePlayer(player);
        Location capsule = usingCapsule.get(player);

        usingCapsule.remove(player);
        availableCapsules.add(capsule);

        buildCapsule(capsule, Material.AIR);

        int playersAmount = getPlayers().size();
        if(playersAmount < Collections.min(config.waitingConfig.countdown.keySet())) secondsLeft = null;
    }

    private void buildCapsule(Location loc, Material material) {
        World world = config.gameWorld;

        for(int x = -2; x <= 2; x++) {
            for(int y = -2; y <= 2; y++) {
                for(int z = -2; z <= 2; z++) {
                    if(tripleMax(x, y, z) > 1) {
                        Block block = world.getBlockAt(loc.clone().add(x, y, z));
                        block.setType(material);
                    }
                }
            }
        }
    }

    private int tripleMax(int x, int y, int z) {
        return Math.max(Math.abs(x), Math.max(Math.abs(y), Math.abs(z)));
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

}
