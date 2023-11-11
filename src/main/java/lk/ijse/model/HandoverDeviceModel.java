package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.DeviceDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HandoverDeviceModel {
    public static List<DeviceDto> getAllDevices() throws SQLException {
        List<DeviceDto> list = new ArrayList<>();

        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM handoverDevice";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            list.add(new DeviceDto());
            DeviceDto deviceDto = new DeviceDto();
                deviceDto.setDeviceId(resultSet.getString(1));
                deviceDto.setDName(resultSet.getString(2));
                deviceDto.setProblem(resultSet.getString(3));
                deviceDto.setStatus(resultSet.getString(4));
                deviceDto.setCost(resultSet.getString(5));
                deviceDto.setDate(resultSet.getString(6));
                deviceDto.setCId(resultSet.getString(7));
        }
        return list;
    }

    public static boolean setDevice(DeviceDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO handoverDevice VALUES (?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, dto.getDeviceId());
        preparedStatement.setObject(2, dto.getDName());
        preparedStatement.setObject(3, dto.getProblem());
        preparedStatement.setObject(4, dto.getStatus());
        preparedStatement.setObject(5, dto.getCost());
        preparedStatement.setObject(6, dto.getDate());
        preparedStatement.setObject(7, dto.getCId());
        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean updateDevice(DeviceDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "Update handoverDevice SET dName =?, problem =?, status =?, cost =?, date =?, cId =? WHERE deviceId =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, dto.getDeviceId());
        preparedStatement.setString(2, dto.getDName());
        preparedStatement.setString(3, dto.getProblem());
        preparedStatement.setString(4, dto.getStatus());
        preparedStatement.setString(5, dto.getCost());
        preparedStatement.setString(6, dto.getDate());
        preparedStatement.setString(7, dto.getCId());
        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean deleteDevice(String deviceId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM handoverDevice WHERE deviceId =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, deviceId);
        return preparedStatement.executeUpdate() > 0;
    }
}
