package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.Game;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.Member;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun.builder.Director;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun.builder.Weapon;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun.builder.WeaponBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class InventoryShopCommand implements CommandExecutor {

    public static ItemStack AK47, M4A1S, AWP, SG553, GLOCK, USP, ARMOR, HELMET;
    public static Weapon AK47_data, M4A1S_data, AWP_data, SG553_data, GLOCK_data, USP_data, ARMOR_data, HELMET_data;

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if (sender instanceof Player p) {
            if (label.equalsIgnoreCase("shop")) {
                if (Game.isMember(p.getName())) {
                    Member member = Game.getMember(p.getName());
                    if (Game.isBuyTime()) {
                        Inventory gui = Bukkit.createInventory(p, 9, ChatColor.WHITE + "Your money: " + ChatColor.DARK_GREEN + "$"+ member.getMoney());

                        WeaponBuilder builder = new WeaponBuilder();
                        Director director = new Director();

                        director.build_ak47(builder);
                        AK47 = builder.getResult();
                        AK47_data = builder.getResultData();

                        director.build_m4a1s(builder);
                        M4A1S = builder.getResult();
                        M4A1S_data = builder.getResultData();

                        director.build_awp(builder);
                        AWP = builder.getResult();
                        AWP_data = builder.getResultData();

                        director.build_sg553(builder);
                        SG553 = builder.getResult();
                        SG553_data = builder.getResultData();

                        director.build_glock(builder);
                        GLOCK = builder.getResult();
                        GLOCK_data = builder.getResultData();

                        director.build_usp(builder);
                        USP = builder.getResult();
                        USP_data = builder.getResultData();

                        director.build_armor(builder);
                        ARMOR = builder.getResult();
                        ARMOR_data = builder.getResultData();

                        director.build_helmet(builder);
                        HELMET = builder.getResult();
                        HELMET_data = builder.getResultData();

                        ItemStack[] menu_items = {AK47, M4A1S, AWP, SG553, GLOCK, USP, ARMOR, HELMET};
                        gui.setContents(menu_items);
                        p.openInventory(gui);
                    } else {
                        p.sendMessage("Buy time has passed.");
                    }
                } else {
                    p.sendMessage("You are not a member of counter-strike game mode.");
                }
            }
        }
        return false;
    }

}
