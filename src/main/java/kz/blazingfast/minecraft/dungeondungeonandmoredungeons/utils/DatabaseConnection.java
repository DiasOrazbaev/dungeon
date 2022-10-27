package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.DungeonDungeonAndMoreDungeons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection(String url, String username, String password) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            DungeonDungeonAndMoreDungeons.log("DDD >>> Achtung! DatabaseConnection() exception occurred: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DatabaseConnection getInstance(String url, String username, String password) throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection(url,username,password);
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnection(url, username, password);
        }
        return instance;
    }
}
