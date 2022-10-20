package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class ArmorShopCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1) {
            return List.of(
                    "AK47",
                    "M4A1S",
                    "AWP",
                    "GLOCK",
                    "USP"
            );
        }
        return null;
    }
}
