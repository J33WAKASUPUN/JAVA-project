package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
            List<DeviceDto> obLlist = HandoverDeviceModel.getAllDevices();
            for(DeviceDto dto : obLlist){
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

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

}
