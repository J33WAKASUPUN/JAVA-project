package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.SupplierDto;

import java.sql.Connection;
import java.sql.SQLException;

public class ReStockModel {
    public static boolean itemSupplier(SupplierDto supplierDto, ItemDto itemDto) throws SQLException {
        String supplierId = supplierDto.getSupId();
        String itemId = itemDto.getItemId();

        Connection connection = null;

        connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isUpdated = ItemModel.updateItemBySupply(itemDto);
            if(isUpdated){
                boolean isSaved = SupplierItemModel.saveSupplierItem(supplierId,itemId);
                if(isSaved){
                    connection.commit();
                    return true;
                }
            }
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }
}
