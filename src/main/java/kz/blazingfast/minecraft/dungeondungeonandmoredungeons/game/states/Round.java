package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.states;

public class Round {

    private State state;
    private int attackWins;
    private int defenseWins;
    private int currentRound = getCurrentRound();
    private boolean running;
    private int freezeTime;
    private int buyTime;
    private int endTime;
    private int time;
    private String sign;

    public Round() {
        this.state = new ReadyState(this);
    }

    public void changeState(State newState) {
        this.state = newState;
    }

    public void setRunning(boolean run) {
        this.running = run;
    }

    public void setSign(String newSign) {
        this.sign = newSign;
    }

    public void setTime(int seconds) {
        this.time = seconds;
    }

    public void setFreezeTime(int seconds) {
        this.freezeTime = seconds;
    }

    public void setBuyTime(int seconds) {
        this.buyTime = seconds;
    }

    public void setEndTime(int seconds) {
        this.endTime = seconds;
    }

    public void setDefenseWins(int defenseWins) {
        this.defenseWins = defenseWins;
    }

    public void setAttackWins(int attackWins) {
        this.attackWins = attackWins;
    }

    public String getSign() {
        return sign;
    }

    public int getFreezeTime() {
        return freezeTime;
    }

    public int getBuyTime() {
        return buyTime;
    }

    public int getTime() {
        return time;
    }

    public int getEndTime() {
        return endTime;
    }

    public boolean isRunning() {
        return running;
    }

    public State getState() {
        return state;
    }

    public int getCurrentRound() {
        return attackWins + defenseWins + 1;
    }

    public void incrementRound() {
        this.currentRound++;
    }

    public int getAttackWins() {
        return attackWins;
    }

    public int getDefenseWins() {
        return defenseWins;
    }

    public void decrementTime() { time--; freezeTime--; buyTime--; }

    public void decrementEndTime() {
        endTime--;
    }

}
