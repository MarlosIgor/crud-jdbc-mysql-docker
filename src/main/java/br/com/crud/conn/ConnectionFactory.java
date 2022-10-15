package br.com.crud.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() throws SQLException {

        String url = "jdbc:mysql://localhost:3309/anime_store_code";
        String username = "root";
        String password = "root";

        return DriverManager.getConnection(url, username, password);

    }
}
