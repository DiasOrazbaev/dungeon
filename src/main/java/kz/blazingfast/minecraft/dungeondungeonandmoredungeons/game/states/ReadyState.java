package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.states;

public class ReadyState extends State {

    public ReadyState(Round round) {
        super(round);
        round.setRunning(false);
        round.setSign("Ready to run");
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
    }

    @Override
    public void onNewRound() {
        round.changeState(new NewRoundState(round));
    }
}
