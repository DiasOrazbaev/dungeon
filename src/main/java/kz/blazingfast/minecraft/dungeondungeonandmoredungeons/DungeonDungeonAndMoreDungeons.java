package kz.blazingfast.minecraft.dungeondungeonandmoredungeons;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.commands.LoginCMD;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.commands.RegisterCMD;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.AuthCore;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.DatabaseManipulation;
import org.bukkit.plugin.java.JavaPlugin;


public final class DungeonDungeonAndMoreDungeons extends JavaPlugin {

    public static DungeonDungeonAndMoreDungeons plugin;
    public static String url = "jdbc:postgresql://localhost:5432/dungeon";
    public static String database = "postgres";
    public static String password = "hehowich";

    public static DungeonDungeonAndMoreDungeons getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {

        plugin = getInstance();
        DatabaseManipulation.createTable();

        getCommand("register").setExecutor(new RegisterCMD());
        getCommand("login").setExecutor(new LoginCMD());

    }

    @Override
    public void onDisable() {
        plugin = null;
        clearAll();
    }

    private synchronized void clearAll() {
        if (!AuthCore.logged.isEmpty()) {
            AuthCore.logged.clear();
        }

        if (!AuthCore.task.isEmpty()) {
            AuthCore.task.clear();
        }

    }
}

