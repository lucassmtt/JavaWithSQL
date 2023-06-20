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
import java.util.List;
import java.util.Properties;

public class Program
{
    public static void main(String[] args)
    {
        SellerDao sellerDao = DaoFactory.createSellerDao();
        System.out.println("=== TEST 1: seller findByID ===: ");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n=== TEST 2: seller findByDepartment ===: ");
        Department department = new Department(2, "Eletronics");
        List<Seller> list = sellerDao.findByDepartment(department);
        for (Seller employee : list){
            System.out.println(employee);
        }

        System.out.println("\n=== TEST 3: seller findAll ===: ");
        list = sellerDao.findAll();
        for (Seller employee : list){
            System.out.println(employee);
        }

        System.out.println("\n=== TEST 4: seller insert ===: ");
        Seller sellerToInsert = new Seller(null, "Joao", "Joao@gmail.com", new Date(), 4000.00, department);
        sellerDao.insert(sellerToInsert);
        System.out.println("Successful insertion! ID: " + sellerToInsert.getId());

        System.out.println("\n=== TEST 5: seller update ===: ");
        seller = sellerDao.findById(2);
        seller.setName("Matheus Oliveira");
        seller.setDepartment(department);
        sellerDao.update(seller);
        System.out.println("Update complete");
    }
}
