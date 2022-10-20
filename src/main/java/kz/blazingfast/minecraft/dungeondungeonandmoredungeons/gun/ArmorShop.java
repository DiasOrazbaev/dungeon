package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ArmorShop {

    public static void handle(Gun gun, Player player) {
        ItemStack gunItem = new ItemStack(gun.getMaterial());
        ItemMeta meta = gunItem.getItemMeta();
        meta.getPersistentDataContainer().set(
                NamespacedKey.fromString("gun_name"),
                PersistentDataType.STRING,
                gun.name()
        );
        meta.getPersistentDataContainer().set(
                NamespacedKey.fromString("bullets_count"),
                PersistentDataType.INTEGER,
                gun.getBulletCount()
        );
        meta.setDisplayName(String.format("%s %d|%d", gun.getName(), gun.getBulletCount(), gun.getBulletCount()));

        gunItem.setItemMeta(meta);
        player.getInventory().addItem(gunItem);
    }
}
