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

    public static UserDto getUserByName(String userName) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM user WHERE userName = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,userName);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            UserDto userDto = new UserDto();
            userDto.setUserId(resultSet.getString(1));
            userDto.setUserName(resultSet.getString(2));
            userDto.setPassword(resultSet.getString(3));
            return userDto;
        }
        return null;
    }

    public static UserDto getUserDtoList(String userId) throws SQLException {
        List<UserDto> list = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM user WHERE userId = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            UserDto userDto = new UserDto();
            userDto.setUserId(resultSet.getString(1));
            userDto.setUserName(resultSet.getString(2));
            userDto.setPassword(resultSet.getString(3));
            return userDto;
        }
        return null;
    }

    public static boolean setUser(UserDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO user VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,dto.getUserId());
        preparedStatement.setObject(2,dto.getUserName());
        preparedStatement.setObject(3,dto.getPassword());
        preparedStatement.setObject(4,dto.getEmail());
        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean updateUser (String password, String userId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "Update user SET password = ? WHERE userId =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, password);
        preparedStatement.setString(2, userId);
        return preparedStatement.executeUpdate() > 0;
    }

    public static String generateNextUserId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT userId FROM user ORDER BY userId DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitUserId(resultSet.getString(1));
        }
        return splitUserId(null);
    }

    private static String splitUserId(String currentUserId) {
        if(currentUserId != null) {
            String[] split = currentUserId.split("U0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            if(id < 10) {
                return "U00" + id;
            } else if (id < 100) {
                return "U0" + id;
            } else {
                return "U" + id;
            }
        } else {
            return "U001";
        }
    }
}
