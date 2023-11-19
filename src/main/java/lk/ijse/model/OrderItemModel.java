package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.tm.OrderTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderItemModel {
    private static boolean saveOrderDetails(String orderId, OrderTm tm) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO orderItemDetail VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setInt(1, tm.getQty());
        pstm.setDouble(2, tm.getUnitPrice());
        pstm.setString(3, tm.getItemId());
        pstm.setString(4, orderId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean saveOrderDetails(String orderId, List<OrderTm> cartTmList) throws SQLException {
        for(OrderTm tm : cartTmList) {
            if(!saveOrderDetails(orderId, tm)) {
                return false;
            }
        }
        return true;
    }
}
