package application;

import db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Program
{
    public static void main(String[] args){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DB.getConnection();
            String sql = "INSERT INTO seller" +
                    "(Name, Email, BirthDate, BaseSalary, DepartmentId)" +
                    "VALUES" +
                    "(? ? ? ? ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "Carl Sagan");
            preparedStatement.setString(2, "Carlsagan@gmail.com");
            preparedStatement.setDate(3, new java.sql.Date((format.parse("22/04/1990").getTime())));
            preparedStatement.setDouble(4, 4.000);
            preparedStatement.setInt(5, 4);
            int rows_affect = preparedStatement.executeUpdate();
            System.out.println("OK");
            System.out.println("ROW AFFECT " + rows_affect);
        }
        catch (SQLException e){
            e.getMessage();
        }
        catch (ParseException e){
            e.getMessage();
        }
        finally {
            DB.closeStatement(preparedStatement);
            DB.closeConnection();
        }
    }
}
