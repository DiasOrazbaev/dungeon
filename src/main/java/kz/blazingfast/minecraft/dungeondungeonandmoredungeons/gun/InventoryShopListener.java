package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.decorator.Armor;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.decorator.ArmorHelmetDecorator;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.decorator.ArmorOnlyDecorator;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun.builder.Weapon;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun.builder.WeaponBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Objects;
import static kz.blazingfast.minecraft.dungeondungeonandmoredungeons.DungeonDungeonAndMoreDungeons.log;
import static kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun.InventoryShopCommand.*;

public class InventoryShopListener extends WeaponBuilder  implements Listener{

    @EventHandler
    public void clickEvent(InventoryClickEvent event) {

        Player p = (Player) event.getWhoClicked();
        try {
            if (event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GREEN + "Gun Shop")) {
                if (Objects.requireNonNull(event.getCurrentItem()).hasItemMeta() && Objects.requireNonNull(event.getCurrentItem().getItemMeta()).hasDisplayName()) {
                    switch (Objects.requireNonNull(event.getCurrentItem()).getType()) {
                        case BLACK_DYE -> {
                            p.closeInventory();
                            try {
                                handle(AK47, AK47_data, p);
                                p.sendMessage(String.format("to %s given %s", p.getName(), AK47_data.getName()));
                            } catch (IllegalArgumentException e) {
                                log("Achtung! ArmorShopCMD exception occurred: Incorrect name of gun: " + e.getMessage());
                            }
                        }
                        case BLUE_DYE -> {
                            p.closeInventory();
                            try {
                                handle(M4A1S, M4A1S_data, p);
                                p.sendMessage(String.format("to %s given %s", p.getName(), M4A1S_data.getName()));
                            } catch (IllegalArgumentException e) {
                                log("Achtung! ArmorShopCMD exception occurred: Incorrect name of gun: " + e.getMessage());
                            }
                        }
                        case GREEN_DYE -> {
                            p.closeInventory();
                            try {
                                handle(AWP, AWP_data, p);
                                p.sendMessage(String.format("to %s given %s", p.getName(), AWP_data.getName()));
                            } catch (IllegalArgumentException e) {
                                log("Achtung! ArmorShopCMD exception occurred: Incorrect name of gun: " + e.getMessage());
                            }
                        }
                        case GRAY_DYE -> {
                            p.closeInventory();
                            try {
                                handle(SG553, SG553_data, p);
                                p.sendMessage(String.format("to %s given %s", p.getName(), SG553_data.getName()));
                            } catch (IllegalArgumentException e) {
                                log("Achtung! ArmorShopCMD exception occurred: Incorrect name of gun: " + e.getMessage());
                            }
                        }
                        case BROWN_DYE -> {
                            p.closeInventory();
                            try {
                                handle(GLOCK, GLOCK_data, p);
                                p.sendMessage(String.format("to %s given %s", p.getName(), GLOCK_data.getName()));
                            } catch (IllegalArgumentException e) {
                                log("Achtung! ArmorShopCMD exception occurred: Incorrect name of gun: " + e.getMessage());
                            }
                        }
                        case LIGHT_BLUE_DYE -> {
                            p.closeInventory();
                            try {
                                handle(USP, USP_data, p);
                                p.sendMessage(String.format("to %s given %s", p.getName(), USP_data.getName()));
                            } catch (IllegalArgumentException e) {
                                log("Achtung! ArmorShopCMD exception occurred: Incorrect name of gun: " + e.getMessage());
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
                } else {
                    event.setCancelled(true);
                }
                event.setCancelled(true);
            }
        } catch (Exception e) {
            log("Achtung! clickEvent(InventoryClickEvent exception occurred");
            //TODO solve it Dias, please!
        }
    }

    public static void handle(@NotNull ItemStack gun, @NotNull Weapon gun_data, @NotNull Player p) {

        WeaponBuilder wp = new WeaponBuilder();
        ItemStack gunItem = wp.copy(gun);

        ItemMeta meta = gunItem.getItemMeta();
        assert meta != null;
        List<String> lore = meta.getLore();

        assert lore != null;
        lore.add("OWNER: " + ChatColor.LIGHT_PURPLE +  p.getName());

        meta.setLore(lore);

        meta.setDisplayName(String.format(
                "%s %s|%s %s",
                "" + ChatColor.WHITE + gun_data.getName(),
                "" + ChatColor.WHITE + gun_data.getMagazine(),
                "" + ChatColor.GRAY + gun_data.getMagazine(),
                "" + ChatColor.DARK_GREEN + gun_data.getAmmo()));
        gunItem.setItemMeta(meta);
        p.getInventory().addItem(gunItem);
    }
}
