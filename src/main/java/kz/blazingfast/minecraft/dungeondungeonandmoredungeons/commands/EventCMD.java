package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.commands;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.observer.Subscribers;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Logger;


public class EventCMD implements CommandExecutor {
    private Logger logger = Bukkit.getLogger();

    public EventCMD() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (label.equalsIgnoreCase("event") && !(args.length == 1)) {
                p.sendMessage("usage /event <event>");
            }
            if (args.length == 1) {
                if (label.equalsIgnoreCase("event") && args[0].equalsIgnoreCase("add")) {
                    p.sendMessage("usage /event add <event>");
                } else if (label.equalsIgnoreCase("event") && args[0].equalsIgnoreCase("remove")){
                    p.sendMessage("usage /event remove <event>");
                }
            }

            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("add") && !args[1].isEmpty()) {
                    Subscribers.getEventManager().AddEvent(args[1]);
                    p.sendMessage("event added");
                    return true;
                } else if (args[0].equalsIgnoreCase("remove") && !args[1].isEmpty()) {
                    try {
                        Subscribers.getEventManager().removeEvent(args[1]);
                        p.sendMessage("event removed");
                    } catch (Exception e) {
                        System.out.println("Achtung! EventCMD removeEvent() exception occurred:" + e);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("sendAll")) {
                    Subscribers.getEventManager().sendAll();
                    return true;
                }
            }
        }

        return false;
    }
}