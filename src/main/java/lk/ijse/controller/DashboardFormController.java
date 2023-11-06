package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class DashboardFormController {
    public JFXButton btnOrders;
    public AnchorPane subRoot;
    public JFXButton btnDashboard;
    public AnchorPane root;
    public AnchorPane subRootAcc;
    public JFXButton btnMyAccount;

    public void initializer(){

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
}
