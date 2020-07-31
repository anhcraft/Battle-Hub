package dev.anhcraft.battle.customgamemode;

import dev.anhcraft.battle.api.arena.game.Mode;
import dev.anhcraft.confighelper.ConfigSchema;
import org.bukkit.plugin.java.JavaPlugin;

public final class MainClass extends JavaPlugin {
    public static Mode MODE;

    @Override
    public void onEnable() {
        CustomController controller = new CustomController();
        MODE = Mode.register(new Mode("custom_gm", ConfigSchema.of(CustomGameOptions.class)));
        MODE.setController(controller);
        getServer().getPluginManager().registerEvents(controller, this);
    }

    @Override
    public void onDisable() {

    }
}
