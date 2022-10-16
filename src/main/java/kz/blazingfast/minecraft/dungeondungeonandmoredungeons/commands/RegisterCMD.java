package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.commands;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.AuthCore;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.DatabaseManipulation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegisterCMD implements CommandExecutor {

    public RegisterCMD() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (label.equalsIgnoreCase("register")) {
                p.sendMessage("/register <password>");
            }

            if (args.length == 2) {
                if (!DatabaseManipulation.isRegistered(p.getName())) {
                    String pwd1 = args[0];
                    String pwd2 = args[1];
                    if (pwd1.length() >= 4) {
                        if (pwd1.equals(pwd2)) {
                            AuthCore.register(p, pwd1);
                        } else {
                            p.sendMessage("Password isn't match!");
                        }
                    } else {
                        p.sendMessage("Password credentials is incorrect: Lenght >= 4");
                    }
                } else {
                    p.sendMessage("You are already registered");
                }
            }
        }

        return false;
    }
}
