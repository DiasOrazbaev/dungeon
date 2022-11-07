package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.states;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.FreezeMode;

public class PausedState extends State {

    public PausedState(Round round) {
        super(round);
        round.setRunning(false);
        round.setSign("Round Paused");
        FreezeMode.blockAllPlayers();

    }

    @Override
    public void onResume() {
        round.changeState(new ProcessingState(round));
        FreezeMode.unblockAllPlayers();
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onNewRound() {
        round.changeState(new NewRoundState(round));
    }
}
