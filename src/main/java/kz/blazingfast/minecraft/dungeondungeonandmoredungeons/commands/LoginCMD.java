package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.commands;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.AuthCore;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.DatabaseManipulation;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.SHA256;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class LoginCMD implements CommandExecutor {

    public LoginCMD() {

    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender instanceof Player p) {
            if (label.equalsIgnoreCase("login")) {
                p.sendMessage("/login <password>");
            }

            if (args.length == 1 && DatabaseManipulation.isRegistered(p.getName()) && !AuthCore.isLogged(p)) {
                String pass = SHA256.hash(args[0]);
                if (pass != null && pass.equals(DatabaseManipulation.getPassword(p))) {
                    AuthCore.login(p);
                } else {
                    p.sendMessage("Password credentials are wrong!");
                }
            }
        }
        return false;
    }
}
