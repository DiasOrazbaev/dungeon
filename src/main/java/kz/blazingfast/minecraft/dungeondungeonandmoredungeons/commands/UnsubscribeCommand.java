package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.commands;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.observer.Subscribers;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.observer.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;


public class UnsubscribeCommand implements CommandExecutor {

    public UnsubscribeCommand() {
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if (sender instanceof Player p) {
            if (label.equalsIgnoreCase("unsubscribe")) {
                Subscribers.getEventManager().unsubscribe(new User(p.getName()));
                p.sendMessage("You unsubscribed from the event");
            }
        }
        return false;
    }
}
