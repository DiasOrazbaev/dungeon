package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArmorShop {

    public static void handle(Gun gun, Player player) {

        ItemStack gunItem = new ItemStack(gun.getMaterial(), 1);
        ItemMeta meta = gunItem.getItemMeta();
        List<String> lore = new ArrayList<>();

        meta.getPersistentDataContainer().set(
                NamespacedKey.fromString("gun_name"),
                PersistentDataType.STRING,
                gun.name()
        );

        meta.getPersistentDataContainer().set(
                NamespacedKey.fromString("gun_magazine"),
                PersistentDataType.INTEGER,
                gun.getMagazine()
        );

        meta.getPersistentDataContainer().set(
                NamespacedKey.fromString("gun_ammo"),
                PersistentDataType.INTEGER,
                gun.getAmmo()
        );

        lore.add("ID: " + UUID.randomUUID());
        lore.add("Damage: " + gun.getDamage());
        lore.add("Bullets per magazine: " + gun.getMagazine());
        lore.add("Max ammo: " + gun.getAmmo());


        meta.setDisplayName(gun.getName());
        meta.setLore(lore);


        meta.setDisplayName(String.format(
                "%s %s|%s %s",
                "" + ChatColor.WHITE + gun.getName(),
                "" + ChatColor.WHITE + gun.getMagazine(),
                "" + ChatColor.GRAY + gun.getMagazine(),
                "" + ChatColor.DARK_GREEN + gun.getAmmo()));
        gunItem.setItemMeta(meta);
        player.getInventory().addItem(gunItem);
    }
}
