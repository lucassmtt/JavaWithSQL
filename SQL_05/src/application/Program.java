package application;

import db.DB;
import db.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Scanner;

public class Program
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DB.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            int rows_affect_1 = statement.executeUpdate(
                    "UPDATE base_de_dados.seller SET BaseSalary = 20000 WHERE (DepartmentId = 1);"
            );

            int rows_affect_2 = statement.executeUpdate(
                    "UPDATE base_de_dados.seller SET BaseSalary = BaseSalary * 3 WHERE (DepartmentId = 2);"
            );

            System.out.println("You confirm the operation? ");
            String response = scanner.next().toUpperCase();
            if (response.equals("S")){
                System.out.println("Rows affect " + (rows_affect_1 + rows_affect_2));
                connection.commit();
            }
            else {
                connection.rollback();
            }
        }
        catch (Exception e){
            try {
                if (connection != null){
                    connection.rollback();
                    throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
                }
            }
            catch (SQLException e1){
                throw new DbException("Error in rollback! Caused by: " + e1.getMessage());
            }
        }
        finally {
            DB.closeStatement(statement);
            DB.closeConnection();
        }
    }
}
