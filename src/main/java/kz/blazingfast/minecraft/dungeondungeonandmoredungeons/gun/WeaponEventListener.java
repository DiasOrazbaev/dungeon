package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import static kz.blazingfast.minecraft.dungeondungeonandmoredungeons.DungeonDungeonAndMoreDungeons.log;

public class WeaponEventListener implements Listener {

    int clickCounter = 0;

    @EventHandler
    public void onShoot(PlayerInteractEvent event) {
        try {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();
                if (WeaponLogic.isGun(itemInMainHand)) {
                    WeaponLogic.shoot(itemInMainHand, event.getPlayer());
                    WeaponLogic.displayGunInterface(itemInMainHand);
                }
            }
        } catch (Exception e) {
            log("Achtung! @EventHandler onShoot(PlayerInteractEvent) exception occurred: " + e.getMessage());
        }
    }


    @EventHandler
    public void onReload(PlayerSwapHandItemsEvent event) {
        try {
            ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();
            if (WeaponLogic.isGun(itemInMainHand)) {
                WeaponLogic.reload(itemInMainHand);
                WeaponLogic.displayGunInterface(itemInMainHand);
                event.setCancelled(true);
            }
        } catch (Exception e) {
            log("Achtung! @EventHandler onReload(PlayerSwapHandItemsEvent) exception occurred: " + e.getMessage());
        }
    }

    @EventHandler
    public void onScope(PlayerInteractEvent event) {
        try {
            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();
                if (WeaponLogic.isGun(itemInMainHand)) {
                    clickCounter++;
                    if (!(WeaponLogic.scope(itemInMainHand, event.getPlayer(), clickCounter))) {
                        clickCounter = 0;
                    }

                    if (clickCounter > 2) {
                        clickCounter = 0;
                    }
                }
            }
        } catch (Exception e) {
            log("Achtung! @EventHandler onScope(PlayerInteractEvent) exception occurred: " + e.getMessage());
        }
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        try {
            Player p = event.getPlayer();
            int i = event.getNewSlot();
            int c = event.getPreviousSlot();
            ItemStack item1 = event.getPlayer().getInventory().getItem(i);
            ItemStack item2 = event.getPlayer().getInventory().getItem(c);

            if (item1 != item2) {
                p.removePotionEffect(PotionEffectType.SLOW);
                clickCounter = 0;
            }
        } catch (Exception e) {
            log("Achtung! @EventHandler onPlayerItemHeld(PlayerItemHeldEvent) exception occurred: " + e.getMessage());
        }
    }
}
