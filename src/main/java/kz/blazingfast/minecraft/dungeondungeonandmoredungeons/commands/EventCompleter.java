package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import javax.annotation.Nonnull;
import java.util.List;

public class EventCompleter implements TabCompleter {

    public EventCompleter() {
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if (args.length == 1) {
            return List.of(
                    "add",
                    "remove",
                    "sendAll"
            );
        }
        return null;
    }
}
