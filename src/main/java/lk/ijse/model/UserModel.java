package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {
    public static List<UserDto> getAllUsers() throws SQLException {
        List<UserDto> list = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM user";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            UserDto userDto = new UserDto();
            userDto.setUserId(resultSet.getString(1));
            userDto.setUserName(resultSet.getString(2));
            userDto.setPassword(resultSet.getString(3));
            list.add(userDto);
        }
        return list;
    }

    public static boolean setUser(UserDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO user VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,dto.getUserId());
        preparedStatement.setObject(2,dto.getUserName());
        preparedStatement.setObject(3,dto.getPassword());
        preparedStatement.setObject(4,dto.getEmail());
        preparedStatement.setObject(5,dto.getImg());
        return preparedStatement.executeUpdate() > 0;
    }
}
