package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.observer;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Teamchat implements Observable {

    private List<Observer> teammates = new ArrayList<>();
    private String message;
    private Player sender;

    public Teamchat() {
    }

    public void sendMessage(Player sender, String message) {
        this.sender = sender;
        this.message = message;
        sendTeamMessage();
    }

    @Override
    public void addChatMember(Observer observer) {
        this.teammates.add(observer);
    }

    @Override
    public  void removeChatMember(Observer observer) {
        this.teammates.remove(observer);
    }

    @Override
    public void sendTeamMessage() {
        for (Observer observer: this.teammates) {
            observer.handleMessage(this.sender, this.message);
        }
    }
}
