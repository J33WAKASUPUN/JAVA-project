package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dto.DeviceDto;
import lk.ijse.dto.tm.DeviceTm;
import lk.ijse.model.HandoverDeviceModel;

import java.sql.SQLException;
import java.util.List;

public class HandoverDeviceManageFormController {

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
    private JFXTextField txtStatus;

    public void initialize(){
        setCellValueFactory();
        loadAllItems();
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

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            String id = txtId.getText();
            String name = txtName.getText();
            String problem = txtProblem.getText();
            String status = txtStatus.getText();
            String cost = txtCost.getText();
            String date = "2023.12.12";
            String cId = "C001";

            DeviceDto deviceDto = new DeviceDto(id, name, problem, status, cost, date ,cId);

            boolean isAdded = HandoverDeviceModel.setDevice(deviceDto);
            if (isAdded) {
                Alert alert =new Alert(Alert.AlertType.CONFIRMATION, "Success");
                alert.showAndWait();
                loadAllItems();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Something went wrong");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Success");
                alert.showAndWait();
                loadAllItems();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Something went wrong");
                alert.showAndWait();
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
        String status = txtStatus.getText();
        String cost = txtCost.getText();
        String date = "2023.12.12";
        String cId = "C001";

        if(id.isEmpty() || name.isEmpty() || problem.isEmpty() || status.isEmpty() || cost.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill all fields");
            alert.showAndWait();
            return;
        }
        DeviceDto dto = new DeviceDto(id, name, problem, status, cost, date ,cId);

        try {
            boolean isUpdated = HandoverDeviceModel.updateDevice(dto);
            if (isUpdated){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Success");
                alert.showAndWait();
                loadAllItems();
                clearFields();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Something went wrong");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtProblem.setText("");
        txtStatus.setText("");
        txtCost.setText("");
    }

}
