package db;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class DB
{
    private static Connection connection = null;

    public static Properties getProperties()
    {
        try(FileInputStream fileInputStream = new FileInputStream(
                "/home/lucas-motta/Documents/GitHub/JavaWithSQL/ContainerConfig/.env"
        ))
        {
            Properties properties = new Properties();
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
            try{
                Properties properties = getProperties();
                String dburl = properties.getProperty("dburl");
                connection = DriverManager.getConnection(dburl, properties);
                System.out.println("Get connect success!");
            }
            catch (Exception e){
                throw new DbException(e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection(){
        if (connection != null){
            try{
                connection.close();
                System.out.println("Connection closed!");
            }
            catch (Exception e){
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement statement){
        if (statement != null){
            try{
               statement.close();
                System.out.println("Statement closed!");
            }
            catch (Exception e){
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet){
        if (resultSet != null){
            try {
                resultSet.close();
                System.out.println("Result set closed!");
            }
            catch(Exception e){
                throw new DbException(e.getMessage());
            }
        }
    }
}
