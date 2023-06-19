package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.dao.impl.DepartmentJDBC;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.Properties;

public class Program
{
    public static void main(String[] args)
    {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        Seller seller = sellerDao.findById(2);
        System.out.println(seller);
    }
}