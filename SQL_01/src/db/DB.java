package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null){
            try {
                Properties properties = loadProperties();
                String url = properties.getProperty("dburl");
                connection = DriverManager.getConnection(url, properties);
            }
            catch (SQLException e) {
                throw new DbException(e.getMessage());
            }

        }
        return connection;
    }

    public static void closeConnection(){
        if (connection != null){
            try{
                System.out.println("Connection closed...");
                connection.close();
            }
            catch (SQLException e){
                throw new DbException(e.getMessage());
            }
        }
    }

    public static Properties loadProperties(){
        try (FileInputStream fileInputStream = new FileInputStream(
                "/home/lucas-motta/Documents/GitHub/JavaWithSQL/ContainerConfig/.env"
//                "/home/lucas-motta/Documents/GitHub/JavaWithSQL/SQL_01/db.properties"
        )) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
//            System.out.println(properties.toString());
            return properties;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeStatement(Statement statement) {
        if (statement != null){
            try{
                System.out.println("Statement closed...");
                statement.close();
            }
            catch (SQLException e){
                throw new DbException(e.getMessage());
            }
        }

    }

    public static void closeResultSet(ResultSet resultSet){
        if (resultSet != null) {
            try {
                resultSet.close();
                System.out.println("Closed resultSet...");
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}
