package dev.anhcraft.battle.customgamemode;

import dev.anhcraft.battle.api.arena.game.options.GameOptions;
import dev.anhcraft.battle.utils.LocationUtil;
import dev.anhcraft.confighelper.annotation.*;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

@Schema
public class CustomGameOptions extends GameOptions {
    @Key("boss.type")
    @Explanation("Boss type")
    @PrettyEnum
    @Validation(notNull = true)
    private EntityType bossType;

    @Key("boss.location")
    @Explanation("Boss spawn point")
    @Validation(notNull = true)
    private String bossLocation;

    @Key("boss.health")
    @Explanation("Boss health points")
    private int bossHealth;

    @NotNull
    public EntityType getBossType() {
        return bossType;
    }

    @NotNull
    public Location getBossLocation() {
        return LocationUtil.fromString(bossLocation);
    }

    public int getBossHealth() {
        return bossHealth;
    }
}
