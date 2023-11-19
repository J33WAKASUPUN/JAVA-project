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
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.time.LocalDate;

public class DashboardFormController {

    @FXML
    private Pane CustomerCountPane;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnOrders;

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnStock;

    @FXML
    private JFXButton btnSupplier;

    @FXML
    private JFXButton btnReports;

    @FXML
    private JFXButton btnDevice;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private Label lblCustomerCount;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblIncomeCount;

    @FXML
    private Label lblSales;

    @FXML
    private Label lblTodayCustomerCount;

    @FXML
    private Label lblTodayIncome;

    @FXML
    private Label lblTodaySales;

    @FXML
    private Label lblUserId;

    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane subRoot;

    public void initialize(){
        //btnDashboard.setStyle("-fx-background-color: #D3D3D3;");
        setDate();
    }


    public void btnDashboardOnActoin(ActionEvent actionEvent) throws IOException {
        setUI(root,"/view/DashboardForm.fxml");

        btnDashboard.setStyle("-fx-background-color: #D3D3D3;");
        btnOrders.setStyle("-fx-background-color: #FFFFFF;");
        btnCustomer.setStyle("-fx-background-color: #FFFFFF;");
        btnDevice.setStyle("-fx-background-color: #FFFFFF;");
        btnStock.setStyle("-fx-background-color: #FFFFFF;");
        btnSupplier.setStyle("-fx-background-color: #FFFFFF;");
        btnReports.setStyle("-fx-background-color: #FFFFFF;");
        btnEmployee.setStyle("-fx-background-color: #FFFFFF;");
    }

    public void btnOrdersOnAction(ActionEvent actionEvent) throws IOException {
        setUI(subRoot,"/view/OrderManageForm.fxml");

        btnDashboard.setStyle("-fx-background-color: #FFFFFF;");
        btnOrders.setStyle("-fx-background-color: #D3D3D3;");
        btnCustomer.setStyle("-fx-background-color: #FFFFFF;");
        btnDevice.setStyle("-fx-background-color: #FFFFFF;");
        btnStock.setStyle("-fx-background-color: #FFFFFF;");
        btnSupplier.setStyle("-fx-background-color: #FFFFFF;");
        btnReports.setStyle("-fx-background-color: #FFFFFF;");
        btnEmployee.setStyle("-fx-background-color: #FFFFFF;");
    }

    public void btnCustomersOnAction(ActionEvent actionEvent) throws IOException {
        setUI(subRoot,"/view/CustomerManageForm.fxml");

        btnDashboard.setStyle("-fx-background-color: #FFFFFF;");
        btnOrders.setStyle("-fx-background-color: #FFFFFF;");
        btnCustomer.setStyle("-fx-background-color: #D3D3D3;");
        btnDevice.setStyle("-fx-background-color: #FFFFFF;");
        btnStock.setStyle("-fx-background-color: #FFFFFF;");
        btnSupplier.setStyle("-fx-background-color: #FFFFFF;");
        btnReports.setStyle("-fx-background-color: #FFFFFF;");
        btnEmployee.setStyle("-fx-background-color: #FFFFFF;");
    }



    public void btnDeviceOnAction(ActionEvent actionEvent) throws IOException {
        setUI(subRoot,"/view/HandoverDeviceManageForm.fxml");

        btnDashboard.setStyle("-fx-background-color: #FFFFFF;");
        btnOrders.setStyle("-fx-background-color: #FFFFFF;");
        btnCustomer.setStyle("-fx-background-color: #FFFFFF;");
        btnDevice.setStyle("-fx-background-color: #D3D3D3;");
        btnStock.setStyle("-fx-background-color: #FFFFFF;");
        btnSupplier.setStyle("-fx-background-color: #FFFFFF;");
        btnReports.setStyle("-fx-background-color: #FFFFFF;");
        btnEmployee.setStyle("-fx-background-color: #FFFFFF;");
    }

    public void btnStockOnActoin(ActionEvent actionEvent) throws IOException {
        setUI(subRoot,"/view/StockManageForm.fxml");

        btnDashboard.setStyle("-fx-background-color: #FFFFFF;");
        btnOrders.setStyle("-fx-background-color: #FFFFFF;");
        btnCustomer.setStyle("-fx-background-color: #FFFFFF;");
        btnDevice.setStyle("-fx-background-color: #FFFFFF;");
        btnStock.setStyle("-fx-background-color: #D3D3D3;");
        btnSupplier.setStyle("-fx-background-color: #FFFFFF;");
        btnReports.setStyle("-fx-background-color: #FFFFFF;");
        btnEmployee.setStyle("-fx-background-color: #FFFFFF;");
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) throws IOException {
        setUI(subRoot,"/view/SupplierManageForm.fxml");

        btnDashboard.setStyle("-fx-background-color: #FFFFFF;");
        btnOrders.setStyle("-fx-background-color: #FFFFFF;");
        btnCustomer.setStyle("-fx-background-color: #FFFFFF;");
        btnDevice.setStyle("-fx-background-color: #FFFFFF;");
        btnStock.setStyle("-fx-background-color: #FFFFFF;");
        btnSupplier.setStyle("-fx-background-color: #D3D3D3;");
        btnReports.setStyle("-fx-background-color: #FFFFFF;");
        btnEmployee.setStyle("-fx-background-color: #FFFFFF;");
    }

    public void btnReportsOnAction(ActionEvent actionEvent) throws IOException {
        setUI(subRoot,"/view/ReportForm.fxml");

        btnDashboard.setStyle("-fx-background-color: #FFFFFF;");
        btnOrders.setStyle("-fx-background-color: #FFFFFF;");
        btnCustomer.setStyle("-fx-background-color: #FFFFFF;");
        btnDevice.setStyle("-fx-background-color: #FFFFFF;");
        btnStock.setStyle("-fx-background-color: #FFFFFF;");
        btnSupplier.setStyle("-fx-background-color: #FFFFFF;");
        btnReports.setStyle("-fx-background-color: #D3D3D3;");
        btnEmployee.setStyle("-fx-background-color: #FFFFFF;");
    }

    public void btnEmployeesOnAction(ActionEvent actionEvent) throws IOException {
        setUI(subRoot,"/view/EmployeeManageForm.fxml");

        btnDashboard.setStyle("-fx-background-color: #FFFFFF;");
        btnOrders.setStyle("-fx-background-color: #FFFFFF;");
        btnCustomer.setStyle("-fx-background-color: #FFFFFF;");
        btnDevice.setStyle("-fx-background-color: #FFFFFF;");
        btnStock.setStyle("-fx-background-color: #FFFFFF;");
        btnSupplier.setStyle("-fx-background-color: #FFFFFF;");
        btnReports.setStyle("-fx-background-color: #FFFFFF;");
        btnEmployee.setStyle("-fx-background-color: #D3D3D3;");
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
