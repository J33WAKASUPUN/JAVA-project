package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.OrderDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class PlaceOrderModel {
    private OrdersModel orderModel = new OrdersModel();
    private ItemModel itemModel = new ItemModel();
    private OrderItemModel orderItemModel = new OrderItemModel();

    public boolean placeOrder(OrderDto orderDto) throws SQLException {

        String orderId = orderDto.getOrderId();
        String customerId = orderDto.getCId();
        LocalDate date = orderDto.getDate();

        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = orderModel.saveOrder(orderId, date ,customerId);
            if (isOrderSaved) {
                boolean isUpdated = itemModel.updateItem(orderDto.getOrderTmList());
                if(isUpdated) {
                    boolean isOrderDetailSaved = orderItemModel.saveOrderDetails(orderDto.getOrderId(), orderDto.getOrderTmList());
                    if (isOrderDetailSaved) {
                        connection.commit();
                    }
                }
            }
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }

        return true;
    }
}
