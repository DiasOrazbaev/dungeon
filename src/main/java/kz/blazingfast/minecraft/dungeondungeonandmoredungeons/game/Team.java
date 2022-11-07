package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Team {

    private final String teamname;
    private int wins;
    private int loseStreak;

    List<Member> members = new ArrayList<>();
    List<Member> dead = new ArrayList<>();

    public Team(String teamname, int wins, int loseStreak) {
        this.teamname = teamname;
        this.wins = wins;
        this.loseStreak = loseStreak;
    }

    public int deadMembersNumber() {
        return dead.size();
    }

    public int membersNumber() {
        return members.size();
    }

    public void killMember(Member member) {
        dead.add(member);
    }

    public boolean isDead(Member member) {
        return dead.contains(member);
    }

    public void respawnMembers() {
        for (Member member : dead) {
            dead.remove(member);
        }
    }

    public void respawnMember(Member member) {
        dead.remove(member);
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public void removeMember(Member member) {
        members.remove(member);
    }

    public Member getMember(String player) {
        for (Member member : members) {
            if (Objects.equals(member.getMembername(), player)) {
                return member;
            }
        }
        return null;
    }

    public boolean isMember(String player) {
        for (Member member : members) {
            if (Objects.equals(member.getMembername(), player)) {
                return true;
            }
        }
        return false;
    }

    public String getTeamname() {
        return teamname;
    }

    public void incrementWin() {
        wins++;
        for (Member member : members) {
            member.giveWinMoney();
        }
    }

    public void incrementLoseStreak() {
        if (loseStreak < 0) {
            loseStreak = 0;
        }
        if (loseStreak > 4) {
            loseStreak = 4;
        }
        for (Member member : members) {
            member.giveLoseMoney(loseStreak);
        }
        loseStreak++;
    }

    public void decrementLoseStreak() {
        if (loseStreak > 4) {
            loseStreak = 4;
        }
        loseStreak--;
        if (loseStreak < 0) {
            loseStreak = 0;
        }
    }

    public int getWins() {
        return wins;
    }

    public List<Member> getMembers(){
        return members;
    }

}

