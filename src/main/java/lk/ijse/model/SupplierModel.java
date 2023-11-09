package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.SupplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {
    public static List<SupplierDto> getAllSuppliers() throws SQLException {
        List<SupplierDto> list = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            SupplierDto supplierDto = new SupplierDto();
            supplierDto.setSupId(resultSet.getString(1));
            supplierDto.setSName(resultSet.getString(2));
            supplierDto.setAddress(resultSet.getString(3));
            supplierDto.setContact(resultSet.getString(4));
            list.add(supplierDto);
        }
        return list;
    }

    public static boolean setSupplier(SupplierDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO supplier VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,dto.getSupId());
        preparedStatement.setObject(2,dto.getSName());
        preparedStatement.setObject(3,dto.getAddress());
        preparedStatement.setObject(4,dto.getContact());
        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean updateSupplier(SupplierDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "Update supplier SET sName=?, address =?, contact =? WHERE supId =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, dto.getSName());
        preparedStatement.setString(2, dto.getAddress());
        preparedStatement.setString(3, dto.getContact());
        preparedStatement.setString(4, dto.getSupId());
        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean deleteSupplier(String supId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM supplier WHERE supId =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, supId);
        return preparedStatement.executeUpdate() > 0;
    }
}
