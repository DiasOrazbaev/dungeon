package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.observer;

import org.bukkit.entity.Player;
import java.util.Objects;

public class Teammate implements Observer {

    private final Player player;

    public Teammate(Player player) {
        this.player = player;
    }

    @Override
    public void handleMessage(Player sender, String message) {
        Objects.requireNonNull(player).sendMessage("Teamchat <" + sender + "> " + message);

    }
}
