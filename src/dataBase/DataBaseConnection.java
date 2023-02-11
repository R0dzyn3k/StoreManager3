package dataBase;

import java.sql.*;

public class DataBaseConnection {

    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:custom.db";

    public static Connection getConn() {
        return conn;
    }

    public static Statement getStat() {
        return stat;
    }

     static Connection conn;
     static Statement stat;

    public DataBaseConnection() {
        try {
            Class.forName(DataBaseConnection.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        }

        DatabaseCreator databaseCreator = new DatabaseCreator(stat);
        databaseCreator.createTables();
    }


















    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem z zamknieciem polaczenia");
            e.printStackTrace();
        }
    }
}
