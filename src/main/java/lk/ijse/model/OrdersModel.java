package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.OrderDto;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdersModel {
    public static List<OrderDto> getAllOders() throws SQLException {
        List<OrderDto> list = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM orders");
        ResultSet resultSet = ps.executeQuery();
        while(resultSet.next()){
            OrderDto orderDto= new OrderDto();

            orderDto.setCId(resultSet.getString(1));
            orderDto.setDate(LocalDate.parse(resultSet.getString(2)));
            orderDto.setCId(resultSet.getString(3));
            list.add(orderDto);
        }
        return list;
    }

    public static List<OrderDto> getAllOdersByDate(String date) throws SQLException {
        List<OrderDto> list = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM orders WHERE date = ?");
        ps.setString(1, date);
        ResultSet resultSet = ps.executeQuery();
        while(resultSet.next()){
            OrderDto orderDto= new OrderDto();

            orderDto.setCId(resultSet.getString(1));
            orderDto.setDate(LocalDate.parse(resultSet.getString(2)));
            orderDto.setCId(resultSet.getString(3));
            list.add(orderDto);
        }
        return list;
    }
    public static boolean saveOrder(String orderId,LocalDate date, String customerId ) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO orders VALUES(?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, orderId);
        pstm.setDate(2, Date.valueOf(date));
        pstm.setString(3, customerId);

        return pstm.executeUpdate() > 0;
    }

    public static String generateNextOrderId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentOrderId) {
        if(currentOrderId != null) {
            String[] split = currentOrderId.split("O0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            if(id < 10) {
                return "O00" + id;
            } else if (id < 100) {
                return "O0" + id;
            } else {
                return "O" + id;
            }
        } else {
            return "O001";
        }
    }
}
