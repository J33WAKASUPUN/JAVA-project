package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.tm.OrderTm;

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
        preparedStatement.setString(1, dto.getItemName());
        preparedStatement.setString(2, dto.getQtyOnHand());
        preparedStatement.setString(3, dto.getCost());
        preparedStatement.setString(4, dto.getUnitPrice());
        preparedStatement.setString(5, dto.getItemId());
        return preparedStatement.executeUpdate() > 0;
    }

    public  static  boolean updateItemBySupply(ItemDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "Update item SET qtyOnHand = qtyOnHand + ?, cost =? WHERE itemId =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, dto.getQtyOnHand());
        preparedStatement.setString(2, dto.getCost());
        preparedStatement.setString(3, dto.getItemId());
        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean updateItem(List<OrderTm> orderTmList) throws SQLException {
        for(OrderTm tm : orderTmList) {
            System.out.println("Item: " + tm);
            if(!updateQty(tm.getItemId(), tm.getQty())) {
                return false;
            }
        }
        return true;
    }

    public static boolean updateQty(String code, int qty) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE item SET qtyOnHand = qtyOnHand - ? WHERE itemId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setString(2, code);

        return pstm.executeUpdate() > 0; //false
    }

    public static boolean deleteItem(String itemId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM item WHERE itemId =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, itemId);
        return preparedStatement.executeUpdate() > 0;
    }

    public static ItemDto getItem(String itemName) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM item WHERE itemName = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, itemName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            ItemDto itemDto = new ItemDto();
            itemDto.setItemId(resultSet.getString(1));
            itemDto.setItemName(resultSet.getString(2));
            itemDto.setQtyOnHand(resultSet.getString(3));
            itemDto.setCost(resultSet.getString(4));
            itemDto.setUnitPrice(resultSet.getString(5));
            return itemDto;
        } else {
            return null;
        }
    }

    public static ItemDto getItemById (String id) throws SQLException {
        Connection connection =DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM item WHERE itemId =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            ItemDto itemDto = new ItemDto();
            itemDto.setItemId(resultSet.getString(1));
            itemDto.setItemName(resultSet.getString(2));
            itemDto.setQtyOnHand(resultSet.getString(3));
            itemDto.setCost(resultSet.getString(4));
            itemDto.setUnitPrice(resultSet.getString(5));
            return itemDto;
        } else {
            return null;
        }
    }

    public static String generateNextItemId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT itemId FROM item ORDER BY itemId DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitItemId(resultSet.getString(1));
        }
        return splitItemId(null);
    }

    private static String splitItemId(String currentItemId) {
        if(currentItemId != null) {
            String[] split = currentItemId.split("I0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            if(id < 10) {
                return "I00" + id;
            } else if (id < 100) {
                return "I0" + id;
            } else {
                return "I" + id;
            }
        } else {
            return "I001";
        }
    }
}
