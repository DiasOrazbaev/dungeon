package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils;

import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static kz.blazingfast.minecraft.dungeondungeonandmoredungeons.DungeonDungeonAndMoreDungeons.*;


public class DatabaseManipulation {

    public static DatabaseConnection connection;

    public DatabaseManipulation() {

    }

    public static boolean isConnected() {
        return connection != null;
    }

    public static synchronized void createTable() {

        try {
            connection = DatabaseConnection.getInstance(url, database,password);
            System.out.println("Database connected!");
        } catch (SQLException e) {
            System.out.println("Achtung! Can't connect to Database");
            return;
        }

        try {
            assert connection != null;
            String sql = "CREATE TABLE IF NOT EXISTS users(nickname varchar primary key, password varchar)";
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Achtung! Unable to create <users> table in the database");
        }
    }

    public static synchronized boolean isRegistered(String s) {
        try {
            PreparedStatement ps = connection.getConnection().prepareStatement("SELECT *, COUNT(*) AS total FROM users WHERE nickname=? LIMIT 1;");
            ps.setString(1, s);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getInt("total") != 0) {
                rs.close();
                ps.close();
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Achtung! isRegistered() exception occurred!");
        }

        return false;
    }

    public static synchronized void registerPlayer(Player p, String password) {
        try {
            PreparedStatement ps = connection.getConnection().prepareStatement("INSERT INTO users(nickname,password) VALUES (?,?);");
            ps.setString(1, p.getName());
            ps.setString(2, sha256.hash(password));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Achtung! registerPlayer() exception occurred!");
        }
    }

    public static synchronized void loginPlayer(Player p) {
    }

    public static synchronized  String getPassword(Player p) {
        try {
            PreparedStatement ps = connection.getConnection().prepareStatement("SELECT *, COUNT(*) AS total FROM users WHERE nickname=? LIMIT 1;");
            ps.setString(1,p.getName());
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getInt("total") != 0) {
                String s = rs.getString("password");
                rs.close();
                ps.close();
                return s;
            }
        } catch (SQLException e) {
            System.out.println("DatabaseManipulation getPassword() exception occurred");
        }

        return null;
    }
}