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
            DeviceDto deviceDto = new DeviceDto();
                deviceDto.setDeviceId(resultSet.getString(1));
                deviceDto.setDName(resultSet.getString(2));
                deviceDto.setProblem(resultSet.getString(3));
                deviceDto.setStatus(resultSet.getString(4));
                deviceDto.setCost(String.valueOf(resultSet.getString(5)));
                deviceDto.setDate(resultSet.getDate(6).toLocalDate());
                deviceDto.setCId(resultSet.getString(7));

                list.add(deviceDto);
        }
        return list;
    }

    public static List<DeviceDto> getAllDevicesByStatus(String status) throws SQLException {
        List<DeviceDto> list = new ArrayList<>();

        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM handoverDevice WHERE status = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, status);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            DeviceDto deviceDto = new DeviceDto();
            deviceDto.setDeviceId(resultSet.getString(1));
            deviceDto.setDName(resultSet.getString(2));
            deviceDto.setProblem(resultSet.getString(3));
            deviceDto.setStatus(resultSet.getString(4));
            deviceDto.setCost(String.valueOf(resultSet.getString(5)));
            deviceDto.setDate(resultSet.getDate(6).toLocalDate());
            deviceDto.setCId(resultSet.getString(7));

            list.add(deviceDto);
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
        preparedStatement.setString(6, String.valueOf(dto.getDate()));
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

    public static DeviceDto getDevice(String deviceId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM handoverDevice WHERE deviceId =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, deviceId);
        ResultSet resultSet = preparedStatement.executeQuery();
        DeviceDto deviceDto = new DeviceDto();
        while (resultSet.next()) {
            deviceDto.setDeviceId(resultSet.getString(1));
            deviceDto.setDName(resultSet.getString(2));
            deviceDto.setProblem(resultSet.getString(3));
            deviceDto.setStatus(resultSet.getString(4));
            deviceDto.setCost(String.valueOf(resultSet.getString(5)));
            deviceDto.setDate(resultSet.getDate(6).toLocalDate());
            deviceDto.setCId(resultSet.getString(7));
        }
        return deviceDto;
    }

    public static String generateNextDeviceId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT deviceId FROM handoverdevice ORDER BY deviceId DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitDeviceId(resultSet.getString(1));
        }
        return splitDeviceId(null);
    }

    private static String splitDeviceId(String currentDeviceId) {
        if(currentDeviceId != null) {
            String[] split = currentDeviceId.split("D0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            if(id < 10) {
                return "D00" + id;
            } else if (id < 100) {
                return "D0" + id;
            } else {
                return "D" + id;
            }
        } else {
            return "D001";
        }
    }
}
