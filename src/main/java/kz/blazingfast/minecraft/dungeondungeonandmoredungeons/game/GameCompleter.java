package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import javax.annotation.Nonnull;
import java.util.List;

public class GameCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if (args.length == 1) {
            return List.of(
                    "start",
                    "stop",
                    "team",
                    "round",
                    "stat",
                    "my_stat",
                    "print_game",
                    "get",
                    "respawn"
            );
        }



        if (args[0].equals("my_stat") || args[0].equals("print_game") || args[0].equals("stop") || args[0].equals("start") || args[0].equals("respawn")  && args.length != 2) {
            return List.of();
        }

        if (args[0].equals("team") && args.length == 2) {
            return List.of(
                    "add_player",
                    "remove_player",
                    "swap_sides",
                    "swap"
            );
        }

        if (args[0].equals("stat") && args.length == 2) {
            return List.of(
                    "<player>"
            );
        }

        if (args[0].equals("get") && args.length == 2) {
            return List.of(
                    "alive_on_map",
                    "alive_on_attack",
                    "alive_on_defense"
            );
        }

        if (args[0].equals("round") && args.length == 2) {
            return List.of(
                    "run",
                    "pause",
                    "new",
                    "set",
                    "set_time",
                    "set_freezetime",
                    "set_buytime"
            );
        }

        if (args[0].equals("team") && args[1].equals("add_player") || args[2].equals("remove_player") && args.length == 4) {
            return List.of(
                    "attack",
                    "defense"
            );
        }

        if (args[0].equals("team") && args[1].equals("swap") && args.length == 3) {
            return List.of("<player>");
        }

        if (args[0].equals("team") && !args[1].isEmpty() && !args[2].isEmpty() && args.length == 4) {
            return List.of("<player>");
        }


        if (args[0].equals("round") && (args[1].equals("set_time") || args[1].equals("set_freezetime") || args[1].equals("set_buytime")) && args.length == 3) {
            return List.of("<seconds>");
        }

        return null;
    }
}
