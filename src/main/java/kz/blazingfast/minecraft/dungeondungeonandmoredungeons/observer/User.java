package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.observer;

import org.bukkit.Bukkit;

import java.util.List;
import java.util.Objects;

public class User implements Observer {

    private final String username;

    public User(String username) {
        this.username = username;
    }

    @Override
    public void handleEvent(List<String> events) {
        Objects.requireNonNull(Bukkit.getPlayer(this.username)).sendMessage("Hello there, " + Bukkit.getPlayer(this.username)
                + "\n ! You participant of: ");
        for (String event: events) {
            Objects.requireNonNull(Bukkit.getPlayer(this.username)).sendMessage(event);
        }
        System.out.println();
    }
}
