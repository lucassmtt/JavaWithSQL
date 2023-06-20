package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.impl.DepartmentDaoJDBC;
import model.entities.Department;

public class ProgramToTestingDepartmentDaoJDBC
{
    public static void main(String[] args)
    {
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("--- 1 TEST --- INSERT  ");
        Department department = new Department(null, "FlipFlop");
        departmentDao.insert(department);
        System.out.println(department.getId());

        System.out.println("--- 2 TEST --- UPDATE  ");
        Department department_to_update = new Department(6, "Automation");
        departmentDao.update(department_to_update);

        System.out.println("--- 3 TEST --- DELETE BY ID  ");
        departmentDao.deleteById(2);

    }
}
