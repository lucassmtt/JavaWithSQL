package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentDaoJDBC implements DepartmentDao
{
    private Connection connection = null;

    public DepartmentDaoJDBC(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public void insert(Department obj)
    {
        if (connection != null) {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            try {
                String sql = "INSERT INTO base_de_dados.department (Name) VALUES (?) ";
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, obj.getName());

                int rows_affected = preparedStatement.executeUpdate();

                if (rows_affected > 0) {
                    resultSet = preparedStatement.getGeneratedKeys();
                    if (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        obj.setId(id);
                        System.out.println("The insertion is complete...");
                    }
                    DB.closeResultSet(resultSet);
                }
                else {
                    System.out.println("No rows affected!");
                }
            }
            catch (SQLException e){
                throw new DbException(e.getMessage());
            }
            finally {
                DB.closeStatement(preparedStatement);
            }
        }
        else {
            System.out.println("The connection is empty...");
        }

    }

    @Override
    public void update(Department obj)
    {
        if (connection != null)
        {
            PreparedStatement preparedStatement = null;

            try {
                String sql = "UPDATE base_de_dados.department SET Name = ? WHERE Id = ? ";
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, obj.getName());
                preparedStatement.setInt(2, obj.getId());
                int rows_affected = preparedStatement.executeUpdate();

                if (rows_affected > 0){
                    System.out.println("Update complete...");
                }
                else {
                    System.out.println("Update incomplete...");
                }
            }
            catch (SQLException e){
                throw new DbException(e.getMessage());
            }
            finally {
                DB.closeStatement(preparedStatement);
            }
        }
    }

    @Override
    public void deleteById(Integer Id)
    {
        if (connection != null)
        {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {
                String sql = "DELETE FROM base_de_dados.department WHERE Id = ? ";
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, Id);
                int rows_affected = preparedStatement.executeUpdate();

                if (rows_affected > 0){
                    System.out.println("Delete complete...");
                }
                else {
                    System.out.println("Delete imcomplete...");
                }
            }
            catch (SQLException e){
                if (e.getErrorCode() == 1451){
                    System.out.println("Deleting data assigned to department! ");
                    SellerDao sellerDao = DaoFactory.createSellerDao();
                    sellerDao.deleteByDepartment(Id);
                    deleteById(Id);
                }
                else{
                    throw new DbException(e.getMessage());
                }
            }
        }

    }

    @Override
    public Department findByID(Integer id)
    {
        if (connection != null)
        {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            try {
                String sql = "SELECT * FROM base_de_dados.department WHERE Id = ? ";
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();
                Department department = new Department();
                while (resultSet.next()){
                    department.setId(resultSet.getInt("Id"));
                    department.setName(resultSet.getString("Name"));
                }
                return department;
            }
            catch (SQLException e){
                throw new DbException(e.getMessage());
            }
        }
        else {
            System.out.println("The connection is empty");
            return null;
        }
    }

    @Override
    public List<Department> findAll()
    {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Department> departmentList = new ArrayList<>();
        Map<Integer, Department> departmentMap = new HashMap<>();

        if (connection != null){
            try {
                String sql = "SELECT * FROM base_de_dados.department ";
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {
                    Department department = new Department(resultSet.getInt("Id"), resultSet.getString("Name"));
                    departmentList.add(department);
                }
                return departmentList;
            }
            catch (SQLException e){
                throw new DbException(e.getMessage());
            }
        }
        else {
            System.out.println("The connection is null...");
        }
        return null;
    }
}