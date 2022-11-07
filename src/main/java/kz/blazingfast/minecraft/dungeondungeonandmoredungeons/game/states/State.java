package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.states;

public abstract class State {

    Round round;

    State(Round round) {
        this.round = round;
    }

    public abstract void onResume();
    public abstract void onPause();
    public abstract void onNewRound();
}
