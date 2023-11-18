package lk.ijse.model;

import lk.ijse.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SupplierItemModel {
    public static boolean saveSupplierItem(String supplierId , String itemId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO itemSupplierDetail VALUES(?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, itemId);
        pstm.setString(2, supplierId);

        return pstm.executeUpdate() > 0;
    }
}
