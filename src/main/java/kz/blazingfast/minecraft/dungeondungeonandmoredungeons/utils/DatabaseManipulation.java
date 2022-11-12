package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.DungeonDungeonAndMoreDungeons;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.Locale;

import static kz.blazingfast.minecraft.dungeondungeonandmoredungeons.DungeonDungeonAndMoreDungeons.*;

public class DatabaseManipulation {

    public static DatabaseConnection connection;
    public static String url = "jdbc:postgresql://" + hostname + ":" + port + "/" + database;

    public DatabaseManipulation() {
    }

    public static boolean isConnected() {
        try {
            connection = DatabaseConnection.getInstance(url, username, dbpassword);
        } catch (SQLException e) {
            DungeonDungeonAndMoreDungeons.log("DDD >>> Achtung! DatabaseManipulation isConnected() exception occurred: Can't connect to Database: " + e.getMessage());
            return false;
        }
        return true;
    }

    public static synchronized void createTable() {
        if (isConnected()) {
            try {
                assert connection != null;
                String sql = "CREATE TABLE IF NOT EXISTS " + table + "(nickname varchar primary key, password varchar);";
                PreparedStatement ps = connection.getConnection().prepareStatement(sql);
                ps.executeUpdate();
                ps.close();
            } catch (SQLException e) {
                DungeonDungeonAndMoreDungeons.log("DDD >>> Achtung! DatabaseManipulation createTable() exception occurred! Unable to create <" + table + "> table in the database: " + e.getMessage());
            }
        }
    }

    public static synchronized boolean isRegistered(String s) {
        try {
            System.out.println("SELECT COUNT(*) AS total FROM " + table + " WHERE nickname=? LIMIT 1;");
            PreparedStatement ps = connection.getConnection().prepareStatement("SELECT COUNT(*) AS total FROM " + table + " WHERE nickname=? LIMIT 1;");
            ps.setString(1, s);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getInt("total") != 0) {
                rs.close();
                ps.close();
                return true;
            }

        } catch (SQLException e) {
            DungeonDungeonAndMoreDungeons.log("DDD >>> Achtung! DatabaseManipulation isRegistered() exception occurred: " + e.getMessage());
        }

        return false;
    }

    public static synchronized void registerPlayer(Player p, String password) {
        try {
            System.out.println("INSERT INTO " + table + "(nickname, password) VALUES (?,?);");
            PreparedStatement ps = connection.getConnection().prepareStatement("INSERT INTO " + table + "(nickname, password) VALUES (?,?);");
            ps.setString(1, p.getName());
            ps.setString(2, SHA256.hash(password));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            DungeonDungeonAndMoreDungeons.log("DDD >>> Achtung! DatabaseManipulation registerPlayer() exception occurred: " + e.getMessage().toLowerCase(Locale.ROOT));
        }
    }

    public static synchronized void loginPlayer(Player p) {
    }

    public static synchronized String getPassword(Player p) {
        try {
            PreparedStatement ps = connection.getConnection().prepareStatement("SELECT password FROM " + table + " WHERE nickname=?;");
            ps.setString(1, p.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String s = rs.getString("password");
                rs.close();
                ps.close();
                return s;
            } else {
                p.sendMessage("Register deb (-_-)");
            }
        } catch (SQLException e) {
            DungeonDungeonAndMoreDungeons.log("DDD >>> DatabaseManipulation getPassword() exception occurred: " + e.getMessage());
        }
        return null;
    }
}