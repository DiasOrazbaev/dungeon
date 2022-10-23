package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.decorator.Armor;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.decorator.ArmorHelmetDecorator;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.decorator.ArmorOnlyDecorator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class InventoryShopEventListener implements Listener {

    @EventHandler
    public void clickEvent(InventoryClickEvent i) {

        Player p = (Player) i.getWhoClicked();

        if (i.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GREEN + "Gun Shop")) {

            switch (Objects.requireNonNull(i.getCurrentItem()).getType()) {
                case BLACK_DYE -> {
                    p.closeInventory();
                    try {
                        Gun gun = Gun.valueOf("AK47");
                        handle(gun, p);
                        p.sendMessage(String.format("to %s given %s", p.getName(), gun.name()));
                    } catch (IllegalArgumentException e) {
                        p.sendMessage("Achtung! ArmorShopCMD exception occurred: Incorrect name of gun: " + e.getMessage());
                    }
                }
                case BLUE_DYE -> {
                    p.closeInventory();
                    try {
                        Gun gun = Gun.valueOf("M4A1S");
                        handle(gun, p);
                        p.sendMessage(String.format("to %s given %s", p.getName(), gun.name()));
                    } catch (IllegalArgumentException e) {
                        p.sendMessage("Achtung! ArmorShopCMD exception occurred: Incorrect name of gun: " + e.getMessage());
                    }
                }
                case GREEN_DYE -> {
                    p.closeInventory();
                    try {
                        Gun gun = Gun.valueOf("AWP");
                        handle(gun, p);
                        p.sendMessage(String.format("to %s given %s", p.getName(), gun.name()));
                    } catch (IllegalArgumentException e) {
                        p.sendMessage("Achtung! ArmorShopCMD exception occurred: Incorrect name of gun: " + e.getMessage());
                    }
                }
                case BROWN_DYE -> {
                    p.closeInventory();
                    try {
                        Gun gun = Gun.valueOf("GLOCK");
                        handle(gun, p);
                        p.sendMessage(String.format("to %s given %s", p.getName(), gun.name()));
                    } catch (IllegalArgumentException e) {
                        p.sendMessage("Achtung! ArmorShopCMD exception occurred: Incorrect name of gun: " + e.getMessage());
                    }
                }
                case LIGHT_BLUE_DYE -> {
                    p.closeInventory();
                    try {
                        Gun gun = Gun.valueOf("USP");
                        handle(gun, p);
                        p.sendMessage(String.format("to %s given %s", p.getName(), gun.name()));
                    } catch (IllegalArgumentException e) {
                        p.sendMessage("Achtung! ArmorShopCMD exception occurred: Incorrect name of gun: " + e.getMessage());
                    }
                }
                case LEATHER_HELMET -> {
                    p.closeInventory();
                    Armor h = new ArmorHelmetDecorator();
                    h.giveAbsorptionHp(p);
                    h.setHp(p);
                    ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
                    ItemStack armor = new ItemStack(Material.LEATHER_CHESTPLATE);

                    ItemMeta helmet_meta = helmet.getItemMeta();
                    ItemMeta armor_meta = armor.getItemMeta();

                    assert helmet_meta != null;
                    helmet_meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
                    helmet_meta.setDisplayName(ChatColor.GRAY + "Helmet");

                    assert armor_meta != null;
                    armor_meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
                    armor_meta.setDisplayName(ChatColor.GRAY + "Armor");

                    helmet.setItemMeta(helmet_meta);
                    armor.setItemMeta(armor_meta);

                    p.getInventory().setHelmet(helmet);
                    p.getInventory().setChestplate(armor);

                }
                case LEATHER_CHESTPLATE -> {
                    p.closeInventory();
                    Armor a = new ArmorOnlyDecorator();
                    a.giveAbsorptionHp(p);
                    a.setHp(p);

                    ItemStack armor = new ItemStack(Material.LEATHER_CHESTPLATE);

                    ItemMeta armor_meta = armor.getItemMeta();

                    assert armor_meta != null;
                    armor_meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
                    armor_meta.setDisplayName(ChatColor.GRAY + "Armor");

                    armor.setItemMeta(armor_meta);

                    p.getInventory().setChestplate(armor);

                }
            }
            i.setCancelled(true);
        }

    }

    public static void handle(Gun gun, Player player) {

        ItemStack gunItem = new ItemStack(gun.getMaterial(), 1);
        ItemMeta meta = gunItem.getItemMeta();
        List<String> lore = new ArrayList<>();

        assert meta != null;
        meta.getPersistentDataContainer().set(
                Objects.requireNonNull(NamespacedKey.fromString("gun_name")),
                PersistentDataType.STRING,
                gun.name()
        );

        meta.getPersistentDataContainer().set(
                Objects.requireNonNull(NamespacedKey.fromString("gun_magazine")),
                PersistentDataType.INTEGER,
                gun.getMagazine()
        );

        meta.getPersistentDataContainer().set(
                Objects.requireNonNull(NamespacedKey.fromString("gun_ammo")),
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
