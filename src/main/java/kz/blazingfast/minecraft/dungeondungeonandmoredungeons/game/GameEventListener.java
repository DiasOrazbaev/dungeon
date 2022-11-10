package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun.WeaponLogic;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
    public void onDeath(PlayerDeathEvent event) {
        try {
            Player player = event.getEntity().getPlayer();
            assert player != null;
            Player killer = event.getEntity().getKiller();
            assert killer != null;
            ItemStack itemInMainHand = killer.getInventory().getItemInMainHand();
            ItemMeta meta = itemInMainHand.getItemMeta();

            if (killer != null) {
                if (meta != null && meta.getLore() != null) {
                    if (WeaponLogic.isGun(itemInMainHand)) {
                        if (Game.isMember(player.getName()) && Game.isMember(killer.getName())) {
                            Member member = Game.getTeam(player.getName()).getMember(player.getName());
                            String memberName = player.getName();
                            Game.incrementMemberDeathStat(memberName);
                            Game.incrementMemberKillStat(killer.getName());
                            Game.getMemberTeam(player.getName()).killMember(member);
                            String playerColored;
                            if (Game.getMemberTeam(player.getName()).equals("defense")) {
                                playerColored = ChatColor.BLUE + player.getName();
                            } else {
                                playerColored = ChatColor.GOLD + player.getName();
                            }
                            String killerColored;
                            if (Game.getMemberTeam(killer.getName()).equals("defense")) {
                                killerColored = ChatColor.BLUE + killer.getName();
                            } else {
                                killerColored = ChatColor.GOLD + killer.getName();
                            }

                            String weaponColored = ChatColor.WHITE + " >> " + meta.getLore().get(2) + " >> ";
                            Bukkit.broadcastMessage(killerColored + weaponColored + playerColored);

                        }

                    }
                }
            } else {
                Game.incrementMemberDeathStat(player.getName());

                String playerColored;
                if (Game.getMemberTeam(player.getName()).equals("defense")) {
                    playerColored = ChatColor.BLUE + player.getName();
                } else {
                    playerColored = ChatColor.GOLD + player.getName();
                }

                String weaponColored = ChatColor.WHITE + " >< ";
                Bukkit.broadcastMessage(playerColored + weaponColored + playerColored);
            }
        } catch (Exception e) {
            log("Achtung! @EventHandler onDeath(PlayerDeathEvent) exception occurred: " + e.getMessage());
        } finally {
            if (Game.getAliveOnDefense() == 0 || Game.getAliveOnAttack() == 0) {
                Game.roundForceWinCondition();
            }
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
