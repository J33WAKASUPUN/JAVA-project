package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.SupplierDto;
import lk.ijse.model.*;

import java.lang.reflect.MalformedParametersException;
import java.sql.SQLException;
import java.util.List;

public class ReStockFormController {

    @FXML
    private JFXComboBox<String> cmbItemName;

    @FXML
    private JFXComboBox<String> cmbSupplierName;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField txtCost;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtSupplierId;


    @FXML
    private JFXTextField txtqty;

    public void initialize(){
        loadItemNames();
        loadSupplierNames();
    }

    private void loadItemNames() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<ItemDto> itemDtos = ItemModel.getAllItems();

            for (ItemDto dto : itemDtos) {
                obList.add(dto.getItemName());
            }
            cmbItemName.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSupplierNames() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<SupplierDto> idList = SupplierModel.getAllSuppliers();

            for (SupplierDto dto : idList) {
                obList.add(dto.getSName());
            }

            cmbSupplierName.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        ((Stage) root.getScene().getWindow()).close();
    }

    @FXML
    void btnReStockOnAction(ActionEvent event) {
        String supplierId = txtSupplierId.getText();
        String supplierName = cmbSupplierName.getValue();
        String address = "";
        String contact = "";
        String itemId = txtId.getText();
        String itemName = cmbItemName.getValue();
        String cost = txtCost.getText();
        String qty = txtqty.getText();
        String unitPrice = "";

        SupplierDto supplierDto = new SupplierDto(supplierId,supplierName,address,contact);
        ItemDto itemDto = new ItemDto(itemId,itemName,qty,cost,unitPrice);

        try {
            boolean isSuccess = ReStockModel.itemSupplier(supplierDto,itemDto);
            if (isSuccess) {
                Alert alert =new Alert(Alert.AlertType.CONFIRMATION, "Update Success!");
                alert.showAndWait();
            } else{
                Alert alert =new Alert(Alert.AlertType.ERROR, "update Failed!");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbItemNameOnAction(ActionEvent event) {
        String name = cmbItemName.getValue();

        if (name != null && !name.isEmpty()) {
            try {
                ItemDto dto = ItemModel.getItem(name);
                txtId.setText(dto.getItemId());
                txtCost.setText(dto.getCost());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Item name is null or empty");
        }
    }

    @FXML
    void cmbSupplierNameOnAction(ActionEvent event) {
        String name = cmbSupplierName.getValue();

        if (name!= null &&!name.isEmpty()) {
            try {
                SupplierDto supplierDto = SupplierModel.getSupplierByName(name);
                txtSupplierId.setText(supplierDto.getSupId());

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Supplier name is null or empty");
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    public void clearFields(){

    }

}
