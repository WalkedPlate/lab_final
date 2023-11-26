package com.example.lab_final.Daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DaoBase {
    public Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String user = "root";
        String pass = "root";
        String database = "lab_9";
        String url = "jdbc:mysql://localhost:3306/lab_9";

        return DriverManager.getConnection(url,user,pass);
        //return DriverManager.getConnection("jdbc:mysql://34.41.157.58:3306/"+database,"root","&}jpA_zp1[s=X71o");
    }
}
