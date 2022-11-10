package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, String commandLabel, String[] args) {
        if (commandLabel.equalsIgnoreCase("spawn")) {
            Player player = (Player) sender;
            World world = player.getWorld();
            Location loc = new Location(world, 68, 3, 19, -90, 0);
            loc.add(0.5, 0, 0.5);
            player.teleport(loc);
            player.sendMessage("§3You have been teleported to the spawn location.");
            return true;
        }
        return false;
    }
}
