package com.mygdx.game.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyJDBC {
    public static String JDBCConnection() {
        String output = null;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/caveflipdatabase", "root", "toor");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from character");
            output = resultSet.getString("Username");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}
