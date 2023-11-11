package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.PaymentDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class PaymentModel {
    public static List<PaymentDto> getAllPayments() throws SQLException {
        List<PaymentDto> list = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM payment";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setPId(resultSet.getString(1));
            paymentDto.setAmount(resultSet.getString(2));
            paymentDto.setStatus(resultSet.getString(3));
            paymentDto.setDate(resultSet.getString(4));
            list.add(paymentDto);
        }
        return list;
    }

    public static boolean setPayment(PaymentDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO payment VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,dto.getPId());
        preparedStatement.setObject(2,dto.getAmount());
        preparedStatement.setObject(3,dto.getStatus());
        preparedStatement.setObject(4,dto.getDate());
        return preparedStatement.executeUpdate() > 0;
    }
}
