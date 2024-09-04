package com.dk.bussiness;

import com.dk.modules.employee;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class employeeBoImplementation implements EmployeeBO {

    private Connection connection;
    private PreparedStatement prepareStatement;
    private Statement statement;
    private ResultSet res;

    private final static String INSERT_QUERY = "insert into employee (id, name, email, dept, salary) values(?, ?, ?, ?, ?)";
    private final static String DELETE_QUERY = "delete from employee where id = ?";
    private final static String UPDATE_QUERY = "update employee set id = ?, name = ?, email = ?, dept = ?, salary = ? where id = ?";
    private final static String SELECT_QUERY = "select * from employee where id = ?";
    private final static String SELECT_ALL_QUERY = "select * from employee";

    public employeeBoImplementation() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcclasses", "root", "root@dk"); // Replace with actual username and password
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int save(employee e) {
        int result = 0;
        try {
            prepareStatement = connection.prepareStatement(INSERT_QUERY);
            prepareStatement.setInt(1, e.getId());
            prepareStatement.setString(2, e.getName());
            prepareStatement.setString(3, e.getEmail());
            prepareStatement.setString(4, e.getDepartment());
            prepareStatement.setInt(5, e.getSalary());

            result = prepareStatement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(int id) {
        int result = 0;
        try {
            prepareStatement = connection.prepareStatement(DELETE_QUERY);
            prepareStatement.setInt(1, id);
            result = prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(employee e) {
        return delete(e.getId());
    }

    @Override
    public int update(employee e) {
        int result = 0;
        try {
            prepareStatement = connection.prepareStatement(UPDATE_QUERY);
            prepareStatement.setInt(1, e.getId());
            prepareStatement.setString(2, e.getName());
            prepareStatement.setString(3, e.getEmail());
            prepareStatement.setString(4, e.getDepartment());
            prepareStatement.setInt(5, e.getSalary());
            prepareStatement.setInt(6, e.getId());

            result = prepareStatement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return result;
    }

    @Override
    public List<employee> getAll() {
        List<employee> employees = new ArrayList<>();
        try {
            statement = connection.createStatement();
            res = statement.executeQuery(SELECT_ALL_QUERY);

            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                String email = res.getString("email");
                String dept = res.getString("dept");
                int salary = res.getInt("salary");

                employee e = new employee(id, name, email, dept, salary);
                employees.add(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public employee get(int id) {
        employee emp = null;
        try {
            prepareStatement = connection.prepareStatement(SELECT_QUERY);
            prepareStatement.setInt(1, id);
            res = prepareStatement.executeQuery();

            if (res.next()) {
                int empId = res.getInt("id");
                String name = res.getString("name");
                String email = res.getString("email");
                String dept = res.getString("dept");
                int salary = res.getInt("salary");

                emp = new employee(empId, name, email, dept, salary);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emp;
    }

    // Clean up resources
    public void close() {
        try {
            if (res != null) res.close();
            if (statement != null) statement.close();
            if (prepareStatement != null) prepareStatement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
