package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static kz.blazingfast.minecraft.dungeondungeonandmoredungeons.DungeonDungeonAndMoreDungeons.log;
import static kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.Game.round;
import static org.bukkit.Bukkit.getPlayer;

public class GameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player p) {

            if (label.equalsIgnoreCase("game") && args.length == 0) {
                p.sendMessage("usage /game <argument>");
            }

            if (label.equalsIgnoreCase("game") && args.length == 1) {
                if (args[0].equalsIgnoreCase("my_stat")) {
                    p.sendMessage(Game.getMemberStat(p.getName()));
                }
                if (args[0].equalsIgnoreCase("print_game")) {
                    Game.printGame(p);
                }
                if (args[0].equalsIgnoreCase("start")) {
                    round.getState().onResume();
                }
                if (args[0].equalsIgnoreCase("respawn")) {
                    Game.respawnMembers();
                }
            }

            if (label.equalsIgnoreCase("game") && args.length == 2) {
                if (args[0].equalsIgnoreCase("round") && (args[1].equalsIgnoreCase("run"))) {
                    round.getState().onResume();
                    p.sendMessage("/game round run command used ");
                } else if (args[0].equalsIgnoreCase("round") && (args[1].equalsIgnoreCase("pause"))){
                    round.getState().onPause();
                    p.sendMessage("/game round pause command used");
                }

                /* else if (args[0].equalsIgnoreCase("round") && (args[1].equalsIgnoreCase("restart"))) {
                    round.getState().onNewRound();
                    p.sendMessage("/game round restart command used"); */

                else if (args[0].equalsIgnoreCase("round") && (args[1].equalsIgnoreCase("new"))) {
                    round.getState().onNewRound();
                    Game.respawnMembers();
                    p.sendMessage("/game round mew command used");
                } else if (args[0].equalsIgnoreCase("stat") && !args[1].isEmpty()) {
                    p.sendMessage(Game.getMemberStat(args[1]));
                } else if (args[0].equalsIgnoreCase("get") && args[1].equalsIgnoreCase("alive_on_map")) {
                    p.sendMessage("Alive all: " + Game.getAliveOnMap());
                }  else if (args[0].equalsIgnoreCase("get") && args[1].equalsIgnoreCase("alive_on_defense")) {
                    p.sendMessage("Alive on defense: " + Game.getAliveOnDefense());
                } else if (args[0].equalsIgnoreCase("get") && args[1].equalsIgnoreCase("alive_on_attack")) {
                    p.sendMessage("Alive on attack: " + Game.getAliveOnAttack());
                }
            }

            if (label.equalsIgnoreCase("game") && args.length == 3) {
                if (args[0].equalsIgnoreCase("round") && (args[1].equalsIgnoreCase("set_time") && !args[2].isEmpty())) {
                    try {
                        Integer seconds = Integer.valueOf(args[2]);

                        round.setTime(seconds);
                        p.sendMessage("/game round set_time " + seconds + " command used");
                    } catch (Exception e) {
                        log(" Achtung! /game round command exception occurred: " + e.getMessage());
                        p.sendMessage("usage /game round set_time <int seconds>");
                    }
                }
            }

            if (label.equalsIgnoreCase("game") && args.length == 4) {
                if (args[0].equalsIgnoreCase("team") && args[1].equalsIgnoreCase("add_player") && !args[2].isEmpty() && !args[3].isEmpty()) {
                    try {

                        if (getPlayer(args[3]) != null) {
                            if (Game.getMemberTeamname(args[3]) == null) {
                                Game.addMemberToTeam(args[2], args[3], 800, 0, 0);
                            } else {
                                if (args[2].equals(Game.getMemberTeamname(args[3]))) {
                                    p.sendMessage("Player is already in this team");
                                } else {
                                    Game.removeMemberFromTeam(Game.getMemberTeam(args[3]).getTeamname(), args[3]);
                                    Game.addMemberToTeam(args[2], args[3], 800, 0, 0);
                                }
                            }
                        } else {
                            p.sendMessage("/game team add_player command require online player!");
                        }
                    } catch (Exception e) {
                        log(" Achtung! /game team add_player command exception occurred: " + e.getMessage());
                        p.sendMessage("usage /game team add_player <team> <player>");
                    }
                } else if (args[0].equalsIgnoreCase("team") && args[1].equalsIgnoreCase("remove_player") && !args[2].isEmpty() && !args[3].isEmpty()) {
                        try {
                            if (getPlayer(args[3]) != null) {
                                if (Objects.equals(Game.getMemberTeamname(args[3]), args[2])) {
                                    Game.removeMemberFromTeam(args[2], args[3]);
                                }
                            } else {
                                p.sendMessage("/game team remove_player command require online player!");
                            }
                        } catch (Exception e) {
                            log(" Achtung! /game team remove_player command exception occurred: " + e.getMessage());
                            p.sendMessage("usage /game team remove_player <team> <player>");
                        }
                    }
                }
            }
        return true;
    }
}