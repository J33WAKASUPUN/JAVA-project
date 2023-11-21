package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class EmployeeModel {
    public static List<EmployeeDto> getAllEmployees() throws SQLException {
        List<EmployeeDto> list = new ArrayList<>();

        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            EmployeeDto employeeDto = new EmployeeDto();
                employeeDto.setEmpId(resultSet.getString(1));
                employeeDto.setName(resultSet.getString(2));
                employeeDto.setAddress(resultSet.getString(3));
                employeeDto.setPosition(resultSet.getString(4));
                employeeDto.setContact(resultSet.getString(5));
                employeeDto.setSalary(resultSet.getString(6));
                employeeDto.setUserId(resultSet.getString(7));

                list.add(employeeDto);
        }
        return list;
    }

    public static boolean saveEmployee(EmployeeDto employeeDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO employee VALUES (?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, employeeDto.getEmpId());
        preparedStatement.setString(2, employeeDto.getName());
        preparedStatement.setString(3, employeeDto.getAddress());
        preparedStatement.setString(4, employeeDto.getPosition());
        preparedStatement.setString(5, employeeDto.getContact());
        preparedStatement.setString(6, employeeDto.getSalary());
        preparedStatement.setString(7, employeeDto.getUserId());
        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean updateEmployee(EmployeeDto employeeDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE employee SET name=?, address=?, position=?, contact=?, salary=?, userId=? WHERE empId=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, employeeDto.getName());
        preparedStatement.setString(2, employeeDto.getAddress());
        preparedStatement.setString(3, employeeDto.getPosition());
        preparedStatement.setString(4, employeeDto.getContact());
        preparedStatement.setString(5, employeeDto.getSalary());
        preparedStatement.setString(6, employeeDto.getUserId());
        preparedStatement.setString(7, employeeDto.getEmpId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean deleteEmployee(String empId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM employee WHERE empId=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, empId);
        return preparedStatement.executeUpdate() > 0;
    }

    public static EmployeeDto getEmployee(String empId) throws SQLException {
        EmployeeDto employeeDto = new EmployeeDto();

        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee WHERE empId=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, empId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            employeeDto.setEmpId(resultSet.getString(1));
            employeeDto.setName(resultSet.getString(2));
            employeeDto.setAddress(resultSet.getString(3));
            employeeDto.setPosition(resultSet.getString(4));
            employeeDto.setContact(resultSet.getString(5));
            employeeDto.setSalary(resultSet.getString(6));
            employeeDto.setUserId(resultSet.getString(7));
        }
        return employeeDto;
    }

    public static String generateNextEmployeeId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT empId FROM employee ORDER BY empId DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitEmployeeId(resultSet.getString(1));
        }
        return splitEmployeeId(null);
    }

    private static String splitEmployeeId(String currentEmployeeId) {
        if(currentEmployeeId != null) {
            String[] split = currentEmployeeId.split("E0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            if(id < 10) {
                return "E00" + id;
            } else if (id < 100) {
                return "E0" + id;
            } else {
                return "E" + id;
            }
        } else {
            return "E001";
        }
    }
}
