package application;

import db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Program
{
    public static void main(String[] args)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DB.getConnection();

            preparedStatement = connection.prepareStatement(
                    "UPDATE base_de_dados.seller " +
                    "SET BaseSalary = BaseSalary + ? " +
                    "WHERE (Email = ?) ");
            preparedStatement.setDouble(1, 1000.00);
            preparedStatement.setString(2, "alex@gmail.com");
            int rowsAffect = preparedStatement.executeUpdate();
            System.out.println("Rows affect: " + rowsAffect);
            System.out.println("A");
        }
        catch (Exception e){
            e.getStackTrace();
        }
        finally {
            DB.closeStatement(preparedStatement);
            DB.closeConnection();
        }

    }
}
