package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Set;
import java.util.TreeSet;

public class VegetableMode {

    public static final Set<String> PLAYERS = new TreeSet<>();

    public static void blockPlayer(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, Integer.MAX_VALUE));
        PLAYERS.add(p.getName());
        System.out.println(PLAYERS);
    }

    public static void unblockPlayer(Player p) {
        p.removePotionEffect(PotionEffectType.BLINDNESS);
        PLAYERS.remove(p.getName());
        System.out.println(PLAYERS);
    }
}