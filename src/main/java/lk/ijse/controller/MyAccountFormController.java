package lk.ijse.controller;

import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    void btnSaveOnAction(ActionEvent event) {

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
