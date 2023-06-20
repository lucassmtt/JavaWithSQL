package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.nio.channels.SelectableChannel;
import java.sql.*;
import java.util.*;

public class SellerDaoJDBC implements SellerDao
{
    private Connection connection = null;

    public SellerDaoJDBC(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void delete(Seller obj) {

    }

    @Override
    public Seller findById(Integer id)
    {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        if (connection != null)
        {
            try {
                preparedStatement = connection.prepareStatement(
                        "SELECT seller.*, department.Name as DepName " +
                             "FROM seller INNER JOIN department " +
                             "ON seller.DepartmentId = department.Id " +
                             "WHERE seller.Id = ?"
                );
                preparedStatement.setInt(1, id);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Seller seller = instantiateSeller(resultSet);
                    return seller;
                }
            }
            catch (SQLException e){
                throw new DbException(e.getMessage());
            }
            finally {
                DB.closeStatement(preparedStatement);
            }
        }
        else{
            System.out.println("The connection is null...");
        }
        return null;
    }

    private Seller instantiateSeller(ResultSet resultSet) throws SQLException
    {
        Seller seller = new Seller(
            resultSet.getInt("Id"),
            resultSet.getString("Name"),
            resultSet.getString("Email"),
            resultSet.getDate("BirthDate"),
            resultSet.getDouble("BaseSalary")
        );
        return seller;
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException
    {
        Department department = new Department();
        department.setId(resultSet.getInt("DepartmentId"));
        department.setName(resultSet.getString("DepName"));
        return department;
    }

    @Override
    public List<Seller> findAll()
    {
        if (connection != null)
        {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            Map<Integer, Department> map = new HashMap<>();
            List<Seller> sellerList = new ArrayList<>();
            try {
                String sql = "SELECT base_de_dados.seller.*, base_de_dados.department.Name as DepName " +
                        "FROM seller INNER JOIN department on seller.DepartmentId = department.Id " +
                        "ORDER BY Name; ";
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()){
                    Department department = map.get(resultSet.getInt("DepartmentId"));

                    if (department == null){
                        department = instantiateDepartment(resultSet);
                        map.put(resultSet.getInt("DepartmentId"), department);
                    }

                    Seller seller = instantiateSeller(resultSet);
                    sellerList.add(seller);
                }
                return sellerList;
            }
            catch (Exception e){
                throw new DbException(e.getMessage());
            }
        }
        System.out.println("The connection is empty...");
        return null;
    }

    @Override
    public List<Seller> findByDepartment(Department department)
    {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        if (connection != null) {
            try {
                int id = department.getId();

                String sql = "SELECT base_de_dados.seller.*, base_de_dados.department.Name as DepName " +
                             "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id " +
                             "WHERE DepartmentId = ? " +
                             "ORDER BY Name; ";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();
                Map<Integer,Department> map = new HashMap<>();
                List<Seller> sellerList = new ArrayList<>();

                while (resultSet.next()){
                    Department dep = map.get(resultSet.getInt("DepartmentId"));
                    if (dep == null){
                        dep = instantiateDepartment(resultSet);
                        map.put(resultSet.getInt("DepartmentId"), dep);
                    }
                    Seller seller = instantiateSeller(resultSet);
                    seller.setDepartment(dep);
                    sellerList.add(seller);
                }
                return sellerList;
            }
            catch (Exception e){
                throw new DbException(e.getMessage());
            }
            finally {
                DB.closeStatement(preparedStatement);
                DB.closeResultSet(resultSet);
            }
        }
        System.out.println("The connection is empty...");
        return null;
    }
}