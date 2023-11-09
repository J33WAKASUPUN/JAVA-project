package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.ItemDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {
    public static List<ItemDto> getAllItems() throws SQLException {
        List<ItemDto> list = new ArrayList<>();

        Connection connection = DBConnection.getInstance().getConnection();
        String sql= "SELECT * FROM item";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet= preparedStatement.executeQuery();
        while (resultSet.next()){
            ItemDto itemDto = new ItemDto();
            itemDto.setItemId(resultSet.getString(1));
            itemDto.setItemName(resultSet.getString(2));
            itemDto.setQtyOnHand(resultSet.getString(3));
            itemDto.setCost(resultSet.getString(4));
            itemDto.setUnitPrice(resultSet.getString(5));
            list.add(itemDto);
        }
        return list;
    }

    public static boolean setItem(ItemDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql= "INSERT INTO item VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,dto.getItemId());
        preparedStatement.setObject(2,dto.getItemName());
        preparedStatement.setObject(3,dto.getQtyOnHand());
        preparedStatement.setObject(4,dto.getCost());
        preparedStatement.setObject(5,dto.getUnitPrice());
        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean updateItem(ItemDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "Update item SET itemName=?, qtyOnHand =?, cost =?, unitPrice =? WHERE itemId =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, dto.getItemId());
        preparedStatement.setString(2, dto.getItemName());
        preparedStatement.setString(3, dto.getQtyOnHand());
        preparedStatement.setString(4, dto.getCost());
        preparedStatement.setString(5, dto.getUnitPrice());
        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean deleteItem(String itemId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM item WHERE itemId =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, itemId);
        return preparedStatement.executeUpdate() > 0;
    }
}
