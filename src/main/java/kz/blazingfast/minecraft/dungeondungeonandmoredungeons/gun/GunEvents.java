package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class GunEvents implements Listener {

    @EventHandler
    public void onShoot(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Gun gun = Gun.getGunFrom(event.getPlayer().getInventory().getItemInMainHand());
            event.getPlayer().sendMessage("pressed right click");
            if (gun != null) {
                gun.shoot(event.getPlayer().getInventory().getItemInMainHand(), event.getPlayer());
            }

        }
    }

    @EventHandler
    public void onReload(PlayerSwapHandItemsEvent event) {
        Gun gun = Gun.getGunFrom(event.getPlayer().getInventory().getItemInMainHand());
        if (gun != null) {
            gun.reload(event.getPlayer().getInventory().getItemInMainHand());
            event.setCancelled(true);
        }
    }
}
