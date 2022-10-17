package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.commands;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.DungeonDungeonAndMoreDungeons;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.AuthCore;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.DatabaseManipulation;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class LogoutCMD implements CommandExecutor {

    public LogoutCMD() {
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull  Command command, @Nonnull  String label, @Nonnull  String[] args) {
        if (sender instanceof Player p) {
            if (DatabaseManipulation.isRegistered(p.getName()) && AuthCore.isLogged(p)) {
                AuthCore.logOut(p);
                p.sendMessage(DungeonDungeonAndMoreDungeons.LOG_OUTTED);
            } else {
                p.sendMessage(DungeonDungeonAndMoreDungeons.LOG_OUTTED_EXCEPT);
            }
        }
        return true;
    }
}