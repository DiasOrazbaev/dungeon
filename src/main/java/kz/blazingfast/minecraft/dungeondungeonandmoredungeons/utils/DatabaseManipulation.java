package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils;

import org.bukkit.entity.Player;
import java.sql.*;
import static kz.blazingfast.minecraft.dungeondungeonandmoredungeons.DungeonDungeonAndMoreDungeons.*;

public class DatabaseManipulation {

    public static DatabaseConnection connection;

    public DatabaseManipulation() {
    }

    public static boolean isConnected() {
        try {
            connection = DatabaseConnection.getInstance(url, database,password);
        } catch (SQLException e) {
            System.out.println("Achtung! isConnected() exception occurred: Can't connect to Database: " + e.getMessage());
            return false;
        }
        return true;
    }

    public static synchronized void createTable() {

        if(isConnected()) {
            try {
                assert connection != null;
                String sql = "CREATE TABLE IF NOT EXISTS " + table + "(nickname varchar primary key, password varchar)";
                PreparedStatement ps = connection.getConnection().prepareStatement(sql);
                ps.executeUpdate();
                ps.close();
            } catch (SQLException e) {
                System.out.println("Achtung! createTable() exception occurred! Unable to create <" + table + "> table in the database: " + e.getMessage());
            }
        }
    }

    public static synchronized boolean isRegistered(String s) {
        try {
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
            System.out.println("Achtung! isRegistered() exception occurred: " + e.getMessage());
        }

        return false;
    }

    public static synchronized void registerPlayer(Player p, String password) {
        try {
            PreparedStatement ps = connection.getConnection().prepareStatement("INSERT INTO " + table + "(nickname,password) VALUES (?,?);");
            ps.setString(1, p.getName());
            ps.setString(2, SHA256.hash(password));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Achtung! registerPlayer() exception occurred: " + e.getMessage());
        }
    }

    public static synchronized void loginPlayer(Player p) {
    }

    public static synchronized  String getPassword(Player p) {
        try {
            PreparedStatement ps = connection.getConnection().prepareStatement("SELECT password FROM " + table + " WHERE nickname=?");
            ps.setString(1,p.getName());
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
            System.out.println("DatabaseManipulation getPassword() exception occurred: " + e.getMessage());
        }

        return null;
    }
}