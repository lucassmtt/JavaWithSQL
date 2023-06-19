package application;

import db.DB;

import java.sql.*;
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
            String sql = "INSERT INTO base_de_dados.seller" +
                    "(Name, Email, BirthDate, BaseSalary, DepartmentId)" +
                    "VALUES" +
                    "(?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, "Carl Sagan");
            preparedStatement.setString(2, "Carlsagan@gmail.com");
            preparedStatement.setDate(3, new java.sql.Date((format.parse("22/04/1990").getTime())));
            preparedStatement.setDouble(4, 4000);
            preparedStatement.setInt(5, 4);

            int rows_affect = preparedStatement.executeUpdate();

            if (rows_affect > 0){
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next()){
                    int id = resultSet.getInt(1);
                    System.out.println("Id: " + id);
                }
            }
            else{
                System.out.println("No rows affect");
            }
        }
        catch (SQLException | ParseException e){
            e.printStackTrace();
        }
        finally {
            DB.closeStatement(preparedStatement);
            DB.closeConnection();
        }
    }
}
