package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.observer.Subscribers;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.observer.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArmorShopCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] strings) {


        if (sender instanceof Player p) {
            if (label.equalsIgnoreCase("gun")) {
                if (strings.length == 0) {
                    p.sendMessage("Set name of gun");
                    return true;
                }

                try {
                    Gun gun = Gun.valueOf(strings[0]);
                    ArmorShop.handle(gun, p);
                    p.sendMessage(String.format("to %s given %s", p.getName(), gun.name()));
                } catch (IllegalArgumentException exception) {
                    p.sendMessage("Incorrect name of gun");
                    return true;
                }
            }
        }


        return true;
    }
}
