package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.time.LocalDate;

public class DashboardFormController {

    @FXML
    private Pane CustomerCountPane;
    public JFXButton btnOrders;
    public AnchorPane subRoot;
    public JFXButton btnDashboard;
    public AnchorPane root;
    public AnchorPane subRootAcc;
    public JFXButton btnMyAccount;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblUserId;

    public void initialize(){
        setDate();
    }


    public void btnDashboardOnActoin(ActionEvent actionEvent) throws IOException {
        setUI(root,"/view/DashboardForm.fxml");
    }

    public void btnOrdersOnAction(ActionEvent actionEvent) throws IOException {
        setUI(subRoot,"/view/OrderManageForm.fxml");
    }

    public void btnCustomersOnAction(ActionEvent actionEvent) throws IOException {
        setUI(subRoot,"/view/CustomerManageForm.fxml");
    }



    public void btnDeviceOnAction(ActionEvent actionEvent) throws IOException {
        setUI(subRoot,"/view/HandoverDeviceManageForm.fxml");
    }

    public void btnStockOnActoin(ActionEvent actionEvent) throws IOException {
        setUI(subRoot,"/view/StockManageForm.fxml");
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) throws IOException {
        setUI(subRoot,"/view/SupplierManageForm.fxml");
    }

    public void btnReportsOnAction(ActionEvent actionEvent) throws IOException {
        setUI(subRoot,"/view/ReportForm.fxml");
    }

    public void btnEmployeesOnAction(ActionEvent actionEvent) throws IOException {
        setUI(subRoot,"/view/EmployeeManageForm.fxml");
    }


    public void setUI (AnchorPane pane, String location) throws IOException {
        pane.getChildren().clear();
        pane.getChildren().add(FXMLLoader.load(getClass().getResource(location)));
    }


    public void btnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Scene scene =new Scene(parent);

        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public void btnAccountSettingOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/MyAccountForm.fxml"));
        Scene scene =new Scene(parent);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Customer");
        stage.show();
        //btnMyAccount.setDisable(true);
    }

    /*public void btnMyAccountSetDisable (Boolean value) {
        btnMyAccount.setDisable(value);
    }*/

    private void setDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
    }
}
