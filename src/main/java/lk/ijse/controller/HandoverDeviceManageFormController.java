package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.DeviceDto;
import lk.ijse.dto.tm.DeviceTm;
import lk.ijse.dto.tm.StockTm;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.HandoverDeviceModel;
import lk.ijse.model.OrdersModel;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class HandoverDeviceManageFormController {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXComboBox<String> cmbCustomerName;

    @FXML
    private JFXComboBox<String> cmbStatus;

    @FXML
    private TableColumn<?, ?> colCost;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDeviceName;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colProblem;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableView<DeviceTm> tblDevice;

    @FXML
    private JFXTextField txtCost;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtProblem;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXTextField txtCustomerID;

    @FXML
    private Label lblOrderDate;

    public void initialize(){
        setCellValueFactory();
        generateNextDeviceId();
        loadAllItems();
        setCmbItems();
        loadCustomerNames();
        setDate();
        tableListener();
    }

    public void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("deviceId"));
        colDeviceName.setCellValueFactory(new PropertyValueFactory<>("dName"));
        colProblem.setCellValueFactory(new PropertyValueFactory<>("problem"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    public void loadAllItems(){
        ObservableList<DeviceTm> obList = FXCollections.observableArrayList();

        try {
            List<DeviceDto> dtolist = HandoverDeviceModel.getAllDevices();

            for(DeviceDto dto : dtolist){
                obList.add(
                        new DeviceTm(
                                dto.getDeviceId(),
                                dto.getDName(),
                                dto.getProblem(),
                                dto.getStatus(),
                                dto.getCost(),
                                dto.getDate()
                        )
                );
            }
            tblDevice.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerNames() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> idList = CustomerModel.getAllCustomer();

            for (CustomerDto dto : idList) {
                obList.add(dto.getName());
            }

            cmbCustomerName.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) {
        String name = cmbCustomerName.getValue();

        if (name!= null &&!name.isEmpty()) {
            try {
                CustomerDto customerDto = CustomerModel.getCustomerByName(name);
                txtCustomerID.setText(customerDto.getCId());

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Customer name is null or empty");
        }
    }

    public void setCmbItems(){
        ObservableList<String> obList = FXCollections.observableArrayList();

        String repairing = "Repairing";
        String repaired = "Repaired";

        obList.add(repairing);
        obList.add(repaired);

        cmbStatus.setItems(obList);
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            String id = txtId.getText();
            String name = txtName.getText();
            String problem = txtProblem.getText();
            String status = cmbStatus.getValue();
            String cost = txtCost.getText();
            LocalDate date = LocalDate.parse(lblOrderDate.getText());
            String cId = txtCustomerID.getText();

            if(id.isEmpty() || name.isEmpty() || cost.isEmpty() || problem.isEmpty() || status.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Fill all fields");
                alert.showAndWait();
                return;
            }

            if(!validateDevice()){
                return;
            }

            DeviceDto deviceDto = new DeviceDto(id, name, problem, status, cost, date ,cId);

            boolean isAdded = HandoverDeviceModel.setDevice(deviceDto);
            if (isAdded) {
                Notifications.create()
                        .graphic(new ImageView(new Image("/icons/icons8-check-mark-48.png")))
                        .text("Device Added")
                        .title("success")
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .show();
                loadAllItems();
                clearFields();
            }else {
                BoxBlur blur = new BoxBlur(3, 3, 1);
                root.setEffect(blur);
                Alert alert = new Alert(Alert.AlertType.ERROR, "Somthing went wrong");
                alert.showAndWait();
                root.setEffect(null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validateDevice(){

        boolean matches = Pattern.matches("[D][0-9]{3,}", txtId.getText());
        if (!matches){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid device id");
            alert.showAndWait();
            return false;
        }

        boolean matches2 = Pattern.matches("\\d{1,}(?:[.,]\\d{1})*(?:[.,]\\d{2})?", txtCost.getText());
        if(!matches2){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid cost");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            boolean isDeleted = HandoverDeviceModel.deleteDevice(id);
            if (isDeleted) {
                BoxBlur blur = new BoxBlur(3, 3, 1);
                root.setEffect(blur);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Success");
                alert.showAndWait();
                root.setEffect(null);
                loadAllItems();
                clearFields();
            } else {
                BoxBlur blur = new BoxBlur(3, 3, 1);
                root.setEffect(blur);
                Alert alert = new Alert(Alert.AlertType.ERROR, "Somthing went wrong");
                alert.showAndWait();
                root.setEffect(null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name= txtName.getText();
        String problem = txtProblem.getText();
        String status = cmbStatus.getValue();
        String cost = txtCost.getText();
        LocalDate date = LocalDate.parse(lblOrderDate.getText());
        String cId = txtCustomerID.getText();

        if(id.isEmpty() || name.isEmpty() || problem.isEmpty() || status.isEmpty() || cost.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill all fields");
            alert.showAndWait();
            return;
        }
        DeviceDto dto = new DeviceDto(id, name, problem, status, cost, date ,cId);

        try {
            boolean isUpdated = HandoverDeviceModel.updateDevice(dto);
            if (isUpdated){
                Notifications.create()
                        .graphic(new ImageView(new Image("/icons/icons8-check-mark-48.png")))
                        .text("Device updated")
                        .title("success")
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .show();
                loadAllItems();
                clearFields();
            } else {
                BoxBlur blur = new BoxBlur(3, 3, 1);
                root.setEffect(blur);
                Alert alert = new Alert(Alert.AlertType.ERROR, "Somthing went wrong");
                alert.showAndWait();
                root.setEffect(null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtSearch.getText();

        if (id!= null &&!id.isEmpty()) {
            try {
                DeviceDto deviceDto = HandoverDeviceModel.getDevice(id);

                if(deviceDto != null){
                    txtId.setText(deviceDto.getCId());
                    txtName.setText(deviceDto.getDName());
                    txtProblem.setText(deviceDto.getProblem());
                    cmbStatus.setValue(deviceDto.getStatus());
                    txtCost.setText(deviceDto.getCost());
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Customer not found");
                    alert.showAndWait();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Customer name is null or empty");
        }
    }

    private void generateNextDeviceId() {
        try {
            String deviceId = HandoverDeviceModel.generateNextDeviceId();
            txtId.setText(deviceId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setDate() {
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
    }

    private void tableListener() {
        tblDevice.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            setData(newValue);
        });
    }

    private void setData(DeviceTm row) {
        txtId.setText(row.getDeviceId());
        txtName.setText(row.getDName());
        txtProblem.setText(String.valueOf(row.getProblem()));
        txtCost.setText(String.valueOf(row.getCost()));
        cmbStatus.setValue(String.valueOf(row.getStatus()));

        try {
            DeviceDto deviceDto = HandoverDeviceModel.getDevice(row.getDeviceId());
            txtCustomerID.setText(deviceDto.getCId());
            CustomerDto customerDto = CustomerModel.getCustomer(deviceDto.getCId());
            cmbCustomerName.setValue(customerDto.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtProblem.setText("");
        cmbStatus.setValue("");
        txtCost.setText("");
        txtSearch.setText("");
        txtCustomerID.setText("");
        generateNextDeviceId();
    }

}
