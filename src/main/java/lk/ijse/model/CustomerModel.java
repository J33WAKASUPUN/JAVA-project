package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.CustomerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {
    public static List<CustomerDto> getAllCustomer() throws SQLException {
        List<CustomerDto> list = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM customer");
        ResultSet resultSet = ps.executeQuery();
        while(resultSet.next()){
            CustomerDto customerDto = new CustomerDto();

            customerDto.setCId(resultSet.getString(1));
            customerDto.setName(resultSet.getString(2));
            customerDto.setEmail(resultSet.getString(3));
            customerDto.setAddress(resultSet.getString(4));
            customerDto.setContact(resultSet.getString(5));
            customerDto.setUserId(resultSet.getString(6));
            list.add(customerDto);
        }
        return list;
    }

    public static boolean setCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql= "INSERT INTO customer VALUES (?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1,dto.getCId());
            preparedStatement.setObject(2,dto.getName());
            preparedStatement.setObject(3,dto.getEmail());
            preparedStatement.setObject(4,dto.getAddress());
            preparedStatement.setObject(5,dto.getContact());
            preparedStatement.setObject(6,dto.getUserId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean updateCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "Update customer SET name=?, email = ?, address = ?, contact = ?, userId= ? WHERE cId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, dto.getName());
        preparedStatement.setString(2, dto.getEmail());
        preparedStatement.setString(3, dto.getAddress());
        preparedStatement.setString(4, dto.getContact());
        preparedStatement.setString(5, dto.getUserId());
        preparedStatement.setString(6, dto.getCId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean deleteCustomer (String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM customer WHERE cId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id );
        return preparedStatement.executeUpdate() > 0;
    }

    public static CustomerDto getCustomer(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM customer WHERE cId =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id );
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            CustomerDto customerDto = new CustomerDto();
            customerDto.setCId(resultSet.getString(1));
            customerDto.setName(resultSet.getString(2));
            customerDto.setEmail(resultSet.getString(3));
            customerDto.setAddress(resultSet.getString(4));
            customerDto.setContact(resultSet.getString(5));
            customerDto.setUserId(resultSet.getString(6));
            return customerDto;
        }
        return null;
    }

    public static CustomerDto getCustomerByName(String name) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM customer WHERE name =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,name );
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            CustomerDto customerDto = new CustomerDto();
            customerDto.setCId(resultSet.getString(1));
            customerDto.setName(resultSet.getString(2));
            customerDto.setEmail(resultSet.getString(3));
            customerDto.setAddress(resultSet.getString(4));
            customerDto.setContact(resultSet.getString(5));
            customerDto.setUserId(resultSet.getString(6));
            return customerDto;
        }
        return null;
    }

    public static String generateNextCustomerId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT cId FROM customer ORDER BY cId DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitCustomerId(resultSet.getString(1));
        }
        return splitCustomerId(null);
    }

    private static String splitCustomerId(String currentCustomerId) {
        if(currentCustomerId != null) {
            String[] split = currentCustomerId.split("C0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            if(id < 10) {
                return "C00" + id;
            } else if (id < 100) {
                return "C0" + id;
            } else {
                return "C" + id;
            }
        } else {
            return "C001";
        }
    }
}
