package lk.ijse.controller;

import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import lk.ijse.dto.UserDto;
import lk.ijse.model.UserModel;

import java.sql.SQLException;

public class MyAccountFormController {

    @FXML
    private Label lblCurrentPassword;

    @FXML
    private Label lblInvaliedPassword;

    @FXML
    private Label lblName;

    @FXML
    private Label lblNewPassword;

    @FXML
    private Label lblPasswordDoesNotMatch;

    @FXML
    private Label lblPosition;

    @FXML
    private Label lblReEnterPassword;

    @FXML
    private Label lblUserName;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXPasswordField txtCurrentPassword;

    @FXML
    private JFXPasswordField txtNewPassword;

    @FXML
    private JFXPasswordField txtReEnterPassword;

    public void initialize (){
        setDisableTrue();
        lblInvaliedPassword.setVisible(false);
        lblPasswordDoesNotMatch.setVisible(false);
    }

    @FXML
    void btnChangePasswordOnAction(ActionEvent event) {
        setDisableFalse();
    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        ((Stage) root.getScene().getWindow()).close();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String userId = "U001";
        String currentPassword = txtCurrentPassword.getText();
        String newPassword = txtNewPassword.getText();
        String reEnterPassword = txtReEnterPassword.getText();

        UserDto userDto = UserModel.getUserDtoList(userId);

        if(userDto.getPassword().equals(currentPassword)){
            if(newPassword.equals(reEnterPassword)){
                boolean isUpdated = UserModel.updateUser(newPassword, userId);
                if(isUpdated){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Password updated");
                    alert.showAndWait();
                    Paint unFocusColor = Paint.valueOf("#0B090A");
                    lblInvaliedPassword.setVisible(false);
                    lblPasswordDoesNotMatch.setVisible(false);
                    txtCurrentPassword.setUnFocusColor(unFocusColor);
                    txtReEnterPassword.setUnFocusColor(unFocusColor);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Password not updated");
                    alert.showAndWait();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "Password does not match");
                alert.showAndWait();
                Paint unFocusColor = Paint.valueOf("#BA181B");
                lblPasswordDoesNotMatch.setVisible(true);
                txtReEnterPassword.setUnFocusColor(unFocusColor);
                return;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalied Current Password");
            alert.showAndWait();
            Paint unFocusColor = Paint.valueOf("#BA181B");
            lblInvaliedPassword.setVisible(true);
            txtCurrentPassword.setUnFocusColor(unFocusColor);
            return;
        }


    }

    private void setDisableTrue(){
        lblCurrentPassword.setDisable(true);
        lblInvaliedPassword.setDisable(true);
        lblNewPassword.setDisable(true);
        lblPasswordDoesNotMatch.setDisable(true);
        lblReEnterPassword.setDisable(true);
        txtCurrentPassword.setDisable(true);
        txtNewPassword.setDisable(true);
        txtReEnterPassword.setDisable(true);
    }

    private void setDisableFalse(){
        lblCurrentPassword.setDisable(false);
        lblInvaliedPassword.setDisable(false);
        lblNewPassword.setDisable(false);
        lblPasswordDoesNotMatch.setDisable(false);
        lblReEnterPassword.setDisable(false);
        txtCurrentPassword.setDisable(false);
        txtNewPassword.setDisable(false);
        txtReEnterPassword.setDisable(false);
    }

}
