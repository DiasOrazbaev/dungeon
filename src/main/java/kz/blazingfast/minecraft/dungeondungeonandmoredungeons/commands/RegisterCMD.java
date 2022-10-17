package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.commands;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.DungeonDungeonAndMoreDungeons;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.AuthCore;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.DatabaseManipulation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class RegisterCMD implements CommandExecutor {

    public RegisterCMD() {
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender instanceof Player p) {
            if (label.equalsIgnoreCase("register") && !(args.length == 2)) {
                p.sendMessage(DungeonDungeonAndMoreDungeons.REGISTER_CMD_USAGE);
            }

            if (args.length == 2) {
                if (!DatabaseManipulation.isRegistered(p.getName())) {
                    String pwd1 = args[0];
                    String pwd2 = args[1];
                    if (pwd1.length() >= 4) {
                        if (pwd1.equals(pwd2)) {
                            AuthCore.register(p, pwd1);
                        } else {
                            p.sendMessage(DungeonDungeonAndMoreDungeons.PASSWORD_NOMATCH);
                        }
                    } else {
                        p.sendMessage(DungeonDungeonAndMoreDungeons.PASSWORD_LENGTH);
                    }
                } else {
                    p.sendMessage(DungeonDungeonAndMoreDungeons.ALREADY_REGISTERED);
                }
            }
        }
        return false;
    }
}