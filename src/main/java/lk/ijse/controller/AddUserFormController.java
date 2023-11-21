package lk.ijse.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dto.UserDto;
import lk.ijse.model.OrdersModel;
import lk.ijse.model.UserModel;

import javax.management.Notification;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.sql.SQLException;

public class AddUserFormController {

    @FXML
    private Label lblEmployeeDetails;

    @FXML
    private Label lblPasswordDoesNotMatch;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXPasswordField txtRePassword;

    @FXML
    private JFXTextField txtUserId;

    @FXML
    private JFXTextField txtUserName;

    public void initialize(){
        generateNextUserId();
        lblPasswordDoesNotMatch.setVisible(false);
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        ((Stage) root.getScene().getWindow()).close();
    }

    @FXML
    void btnComfirmOnAction(ActionEvent event) {
        String userName = txtUserName.getText();
        String userId = txtUserId.getText();
        String email = txtEmail.getText();
        String fristpassword = txtPassword.getText();
        String rePassword = txtRePassword.getText();
        String password = "";

        if(userName.isEmpty() || userId.isEmpty() || email.isEmpty() || fristpassword.isEmpty() || rePassword.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill all fields");
            alert.showAndWait();
            return;
        }

        if (fristpassword.equals(rePassword)){
            password=fristpassword;
        } else {
            Paint unFocusColor = Paint.valueOf("#BA181B");
            lblPasswordDoesNotMatch.setVisible(true);
            txtRePassword.setUnFocusColor(unFocusColor);
            return;
        }

        UserDto dto = new UserDto(userId,userName,password,email);

        try {
            boolean isAdded = UserModel.setUser(dto);
            if (isAdded) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Succsess");
                alert.showAndWait();
                ((Stage) root.getScene().getWindow()).close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextUserId() {
        try {
            String userId = UserModel.generateNextUserId();
            txtUserId.setText(userId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}

