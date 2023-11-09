package lk.ijse.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.Class.forName;

public class DBConnection {
    private  static DBConnection dbConnection;

    private Connection connection;

    String url ="jdbc:mysql://localhost:3306/serisaraNetworks";
    String userName = "root";
    String password = "Dilsh@1234";

    private DBConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection(url,userName,password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance(){
        return ((dbConnection==null)? dbConnection=new DBConnection() : dbConnection);
    }

    public Connection getConnection(){
        return connection;
    }
}
