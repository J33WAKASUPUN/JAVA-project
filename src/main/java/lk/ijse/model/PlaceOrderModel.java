package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.OrderDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class PlaceOrderModel {
    private static OrdersModel orderModel = new OrdersModel();
    private static ItemModel itemModel = new ItemModel();
    private static OrderItemModel orderItemModel = new OrderItemModel();

    public static boolean placeOrder(OrderDto orderDto) throws SQLException {

        String orderId = orderDto.getOrderId();
        String customerId = orderDto.getCId();
        LocalDate date = orderDto.getDate();

        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = OrdersModel.saveOrder(orderId, date, customerId);
            if (isOrderSaved) {
                boolean isUpdated = ItemModel.updateItem(orderDto.getOrderTmList());
                if(isUpdated) {
                    boolean isOrderDetailSaved = OrderItemModel.saveOrderDetails(orderDto.getOrderId(), orderDto.getOrderTmList());
                    if (isOrderDetailSaved) {
                        connection.commit();
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }

        return false;
    }
}
