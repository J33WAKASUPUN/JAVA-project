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
}
