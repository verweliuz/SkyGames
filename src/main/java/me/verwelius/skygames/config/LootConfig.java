package me.verwelius.skygames.config;

import org.bukkit.loot.LootTables;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.List;

@ConfigSerializable
public class LootConfig {

    public List<LootTables> lootTables = List.of(
            LootTables.RUINED_PORTAL,
            LootTables.ANCIENT_CITY,
            LootTables.PILLAGER_OUTPOST,
            LootTables.VILLAGE_WEAPONSMITH,
            LootTables.BURIED_TREASURE,
            LootTables.BASTION_TREASURE,
            LootTables.VILLAGE_TAIGA_HOUSE
    );

}
