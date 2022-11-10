package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game;

public class Member {

    String membername;
    int money;
    int kills;
    int deaths;

    public Member(String membername, int money, int kills, int deaths) {
        this.membername = membername;
        this.money = money;
        this.kills = kills;
        this.deaths = deaths;
    }

    public String getMembername() {
        return membername;
    }

    public String getStat() {
        return "Player name: " + membername + "\nPlayer money: " + money + "\nPlayer kills: " + kills + "\nPlayer deaths: " + deaths;
    }

    public void addStatKill() {
        kills++;
    }

    public void addStatDeath() {
        deaths++;
    }

    public void giveWinMoney() {
        money = money + 3250;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void giveLoseMoney(int loseStreak) {
        money = money + 1400 + loseStreak * 500;
    }

    public void giveRewardMoney(int reward) {
        money = money + reward;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getMoney() {
        return money;
    }
    public boolean canBuy(int cost) {
        return money >= cost;
    }

    public void subtractMoney(int cost) {
        money = money - cost;
    }

    public String toString(){
        return membername;
    }
}