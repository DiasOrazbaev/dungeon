package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.commands;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.observer.Subscribers;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.observer.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SubscribeCMD implements CommandExecutor {

    public SubscribeCMD() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (label.equalsIgnoreCase("subscribe")) {
                Subscribers.getEventManager().subscribe(new User(p.getName()));
                p.sendMessage("You subscribed to the event");
            }
        }
        return false;
    }
}
