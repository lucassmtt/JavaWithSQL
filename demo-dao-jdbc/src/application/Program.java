package application;

import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Program
{
    public static void main(String[] args)
    {
        Department department = new Department(1, "Gym");
        Seller seller = new Seller(21, "Jon", "jon@gmail.con", new Date(), 2000.0, department);

        System.out.println(department);
        System.out.println(seller);
    }
}
