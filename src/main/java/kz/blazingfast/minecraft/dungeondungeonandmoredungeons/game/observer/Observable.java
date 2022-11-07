package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.observer;

public interface Observable {

    void addChatMember(Observer observer);
    void removeChatMember(Observer observer);
    void sendTeamMessage();

}