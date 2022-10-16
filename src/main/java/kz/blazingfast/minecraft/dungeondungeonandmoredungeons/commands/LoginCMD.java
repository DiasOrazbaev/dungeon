package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.commands;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.AuthCore;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.DatabaseManipulation;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.sha256;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoginCMD implements CommandExecutor {

    public LoginCMD() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (label.equalsIgnoreCase("login")) {
                p.sendMessage("/login <password>");
            }

            if (args.length == 1 && DatabaseManipulation.isRegistered(p.getName()) && !AuthCore.isLogged(p)) {
                String pass = sha256.hash(args[0]);
                if (pass.equals(DatabaseManipulation.getPassword(p))) {
                    AuthCore.login(p);
                } else {
                    p.sendMessage("Password credentials are wrong!");
                }
            }
        }

        return false;
    }
}
