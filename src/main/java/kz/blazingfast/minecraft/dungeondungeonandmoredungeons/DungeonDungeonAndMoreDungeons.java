package kz.blazingfast.minecraft.dungeondungeonandmoredungeons;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.commands.*;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.*;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.observer.TeamchatCommand;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.teleport.SpawnCommand;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun.WeaponEventListener;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun.InventoryShopCommand;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun.InventoryShopListener;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.Objects;

public final class DungeonDungeonAndMoreDungeons extends JavaPlugin implements Listener {

    public static DungeonDungeonAndMoreDungeons plugin;
    public static String hostname, port, database, username, dbpassword, table ;

    public static String LOGIN_CMD_USAGE, REGISTER_CMD_USAGE;
    public static String NOPERM_MSG;
    public static String BAD_PASSWORD, PASSWORD_LENGTH, PASSWORD_NOMATCH;
    public static String ALREADY_REGISTERED, ALREADY_LOGGED_IN;
    public static String SUCCESSFULLY_REGISTERED, SUCCESSFULLY_LOGGED_IN;
    public static String REGISTERING, LOGGING_IN;
    public static String REGISTER_REPEATING, LOGIN_REPEATING;
    public static String GO_REGISTER;
    public static String TIMEOUT;
    public static String WELCOME_TITLE, JOIN_TITLE_1, JOIN_TITLE_2;
    public static String LOG_OUTED, LOG_OUTED_EXCEPT;

    @Override
    public void onEnable() {
        plugin = DungeonDungeonAndMoreDungeons.getPlugin(DungeonDungeonAndMoreDungeons.class);
        this.databaseConfigSetup();
        this.messagesConfigSetup();
        this.registerPluginEvents();
        this.registerPluginCommands();

        Game game = new Game();
        game.context();

        }

    @Override
    public void onDisable() {
        plugin = null;
        clearAll();
    }

    public static DungeonDungeonAndMoreDungeons getInstance() {
        return plugin;
    }

    public static void log(String msg) {
        Bukkit.getConsoleSender().sendMessage(msg);
    }

    private void registerPluginEvents() {
        this.getServer().getPluginManager().registerEvents(new ServerEventListener(), this);
        this.getServer().getPluginManager().registerEvents(new WeaponEventListener(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryShopListener(), this);
        this.getServer().getPluginManager().registerEvents(new GameEventListener(),  this);
    }

    private void registerPluginCommands() {
        Objects.requireNonNull(this.getCommand("logout")).setExecutor(new LogoutCommand());
        Objects.requireNonNull(this.getCommand("register")).setExecutor(new RegisterCommand());
        Objects.requireNonNull(this.getCommand("login")).setExecutor(new LoginCommand());
        Objects.requireNonNull(this.getCommand("shop")).setExecutor(new InventoryShopCommand());
        Objects.requireNonNull(this.getCommand("game")).setExecutor(new GameCommand());
        Objects.requireNonNull(this.getCommand("game")).setTabCompleter(new GameCompleter());
        Objects.requireNonNull(this.getCommand("teamchat")).setExecutor(new TeamchatCommand());
        Objects.requireNonNull(this.getCommand("spawn")).setExecutor(new SpawnCommand());
    }

    private synchronized void clearAll() {
        if (!AuthCore.logged.isEmpty())
            AuthCore.logged.clear();
        if (!AuthCore.task.isEmpty())
            AuthCore.task.clear();
    }

    @Nonnull
    private static String cfgShortcut(String path) {
        return Objects.requireNonNull(getInstance().getConfig().getString(path)).replace("&", "§");
    }

    public static boolean isDatabaseConfigCorrect() {
        if (Objects.equals(getInstance().getConfig().getString("database.postgres.hostname"), "hostname"))
            return false;
        else
            return !Objects.equals(getInstance().getConfig().getString("database.postgres.password"), "password");
    }

    private void databaseConfigSetup() {
        hostname = getInstance().getConfig().getString("database.postgres.hostname");
        port = getInstance().getConfig().getString("database.postgres.port");
        database = getInstance().getConfig().getString("database.postgres.database");
        username = getInstance().getConfig().getString("database.postgres.username");
        dbpassword = getInstance().getConfig().getString("database.postgres.dbpassword");
        table = getInstance().getConfig().getString("database.postgres.table");
        if (isDatabaseConfigCorrect())
            DatabaseManipulation.createTable();
        else
            log("DDD >>> Achtung! DatabaseConfigSetup is set upped wrongly." +
                    "\n DDD >>> Look in the config.yml to set up.");
    }

    private void messagesConfigSetup() {
        LOGIN_CMD_USAGE = cfgShortcut("messages.login_cmd_usage");
        REGISTER_CMD_USAGE = cfgShortcut("messages.register_cmd_usage");
        NOPERM_MSG = cfgShortcut("messages.noperm_msg");
        BAD_PASSWORD =  cfgShortcut("messages.bad_password");
        PASSWORD_NOMATCH = cfgShortcut("messages.password_nomatch");
        ALREADY_REGISTERED = cfgShortcut("messages.already_registered");
        ALREADY_LOGGED_IN = cfgShortcut("messages.already_logged_in");
        REGISTERING = cfgShortcut("messages.registering");
        SUCCESSFULLY_REGISTERED = cfgShortcut("messages.successfully_logged_in");
        LOGGING_IN = cfgShortcut("messages.logging_in");
        SUCCESSFULLY_LOGGED_IN = cfgShortcut("messages.successfully_logged_in");
        REGISTER_REPEATING = cfgShortcut("messages.register_repeating");
        LOGIN_REPEATING = cfgShortcut("messages.login_repeating");
        TIMEOUT = cfgShortcut("messages.timeout");
        GO_REGISTER = cfgShortcut("messages.go_register");
        LOG_OUTED = cfgShortcut("messages.log_outed");
        LOG_OUTED_EXCEPT = cfgShortcut("messages.log_outed_except");
        JOIN_TITLE_1 = cfgShortcut("messages.join_title_1");
        JOIN_TITLE_2 = cfgShortcut("messages.join_title_2");
        PASSWORD_LENGTH = cfgShortcut("messages.password_length");
        WELCOME_TITLE = cfgShortcut("messages.welcome_title");
    }
}

