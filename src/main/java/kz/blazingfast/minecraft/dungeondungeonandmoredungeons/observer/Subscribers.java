package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.observer;

import java.util.ArrayList;
import java.util.List;

public class Subscribers implements Observable {

    private List<Observer> subscribers = new ArrayList<>();
    private List<String> events = new ArrayList<>();

    static Subscribers eventManager = new Subscribers();
    public static Subscribers getEventManager() {
        return eventManager;
    }

    public void AddEvent(String event) {
        this.events.add(event);
        sendAll();
    }

    public void removeEvent(String event) {
        this.events.remove(event);
        sendAll();
    }
    @Override
    public void subscribe(Observer observer) {
        this.subscribers.add(observer);
    }

    @Override
    public  void unsubscribe(Observer observer) {
        this.subscribers.remove(observer);
    }
    @Override
    public void sendAll() {
        for (Observer observer: this.subscribers) {
            observer.handleEvent(this.events);
        }
    }

}
