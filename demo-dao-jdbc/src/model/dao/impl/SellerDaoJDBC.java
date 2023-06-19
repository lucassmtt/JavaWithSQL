package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
                    Department department = new Department();
                    department.setId(resultSet.getInt("DepartmentId"));
                    department.setName(resultSet.getString("DepName"));

                    Seller seller = new Seller(
                            resultSet.getInt("Id"),
                            resultSet.getString("Name"),
                            resultSet.getString("Email"),
                            resultSet.getDate("BirthDate"),
                            resultSet.getDouble("BaseSalary"),
                            department
                    );
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
        return null;
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }


}
