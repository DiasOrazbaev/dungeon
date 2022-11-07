package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.observer;

import org.bukkit.entity.Player;

public interface Observer {

    void handleMessage(Player sender, String message);
}
