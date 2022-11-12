package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.teleport;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.DungeonDungeonAndMoreDungeons;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.DatabaseManipulation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class SpawnUtil implements Listener {

    private final Location spawn;

    public SpawnUtil(DungeonDungeonAndMoreDungeons plugin) {

        double x = 68;
        double y = 3;
        double z = 19;
        float yaw = 0;
        float pitch = 0;
        World world = Bukkit.getServer().getWorld("world");
        spawn = new Location(world, x, y, z, yaw, pitch);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void setDefaultSpawnPoint(Player p) {
        p.setBedSpawnLocation(spawn);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.setRespawnLocation(spawn);
    }

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (!DatabaseManipulation.isRegistered(playerName)) {
            player.teleport(spawn);
            setDefaultSpawnPoint(player);
        }
    }

}
