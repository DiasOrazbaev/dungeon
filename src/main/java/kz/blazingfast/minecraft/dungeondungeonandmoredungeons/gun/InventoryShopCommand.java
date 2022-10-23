package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class InventoryShopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender instanceof Player p) {
            Inventory gui = Bukkit.createInventory(p,9, ChatColor.DARK_GREEN + "Gun Shop");

            ItemStack AK47 = new ItemStack(Material.BLACK_DYE);
            ItemStack M4A1S = new ItemStack(Material.BLUE_DYE);
            ItemStack AWP = new ItemStack(Material.GREEN_DYE);
            ItemStack GLOCK = new ItemStack(Material.BROWN_DYE);
            ItemStack USP = new ItemStack(Material.LIGHT_BLUE_DYE);
            ItemStack HELMET = new ItemStack(Material.LEATHER_HELMET);
            ItemStack ARMOR = new ItemStack(Material.LEATHER_CHESTPLATE);

            ItemMeta AK47_meta = AK47.getItemMeta();
            assert AK47_meta != null;
            AK47_meta.setDisplayName(ChatColor.WHITE + "AK-47");
            ArrayList<String> AK47_lore = new ArrayList<>();
            AK47_lore.add(ChatColor.GOLD + "AK-47");
            AK47_meta.setLore(AK47_lore);
            AK47.setItemMeta(AK47_meta);

            ItemMeta M4A1S_meta = M4A1S.getItemMeta();
            assert M4A1S_meta != null;
            M4A1S_meta.setDisplayName(ChatColor.BLUE + "M4A1S");
            ArrayList<String> M4A1S_lore = new ArrayList<>();
            M4A1S_lore.add(ChatColor.GOLD + "M4A1S");
            M4A1S_meta.setLore(M4A1S_lore);
            M4A1S.setItemMeta(M4A1S_meta);

            ItemMeta AWP_meta = AWP.getItemMeta();
            assert AWP_meta != null;
            AWP_meta.setDisplayName(ChatColor.GREEN + "AWP");
            ArrayList<String> AWP_lore = new ArrayList<>();
            AWP_lore.add(ChatColor.GOLD + "AWP");
            AWP_meta.setLore(AWP_lore);
            AWP.setItemMeta(AWP_meta);

            ItemMeta GLOCK_meta = GLOCK.getItemMeta();
            assert GLOCK_meta != null;
            GLOCK_meta.setDisplayName(ChatColor.YELLOW + "GLOCK");
            ArrayList<String> GLOCK_lore = new ArrayList<>();
            GLOCK_lore.add(ChatColor.GOLD + "GLOCK");
            GLOCK_meta.setLore(GLOCK_lore);
            GLOCK.setItemMeta(GLOCK_meta);

            ItemMeta USP_meta = USP.getItemMeta();
            assert USP_meta != null;
            USP_meta.setDisplayName(ChatColor.BLUE + "USP");
            ArrayList<String> USP_lore = new ArrayList<>();
            USP_lore.add(ChatColor.GOLD + "USP");
            USP_meta.setLore(USP_lore);
            USP.setItemMeta(USP_meta);

            ItemMeta HELMET_meta = HELMET.getItemMeta();
            assert HELMET_meta != null;
            HELMET_meta .setDisplayName(ChatColor.WHITE + "ARMOR WITH HELMET");
            ArrayList<String> HELMET_lore = new ArrayList<>();
            HELMET_lore.add(ChatColor.GOLD + "Armor and Helmet: + 20 Absorption Hearts");
            HELMET_meta.setLore(HELMET_lore);
            HELMET.setItemMeta(HELMET_meta);

            ItemMeta ARMOR_meta = ARMOR.getItemMeta();
            assert ARMOR_meta != null;
            ARMOR_meta.setDisplayName(ChatColor.WHITE + "ARMOR ONLY");
            ArrayList<String> ARMOR_lore = new ArrayList<>();
            ARMOR_lore.add(ChatColor.GOLD + "Armor and Helmet: + 10 Absorption Hearts");
            ARMOR_meta.setLore(ARMOR_lore);
            ARMOR.setItemMeta(ARMOR_meta);

            ItemStack[] menu_items = {AK47, M4A1S, AWP, GLOCK, USP, ARMOR, HELMET};
            gui.setContents(menu_items);
            p.openInventory(gui);
        }
        return false;
    }
}
