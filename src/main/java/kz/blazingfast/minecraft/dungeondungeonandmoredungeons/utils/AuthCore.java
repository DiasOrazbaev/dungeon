package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.DungeonDungeonAndMoreDungeons;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import java.util.ArrayList;
import java.util.HashMap;

public class AuthCore implements Listener {

    public static ArrayList<Player> logged = new ArrayList<>();
    public static HashMap<Player, Integer> task = new HashMap<>();

    public AuthCore() {
    }

    public static boolean isLogged(Player p) {
        return logged.contains(p);
    }

    public static void logOut(Player p) {
        logged.remove(p);
        VegetableMode.blockPlayer(p);
    }

    public static void logIn(Player p) {
        logged.add(p);
    }

    public static void register(Player p, String pass) {
        cancelRegisterTask(p);
        p.sendMessage(DungeonDungeonAndMoreDungeons.REGISTERING);
        Bukkit.getScheduler().scheduleSyncDelayedTask(DungeonDungeonAndMoreDungeons.plugin, () -> {
            DatabaseManipulation.registerPlayer(p, pass);
            logIn(p);
            p.sendMessage(DungeonDungeonAndMoreDungeons.SUCCESSFULLY_REGISTERED);
        }, 20L);
    }

    public static void login(Player p) {
        cancelLoginTask(p);
        p.sendMessage(DungeonDungeonAndMoreDungeons.LOGGING_IN);
        Bukkit.getScheduler().scheduleSyncDelayedTask(DungeonDungeonAndMoreDungeons.plugin, () -> {
            DatabaseManipulation.loginPlayer(p);
            logIn(p);
            p.sendMessage(DungeonDungeonAndMoreDungeons.SUCCESSFULLY_LOGGED_IN);

        }, 20L);
    }

    public static void cancelRegisterTask(Player p) {
        if (task.containsKey(p)) {
            Bukkit.getScheduler().cancelTask(task.get(p));
            task.remove(p);
        }
    }

    public static void cancelLoginTask(Player p) {
        if (task.containsKey(p)) {
            Bukkit.getScheduler().cancelTask(task.get(p));
            task.remove(p);
        }
    }

    public static void registerRepeating(final Player p) {
        int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(DungeonDungeonAndMoreDungeons.plugin, () -> p.sendMessage(DungeonDungeonAndMoreDungeons.REGISTER_REPEATING), 0L, 100);
        task.put(p, i);
    }

    public static void loginRepeating(final Player p) {
        int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(DungeonDungeonAndMoreDungeons.plugin, () -> p.sendMessage(DungeonDungeonAndMoreDungeons.LOGIN_REPEATING), 0L, 100);
        task.put(p, i);
    }
}