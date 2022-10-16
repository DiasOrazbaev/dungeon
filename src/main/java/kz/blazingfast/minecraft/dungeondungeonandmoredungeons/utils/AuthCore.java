package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.DungeonDungeonAndMoreDungeons;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitScheduler;

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
    }

    public static void logIn(Player p) {
        logged.add(p);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void secureDisconnect(PlayerQuitEvent e) {
        logOut(e.getPlayer());
    }


    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (DatabaseManipulation.isRegistered(p.getName())) {
            loginRepeating(p);
        } else {
            registerRepeating(p);
        }

        // TODO Player login/register timer code
    }

    public static void register(Player p, String pass) {
        cancelLoginTask(p);
        p.sendMessage("Registering you");
        Bukkit.getScheduler().scheduleSyncDelayedTask(DungeonDungeonAndMoreDungeons.plugin, () -> {
            DatabaseManipulation.registerPlayer(p,pass);
            logIn(p);
            p.sendMessage("Registered successfully");
        }, 20L);
    }

    public static void login(Player p) {
        cancelLoginTask(p);
        p.sendMessage("Logining you");
        Bukkit.getScheduler().scheduleSyncDelayedTask(DungeonDungeonAndMoreDungeons.plugin, () -> {
            DatabaseManipulation.loginPlayer(p);
            logIn(p);
            p.sendMessage("Logined successfully");
        }, 20L);
    }

    public static void cancelRegisterTask(Player p) {
        if (task.containsKey(p)) {
            Bukkit.getScheduler().cancelTask((Integer)task.get(p));
            task.remove(p);
        }
    }

    public static void cancelLoginTask(Player p) {
        if (task.containsKey(p)) {
            Bukkit.getScheduler().cancelTask((Integer)task.get(p));
            task.remove(p);
        }
    }

    public static void registerRepeating(final Player p) {
        int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(DungeonDungeonAndMoreDungeons.plugin, new Runnable() {
            @Override
            public void run() {
                p.sendMessage("register repeating message");

            }
        }, 0L, 20000);
        task.put(p,i);
    }

    public static void loginRepeating(final Player p) {
        int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(DungeonDungeonAndMoreDungeons.plugin, new Runnable() {
            @Override
            public void run() {
                p.sendMessage("login repeating message");
            }
        }, 0L, 20000);
        task.put(p,i);
    }


}

