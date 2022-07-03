package com.homeworkfour.persistence.dao;

import com.homeworkfour.persistence.model.Employee;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC core implementation of EmployeeDao class
 * Able to complete simple crud transactions.
 */

@Service
public class EmployeeDaoJDBCImp implements EmployeeDao<Employee>{
    @Override
    public Employee get(long id) {

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/isbasi", "postgres", "password");
             Statement stmt = conn.createStatement();

        )  {

            ResultSet rs = stmt.executeQuery("SELECT * FROM employee WHERE ID = " + id);

            while(rs.next()){
                //Display values
                System.out.print("ID: " + rs.getInt("id"));
                System.out.print(", Age: " + rs.getInt("age"));
                System.out.print(", Name: " + rs.getString("name"));
                System.out.println(", Position: " + rs.getString("position"));
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }


        return new Employee();
    }

    @Override
    public List<Employee> getAll() {
        return null;
    }

    @Override
    public Employee save(Employee employee) {

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/isbasi", "postgres", "password");
            Statement stmt = conn.createStatement();
        )  {

            System.out.println("Inserting records into the table...");
            String sql = "INSERT INTO employee VALUES (" + employee.getId() + "," + employee.getAge()+",'" + employee.getName() + "'," +  "'" + employee.getPosition() +"')" ;
            stmt.executeUpdate(sql);
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;

    }

    @Override
    public Employee update(Employee employee, long id) {

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/isbasi", "postgres", "password");
             Statement stmt = conn.createStatement();
        )  {

            System.out.println("Updating records...");
            String sql = "UPDATE employee SET name = '"+ employee.getName() +"', age = " +employee.getAge() + ", position = '" + employee.getPosition() +"' WHERE id = "+id ;
            stmt.executeUpdate(sql);
        }

        catch (SQLException e) {
            e.printStackTrace();
        }





        return employee;
    }

    @Override
    public void delete(long id) {

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/isbasi", "postgres", "password");
             Statement stmt = conn.createStatement();
        )  {

            System.out.println("Deleting records...");
            String sql = "DELETE FROM employee WHERE id = "+id ;
            stmt.executeUpdate(sql);
        }

        catch (SQLException e) {
            e.printStackTrace();
        }





    }
}
