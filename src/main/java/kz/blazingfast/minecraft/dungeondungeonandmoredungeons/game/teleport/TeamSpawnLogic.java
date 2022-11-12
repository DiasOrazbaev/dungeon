package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.teleport;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.Game;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.Member;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

import static kz.blazingfast.minecraft.dungeondungeonandmoredungeons.DungeonDungeonAndMoreDungeons.log;

public class TeamSpawnLogic {

    static Coordinates[] attackSpawnCoordinates = {
            new Coordinates(78, 3, 19),
            new Coordinates(76, 3, 18),
            new Coordinates(74, 3, 17),
            new Coordinates(72, 3, 18),
            new Coordinates(70, 3, 19),
            new Coordinates(68, 3, 20),
            new Coordinates(66, 3, 19),
            new Coordinates(64, 2.5, 18),
            new Coordinates(62, 2.5, 19),
            new Coordinates(60, 2, 20),
    };

    static Coordinates[] defenseSpawnCoordinates = {
            new Coordinates(46, -3, 104),
            new Coordinates(48, -3, 105),
            new Coordinates(50, -3, 106),
            new Coordinates(52, -3, 105),
            new Coordinates(54, -3, 104),
            new Coordinates(55, -3, 102),
            new Coordinates(54, -3, 100),
            new Coordinates(52, -3, 99),
            new Coordinates(50, -3, 98),
            new Coordinates(48, -3, 99),
            new Coordinates(46, -3, 100),
            new Coordinates(45, -3, 102)
    };

    public static void attackSpawnPosition() {
        try {
            List<Member> members = Game.getMembersOfTeam("attack");

            if (members.isEmpty()) {
                return;
            }

            int random = new Random().nextInt(0, 9 - members.size());

            for (Member member : members) {
                teleportPlayer(Bukkit.getPlayer(member.getMembername()), attackSpawnCoordinates[random].getX(), attackSpawnCoordinates[random].getY(), attackSpawnCoordinates[random].getZ());
                random++;
            }
        } catch (Exception e) {
            log("Achtung! attackSpawnPosition exception occured: " + e);
        }
    }

    public static void defenseSpawnPosition() {
        try {
            List<Member> members = Game.getMembersOfTeam("defense");

            if (members.isEmpty()) {
                return;
            }

            int random = new Random().nextInt(0, 11 - members.size());

            for (Member member : members) {
                teleportPlayer(Bukkit.getPlayer(member.getMembername()), defenseSpawnCoordinates[random].getX(), defenseSpawnCoordinates[random].getY(), defenseSpawnCoordinates[random].getZ());
                random++;
            }
        } catch (Exception e) {
            log("Achtung! defenseSpawnPosition exception occured: " + e);
        }
    }

    public static void teleportPlayer(Player player, double x, double y, double z) {
        World world = player.getWorld();
        Location loc = new Location(world, x, y, z, -90, 0);
        player.teleport(loc);
    }
}
