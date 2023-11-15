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
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.SupplierDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.dto.tm.SupplierTm;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.SupplierModel;

import java.sql.SQLException;
import java.util.List;

public class SupplierManageFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtContactNumber;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSearch;

    public void initialize() {
        setCellValueFactory();
        loadAllSupplier();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("sName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }
    private void loadAllSupplier() {
        var model = new SupplierModel();

        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();
        try {
            List<SupplierDto> dtoList = SupplierModel.getAllSuppliers();

            for(SupplierDto dto : dtoList){
                obList.add(
                        new SupplierTm(
                                dto.getSupId(),
                                dto.getSName(),
                                dto.getAddress(),
                                dto.getContact()
                        )
                );
            }
            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name= txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContactNumber.getText();

        if(id.isEmpty() || name.isEmpty() || address.isEmpty() || contact.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill all fields");
            alert.showAndWait();
            return;
        }
        SupplierDto dto = new SupplierDto(id, name, address, contact);


        try {
            boolean isSaved = SupplierModel.setSupplier(dto);
            if (isSaved) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Succsess");
                alert.showAndWait();
                loadAllSupplier();
                clearFields();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Somthing went wrong");
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
    void btnDeleteOnActoin(ActionEvent event) {
        String id = txtId.getText();

        try {
            boolean isDeleted = SupplierModel.deleteSupplier(id);
            if (isDeleted) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Success");
                alert.showAndWait();
                loadAllSupplier();
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
        String address = txtAddress.getText();
        String contact = txtContactNumber.getText();

        if(id.isEmpty() || name.isEmpty() || address.isEmpty() || contact.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill all fields");
            alert.showAndWait();
            return;
        }
        SupplierDto dto = new SupplierDto(id, name, address, contact);


        try {
            boolean isUpdated = SupplierModel.updateSupplier(dto);
            if (isUpdated) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Succsess");
                alert.showAndWait();
                loadAllSupplier();
                clearFields();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
                alert.showAndWait();
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
                SupplierDto supplierDto = SupplierModel.getSupplier(id);

                if(supplierDto != null){
                    txtId.setText(supplierDto.getSupId());
                    txtName.setText(supplierDto.getSName());
                    txtAddress.setText(supplierDto.getAddress());
                    txtContactNumber.setText(supplierDto.getContact());
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

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContactNumber.setText("");
        txtSearch.setText("");
    }

}
