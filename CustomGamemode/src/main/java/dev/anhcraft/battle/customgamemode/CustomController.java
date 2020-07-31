package dev.anhcraft.battle.customgamemode;

import dev.anhcraft.battle.api.BattleApi;
import dev.anhcraft.battle.api.arena.game.GamePhase;
import dev.anhcraft.battle.api.arena.game.LocalGame;
import dev.anhcraft.battle.api.arena.game.Mode;
import dev.anhcraft.battle.api.arena.game.controllers.GameController;
import dev.anhcraft.battle.api.stats.natives.ExpStat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CustomController implements GameController, Listener {
    @Override
    public void onJoin(@NotNull Player player, @NotNull LocalGame game) {
        player.sendMessage(ChatColor.RED + "Attack the boss to win!");
        CustomGameOptions options = (CustomGameOptions) game.getArena().getGameOptions();
        if(game.getPlayerCount() == options.getMinPlayers()) {
            game.setPhase(GamePhase.PLAYING);
            Entity e = Objects.requireNonNull(options.getBossLocation().getWorld()).spawnEntity(options.getBossLocation(), options.getBossType());
            if(e instanceof LivingEntity) {
                ((LivingEntity) e).setHealth(options.getBossHealth());
            }
        }
    }

    @Override
    public void onEnd(@NotNull LocalGame game) {

    }

    @Override
    public @NotNull Mode getMode() {
        return MainClass.MODE;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        Player killer = e.getEntity().getKiller();
        if(killer != null) {
            LocalGame game = BattleApi.getInstance().getArenaManager().getGame(killer);
            if(game != null && game.getMode() == getMode()) {
                for(Player p : game.getPlayers().keySet()){
                    p.sendMessage(ChatColor.GREEN + killer.getName() + " killed the boss!");
                }
                Objects.requireNonNull(BattleApi.getInstance().getPlayerData(killer)).getStats().of(ExpStat.class).increase(killer, 1000);
                game.end();
            }
        }
    }
}
