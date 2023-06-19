package model.dao.impl;

import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

import javax.swing.*;
import java.io.FileInputStream;
import java.sql.*;
import java.time.Period;
import java.util.List;
import java.util.Properties;

public class DepartmentJDBC implements DepartmentDao
{
    private Connection connection = null;

    public DepartmentJDBC(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public void insert(Department obj) {

    }

    @Override
    public void update(Department obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Department findByID(Integer id)
    {
        return null;
    }

    @Override
    public List<Department> findAll() {
        return null;
    }
}
