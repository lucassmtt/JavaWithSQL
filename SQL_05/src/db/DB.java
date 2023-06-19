package db;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class DB
{
    public static Connection connection = null;

    public static Properties loadProperties()
    {
        Properties properties = new Properties();
        try(FileInputStream fileInputStream = new FileInputStream(
                "/home/lucas-motta/Documents/GitHub/JavaWithSQL/ContainerConfig/.env"
        )) {
            properties.load(fileInputStream);
            return properties;
        }
        catch (Exception e){
            throw new DbException(e.getMessage());
        }
    }

    public static Connection getConnection()
    {
        if (connection == null){
            try {
                Properties properties = loadProperties();
                connection = DriverManager.getConnection(properties.getProperty("dburl"), properties);
            }
            catch (Exception e){
                throw new DbException(e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection()
    {
        if (connection != null){
            try {
                connection.close();
                System.out.println("Connection closed.");
            }
            catch (Exception e){
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement statement)
    {
        if (statement != null){
            try {
                statement.close();
                System.out.println("Statement closed...");
            }
            catch (Exception e){
                throw new DbException(e.getMessage());
            }
        }
    }
    public static void closeResultSet(ResultSet resultSet)
    {
        if (resultSet != null){
            try {
                resultSet.close();
                System.out.println("Statement closed...");
            }
            catch (Exception e){
                throw new DbException(e.getMessage());
            }
        }
    }
}
