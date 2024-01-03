package me.verwelius.skygames.game.state;

import me.verwelius.skygames.config.Config;
import me.verwelius.skygames.game.GameController;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WaitingState extends GameState {

    private final Set<Location> availableCapsules;
    private final Map<Player, Location> usingCapsule;

    public WaitingState(GameController controller, Config config) {
        super(controller, config);
        this.availableCapsules = config.waitingConfig.spawnCapsules;
        this.usingCapsule = new HashMap<>();
    }

    @Override
    public void addPlayer(Player player) {
        super.addPlayer(player);
        Location capsule = availableCapsules.iterator().next();

        availableCapsules.remove(capsule);
        usingCapsule.put(player, capsule);

        buildCapsule(capsule, Material.GLASS);

        capsule.setWorld(config.gameWorld);
        player.teleport(capsule);
    }

    @Override
    public void removePlayer(Player player) {
        super.removePlayer(player);
        Location capsule = usingCapsule.get(player);

        usingCapsule.remove(player);
        availableCapsules.add(capsule);

        buildCapsule(capsule, Material.AIR);
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

}
