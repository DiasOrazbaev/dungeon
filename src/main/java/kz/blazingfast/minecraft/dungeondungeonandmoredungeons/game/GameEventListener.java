package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import static kz.blazingfast.minecraft.dungeondungeonandmoredungeons.DungeonDungeonAndMoreDungeons.getInstance;
import static kz.blazingfast.minecraft.dungeondungeonandmoredungeons.DungeonDungeonAndMoreDungeons.log;

public class GameEventListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (FreezeMode.MEMBERS.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        try {
            Entity victim = event.getEntity();
            String VictimName = "", KillerName = "";

            if (victim instanceof Player) {
                VictimName = ((Player) victim).getDisplayName();

                if (victim.getLastDamageCause() instanceof EntityDamageByEntityEvent) {

                    EntityDamageByEntityEvent eEvent = (EntityDamageByEntityEvent) victim.getLastDamageCause();
                    KillerName = eEvent.getDamager().getType().getName().toLowerCase();

                    if (Bukkit.getServer().getPlayer(KillerName) != null) {
                        Bukkit.broadcastMessage(KillerName + " >> kill >> " + VictimName);

                        if (Game.roundCanBeEnd()) {
                            Game.roundForceWinCondition();
                        }
                    }
                }
            }
        } catch (Exception e) {
            log("Achtung! @EventHandler onEntityDeath(EntityDeathEvent) exception occurred: " + e.getMessage());
        }
    }

    @EventHandler
    public void afterDeath(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (Game.isMember(player.getName())) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getInstance(), () -> player.setGameMode(GameMode.SPECTATOR), 5);
        }
    }

}
