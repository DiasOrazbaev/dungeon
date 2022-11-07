package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.DungeonDungeonAndMoreDungeons;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.observer.Teamchat;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.observer.Teammate;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.states.Round;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static kz.blazingfast.minecraft.dungeondungeonandmoredungeons.DungeonDungeonAndMoreDungeons.getInstance;
import static org.bukkit.Bukkit.getPlayer;
import static org.bukkit.Bukkit.getServer;

public class Game {

    static Round round;
    static HashMap<String, Team> teams;
    static HashMap<String, Teamchat> chats;
    public static Scoreboard board;
    String sign;
    int dynamicTimer;

    public Game() {
        round = new Round();
        teams = new LinkedHashMap<>();
        chats = new LinkedHashMap<>();
        createTeam("attack");
        createTeam("defense");
        round.setDefenseWins(getTeamWins("defense"));
        round.setAttackWins(getTeamWins("attack"));
    }

    public synchronized void context() {
        new BukkitRunnable() {
            @Override
            public void run() {
                sign = round.getSign();

                if (round.getFreezeTime() >= 0) {
                    dynamicTimer = round.getFreezeTime();
                    if (round.isRunning()) {
                        round.setSign("Freeze time");
                        FreezeMode.blockAllPlayers();
                    }
                } else {
                    dynamicTimer = round.getTime();
                    if (round.isRunning()) {
                        round.setSign(round.getSign());
                        FreezeMode.unblockAllPlayers();
                    }
                }


                if (round.getTime() == 0) {
                    dynamicTimer = round.getEndTime();
                    roundTimeOverWinCondition();
                    roundEndState();
                }

                ScoreboardManager manager = Bukkit.getScoreboardManager();
                assert manager != null;
                board = manager.getNewScoreboard();
                final Objective objective = board.registerNewObjective("purple", "lamborghini");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                objective.setDisplayName(ChatColor.LIGHT_PURPLE + "Server State");

                Score scoreTime = objective.getScore("§6" + "Time left: ");
                scoreTime.setScore(dynamicTimer);

                Score scoreRounds = objective.getScore("§5" + "Round number: " + round.getCurrentRound());
                scoreRounds.setScore(-1);

                Score scoreTable = objective.getScore("§5" + "Attack: " + getTeamWins("attack") + " | Defense: " + getTeamWins("defense"));
                scoreTable.setScore(-2);

                Score scoreState = objective.getScore("§6" + "Current state: " + ChatColor.DARK_GREEN + sign);
                scoreState.setScore(-3);

                Score scoreAlive = objective.getScore("§6" + "Alive on attack: " + getAliveOnAttack() + ChatColor.WHITE + " | " + ChatColor.BLUE  + "Alive on defense: " + getAliveOnDefense());
                scoreAlive.setScore(-4);

                for (Player player : Bukkit.getOnlinePlayers()){
                    player.setScoreboard(board);
                }

                if (round.isRunning()) {
                    round.decrementTime();
                }
            }
        }.runTaskTimer(DungeonDungeonAndMoreDungeons.plugin, 0L, 20L);
    }

    public static void roundForceWinCondition() {
        if (getAliveOnAttack() > 0 && getAliveOnDefense() == 0) {
            incrementDefenseWins();
            Bukkit.broadcastMessage("Defense win this round");
        }

        else if (getAliveOnDefense() > 0 && getAliveOnAttack() == 0) {
            incrementAttackWins();
            Bukkit.broadcastMessage("Attack win this round");
        }

        else {
            round.incrementRound();
            Bukkit.broadcastMessage("Unknown state, result is tie");
        }
    }

    public void roundTimeOverWinCondition() {
        sign = "<Round end>";
        if (getAliveOnDefense() > 0 && getAliveOnAttack() == 0) {
            incrementDefenseWins();
            Bukkit.broadcastMessage("Defense win this round");
        }
        round.getState().onNewRound();
    }

    public void roundEndState() {
        sign = "Round end in: " + round.getEndTime();
        if (round.getEndTime() == 0) {
            round.getState().onNewRound();
            Game.respawnMembers();
        } else {
            round.decrementEndTime();
        }
    }

    public static int getAliveOnMap() {
        return getAliveOnDefense() + getAliveOnAttack();
    }

    public static int getAliveOnAttack() {
        int all = getMembersNumber("attack");
        int dead = getDeadMembersNumber("attack");;
        return all - dead;
    }

    public static int getAliveOnDefense() {
        int all = getMembersNumber("defense");
        int dead = getDeadMembersNumber("defense");;
        return all - dead;
    }

    public static boolean isMember(String player) {
        return getMemberTeam(player) != null;
    }

    public static void incrementMemberKillStat(String player) {
        getMemberTeam(player).getMember(player).addStatKill();
    }

    public static void incrementMemberDeathStat(String player) {
        getMemberTeam(player).getMember(player).addStatDeath();
    }

    public static String getMemberStat(String player) {
        return getMemberTeam(player).getMember(player).getStat();
    }

    public static Member getMember(String player) {
        return getMemberTeam(player).getMember(player);
    }

    public static int getDeadMembersNumber(String team) {
        Team t = teams.get(team);
        return t.deadMembersNumber();
    }

    public static int getMembersNumber(String team) {
        Team t = teams.get(team);
        return t.membersNumber();
    }

    public static @Nullable Team getMemberTeam(String player) {
        if (teams.get("attack").isMember(player)) {
            return getTeam("attack");
        } else if (teams.get("defense").isMember(player)) {
            return getTeam("defense");
        } else return null;
    }

    public static Teamchat getTeamChat(String team) {
        return chats.get(team);
    }

    public static @Nullable String getMemberTeamname(String player) {
        if (teams.get("attack").isMember(player)) {
            return "attack";
        } else if (teams.get("defense").isMember(player)) {
            return "defense";
        } else return null;
    }

    public static Team getTeam(String teamname) {
        return teams.get(teamname);
    }

    public static void incrementAttackWins() {
        Team attack = teams.get("attack");
        attack.decrementLoseStreak();
        attack.incrementWin();
        round.setAttackWins(attack.getWins());

        Team defense = teams.get("defense");
        defense.incrementLoseStreak();
    }

    public static void incrementDefenseWins() {
        Team defense = teams.get("defense");
        defense.incrementWin();
        round.setAttackWins(defense.getWins());

        Team attack = teams.get("attack");
        attack.incrementLoseStreak();
    }

    public void createTeam(String team) {
        Team t = new Team(team, 0, 0);
        teams.put(team, t);

        Teamchat c = new Teamchat();
        chats.put(team, c);
    }

    public static void addMemberToTeam(String team, String player, int money, int kills, int deaths) {
        Member member = new Member(player, money, kills, deaths);
        Team t = teams.get(team);
        t.addMember(member);
        Player p = Bukkit.getPlayer(player);

        Teamchat c = chats.get(team);
        c.addChatMember(new Teammate(p));
        assert p != null;
        p.sendMessage("You are member of team: " + team);
    }

    public static void removeMemberFromTeam(String team, String player) {
        Team t = teams.get(team);
        t.removeMember(t.getMember(player));
        Player p = Bukkit.getPlayer(player);

        Teamchat c = chats.get(team);
        c.removeChatMember(new Teammate(p));
        assert p != null;
        p.sendMessage("You are no more member of team: " + team);

    }

    public static int getTeamWins(String team) {
        Team t = teams.get(team);
        return t.getWins();
    }

    public static void respawnMembers() {
        Team attack = teams.get("attack");
        Team defense = teams.get("defense");
        attack.respawnMembers();
        defense.respawnMembers();

        if (!attack.getMembers().isEmpty()) {
            for (Member member : attack.getMembers()) {
                Player player = getPlayer(member.getMembername());
                player.spigot().respawn();
                getServer().getScheduler().scheduleSyncDelayedTask(getInstance(), () -> player.setGameMode(GameMode.ADVENTURE), 5);
            }
        }

        if (!defense.getMembers().isEmpty()) {
            for (Member member : defense.getMembers()) {
                Player player = getPlayer(member.getMembername());
                player.spigot().respawn();
                getServer().getScheduler().scheduleSyncDelayedTask(getInstance(), () -> player.setGameMode(GameMode.ADVENTURE), 5);
            }
        }


    }

    public static boolean isBuyTime() {
        return round.getBuyTime() >= 0;
    }

    public static void printGame(Player p){
        for(Map.Entry<String , Team> entry : teams.entrySet() ) {
            Team team = entry.getValue();
            p.sendMessage("Team Name : " + team.getTeamname());
            p.sendMessage("Players : " + team.getMembers().toString());
            p.sendMessage("Wins: " + team.getWins());
        }
    }
}
