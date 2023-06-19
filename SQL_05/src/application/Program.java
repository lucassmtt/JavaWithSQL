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
                    "UPDATE base_de_dados.seller " +
                         "SET BaseSalary = 2000000 " +
                         "WHERE DepartmentId = 1;"
            );

            int rows_affect_2 = statement.executeUpdate(
                    "UPDATE base_de_dados.seller " +
                            "SET BaseSalary = BaseSalary * 3" +
                            "WHERE DepartmentId = 2"
            );

            System.out.println("You confirm the operation? ");
            String response = scanner.next().toUpperCase();
            if (response.equals("S")){
                connection.commit();
            }
            else {
                connection.rollback();
            }
        }
        catch (Exception e){
            try {
                connection.rollback();
            }
            catch (SQLException e1){
                throw new DbException(e1.getMessage());
            }
        }
        finally {
            DB.closeStatement(statement);
            DB.closeConnection();
        }
    }
}
