package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.commands;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.DungeonDungeonAndMoreDungeons;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.AuthCore;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.DatabaseManipulation;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.VegetableMode;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.SHA256;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class LoginCommand implements CommandExecutor {

    public LoginCommand() {
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender instanceof Player p) {
            if (label.equalsIgnoreCase("login") && !(args.length == 1)) {
                p.sendMessage(DungeonDungeonAndMoreDungeons.LOGIN_CMD_USAGE);
            }

            if (args.length == 1) {
                if (DatabaseManipulation.isRegistered(p.getName()) && !AuthCore.isLogged(p)) {
                    String pass = SHA256.hash(args[0]);

                    if (pass != null && pass.equals(DatabaseManipulation.getPassword(p))) {
                        AuthCore.login(p);
                        VegetableMode.unblockPlayer(p);
                        VegetableMode.unblindPlayer(p);
                    } else {
                        p.sendMessage(DungeonDungeonAndMoreDungeons.BAD_PASSWORD);
                    }
                } else if (DatabaseManipulation.isRegistered(p.getName()) && AuthCore.isLogged(p)) {
                    p.sendMessage(DungeonDungeonAndMoreDungeons.ALREADY_LOGGED_IN);
                } else {
                    p.sendMessage(DungeonDungeonAndMoreDungeons.GO_REGISTER);
                }
            }
        }
        return false;
    }
}