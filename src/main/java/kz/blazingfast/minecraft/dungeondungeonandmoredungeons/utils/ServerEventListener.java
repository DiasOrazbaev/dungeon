package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.DungeonDungeonAndMoreDungeons;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils.AuthCore.*;

public class ServerEventListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void secureDisconnect(PlayerQuitEvent e) {
        logOut(e.getPlayer());
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.sendMessage(DungeonDungeonAndMoreDungeons.WELCOME_TITLE);
        VegetableMode.blockPlayer(p);
        if (DatabaseManipulation.isRegistered(p.getName())) {
            loginRepeating(p);
        } else {
            registerRepeating(p);
        }

        // TODO Player login/register timer code
    }
}
