package kz.blazingfast.minecraft.dungeondungeonandmoredungeons;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.commands.*;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.AuthCore;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.DatabaseManipulation;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.ServerEventListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class DungeonDungeonAndMoreDungeons extends JavaPlugin implements Listener {

    public static DungeonDungeonAndMoreDungeons plugin;
    public static String url, database, password, table ;

    public static String LOGIN_CMD_USAGE, REGISTER_CMD_USAGE;
    public static String NOPERM_MSG;
    public static String BAD_PASSWORD, PASSWORD_LENGTH, PASSWORD_NOMATCH;
    public static String ALREADY_REGISTERED, ALREADY_LOGGINED;
    public static String SUCCESSFULLY_REGISTERED, SUCCESSFULLY_LOGGED_IN;
    public static String REGISTERING, LOGGING_IN;
    public static String REGISTER_REPEATING, LOGIN_REPEATING;
    public static String GO_REGISTER;
    public static String TIMEOUT;
    public static String WELCOME_TITLE, JOIN_TITLE_1, JOIN_TITLE_2;
    public static String LOG_OUTTED, LOG_OUTTED_EXCEPT;

    public static DungeonDungeonAndMoreDungeons getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        this.config_setup();
        this.messages();

        getServer().getPluginManager().registerEvents(new ServerEventListener(), this);

        plugin = (DungeonDungeonAndMoreDungeons) DungeonDungeonAndMoreDungeons.getPlugin(DungeonDungeonAndMoreDungeons.class);

        getCommand("logout").setExecutor(new LogoutCMD());
        getCommand("register").setExecutor(new RegisterCMD());
        getCommand("login").setExecutor(new LoginCMD());
        getCommand("event").setExecutor(new EventCMD());
        getCommand("event").setTabCompleter(new EventCompleter());
        getCommand("subscribe").setExecutor(new SubscribeCMD());
        getCommand("unsubscribe").setExecutor(new UnsubscribeCMD());
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

    private void config_setup() {
        url = this.getConfig().getString("database.postgres.url");
        database = this.getConfig().getString("database.postgres.database");
        password = this.getConfig().getString("database.postgres.password");
        table = this.getConfig().getString("database.postgres.table");
        DatabaseManipulation.createTable();

    }

    private void messages() {
        LOGIN_CMD_USAGE = "§7Use: §c/login <password>";
        REGISTER_CMD_USAGE = "§7Use: §c/register <password> <password_again>";
        NOPERM_MSG = "§cNot enough permissions!";
        BAD_PASSWORD =  "§cBad password!";
        PASSWORD_NOMATCH = "§cPasswords does not match!";
        ALREADY_REGISTERED = "§cThis nickname is already registered!";
        ALREADY_LOGGINED = "§aYou are already loggined...";
        REGISTERING = "§aRegistering...";
        SUCCESSFULLY_REGISTERED = "§aSuccesfully registered!";
        LOGGING_IN = "§aLogging in...";
        SUCCESSFULLY_LOGGED_IN ="§aSuccesfully logged in!";
        REGISTER_REPEATING = "§7Use: §c/register <password> <password_again>";
        LOGIN_REPEATING = "§7Use: §c/login <password>";
        TIMEOUT = "§cYou were kicked: Login timeout!";
        GO_REGISTER = "§cThis nickname is not registered. Use </register> for more info";
        LOG_OUTTED = "§aProcessing logout...";
        LOG_OUTTED_EXCEPT = "§aWe can't loggout you O_o";
        JOIN_TITLE_1 = "§c§lDUNGEON, DUNGEON AND MORE DUNGEONS";
        JOIN_TITLE_2 = "§7Welcome, {PLAYER}";
        PASSWORD_LENGTH = "§cPassword must be at least 4 characters long!";
        WELCOME_TITLE = "§8§m-------------------------------------\n" +
                        "§c§lDUNGEON, DUNGEON AND MORE DUNGEONS \n" +
                        "§7Welcome, to the club, §ebuddy§7!\n" +
                        "§8§m-------------------------------------";
    }

}

