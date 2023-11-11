package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.OrderDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersModel {
    public static List<OrderDto> getAllOrders() throws SQLException {
        List<OrderDto> list = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM orders";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            OrderDto ordersDto = new OrderDto();
            ordersDto.setOrderId(resultSet.getString(1));
            ordersDto.setQty(resultSet.getString(2));
            ordersDto.setDate(resultSet.getString(3));
            ordersDto.setPrice(resultSet.getString(4));
            ordersDto.setCId(resultSet.getString(5));
            ordersDto.setPId(resultSet.getString(6));
            ordersDto.setDeliveryId(resultSet.getString(7));
            list.add(ordersDto);
        }
        return list;
    }

    public static boolean setOrder(OrderDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO orders VALUES (?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,dto.getOrderId());
        preparedStatement.setObject(2,dto.getQty());
        preparedStatement.setObject(3,dto.getDate());
        preparedStatement.setObject(4,dto.getPrice());
        preparedStatement.setObject(5,dto.getCId());
        preparedStatement.setObject(6,dto.getPId());
        preparedStatement.setObject(7,dto.getDeliveryId());
        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean updateOrder(OrderDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "Update orders SET qty =?, date =?, price =? WHERE orderId =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, dto.getQty());
        preparedStatement.setString(2, dto.getDate());
        preparedStatement.setString(3, dto.getPrice());
        preparedStatement.setString(4, dto.getOrderId());
        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean deleteOrder(String orderId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM orders WHERE orderId =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, orderId);
        return preparedStatement.executeUpdate() > 0;
    }
}
