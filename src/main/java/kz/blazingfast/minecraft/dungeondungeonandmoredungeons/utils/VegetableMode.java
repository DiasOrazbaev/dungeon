package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Set;
import java.util.TreeSet;

public class VegetableMode {

    public static Set<String> PLAYERS = new TreeSet<>();

    public static void blockPlayer(Player p) {
        PLAYERS.add(p.getName());
    }

    public static void blindPlayer(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    public static void unblockPlayer(Player p) {
        PLAYERS.remove(p.getName());
    }

    public static void unblindPlayer(Player p) {
        p.removePotionEffect(PotionEffectType.BLINDNESS);
    }
}