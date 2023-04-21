package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {

    public static Connection getConnection() {
        String userName = "root";
        String password = "MdaYzEbalZin123!";
        String connectionUrl = "jdbc:mysql://localhost:3306";
        try {
        Class.forName("com.mysql.jdbc.Driver");
        return  DriverManager.getConnection( connectionUrl , userName , password);
        }
        catch (SQLException | ClassNotFoundException e) {
            System.out.println( " Соединение не установленно ");
            return null;
        }
    }
}
