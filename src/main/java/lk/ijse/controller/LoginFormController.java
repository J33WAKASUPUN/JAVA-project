package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.dto.UserDto;
import lk.ijse.model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {

    public AnchorPane root;
    public JFXButton btnLogin;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private Label lblPasswordIncorrect;

    @FXML
    private Label lblUsernameIncorrect;

    String userId = null;

    public void initialize(){
        lblUsernameIncorrect.setVisible(false);
        lblPasswordIncorrect.setVisible(false);
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        if (passwordVerify()) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DashboardForm.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
    }

    public boolean passwordVerify() {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        try {
            UserDto userDto = UserModel.getUserByName(userName);
            if (userDto != null) {
                if (userDto.getPassword().equals(password)) {

                    userId=userDto.getUserId();

                    lblPasswordIncorrect.setVisible(false);
                    lblUsernameIncorrect.setVisible(false);
                    Paint unFocusColor = Paint.valueOf("#4d4d4d");
                    txtUserName.setUnFocusColor(unFocusColor);
                    txtPassword.setUnFocusColor(unFocusColor);
                    return true;
                } else {
                    lblUsernameIncorrect.setVisible(false);
                    lblPasswordIncorrect.setVisible(true);
                    Paint unFocusColor = Paint.valueOf("#BA181B");
                    txtPassword.setUnFocusColor(unFocusColor);
                    Paint unFocusColor2 = Paint.valueOf("#4d4d4d");
                    txtUserName.setUnFocusColor(unFocusColor2);
                    return false;
                }
            } else {
                lblUsernameIncorrect.setVisible(true);
                Paint unFocusColor = Paint.valueOf("#BA181B");
                txtUserName.setUnFocusColor(unFocusColor);
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}