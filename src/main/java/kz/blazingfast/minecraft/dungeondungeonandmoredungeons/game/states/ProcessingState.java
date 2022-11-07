package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.states;

public class ProcessingState extends State {

    public ProcessingState(Round round) {
        super(round);
        round.setRunning(true);
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
        round.changeState(new PausedState(round));
    }

    @Override
    public void onNewRound() {
        round.changeState(new NewRoundState(round));
    }
}
