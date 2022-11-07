package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.TreeSet;

import static kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.VegetableMode.PLAYERS;

public class FreezeMode {

    public static Set<String> PLAYEES = new TreeSet<>();

    public static void blockAllPlayers() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            PLAYEES.add(p.getName());
        }
    }

    public static void unblockAllPlayers() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!PLAYERS.contains(p.getName())) {
                PLAYEES.remove(p.getName());
            }
        }
    }

}

