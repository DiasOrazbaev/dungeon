package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.states;

public class NewRoundState extends State {

    public NewRoundState(Round round) {
        super(round);
        round.setRunning(true);
        round.setTime(115);
        round.setFreezeTime(15);
        round.setBuyTime(20);
        round.setEndTime(5);
    }

    @Override
    public void onResume() {
        round.changeState(new ProcessingState(round));
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

