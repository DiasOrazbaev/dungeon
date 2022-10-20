package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.observer;

public interface Observable {

    void subscribe(Observer observer);
    void unsubscribe(Observer observer);
    void sendAll();

}