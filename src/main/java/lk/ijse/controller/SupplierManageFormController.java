package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.SupplierDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.dto.tm.StockTm;
import lk.ijse.dto.tm.SupplierTm;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.OrdersModel;
import lk.ijse.model.SupplierModel;
import org.controlsfx.control.Notifications;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class SupplierManageFormController {

    @FXML
    private AnchorPane root;

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
        generateNextSupplierId();
        loadAllSupplier();
        tableListener();
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
            BoxBlur blur = new BoxBlur(3, 3, 1);
            root.setEffect(blur);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill all fields");
            alert.showAndWait();
            root.setEffect(null);
            return;
        }

        if(!validateSupplier()){
            return;
        }

        SupplierDto dto = new SupplierDto(id, name, address, contact);

        try {
            boolean isSaved = SupplierModel.setSupplier(dto);
            if (isSaved) {
                Notifications.create()
                        .graphic(new ImageView(new Image("/icons/icons8-check-mark-48.png")))
                        .text("Supplier Added")
                        .title("success")
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .show();
                loadAllSupplier();
                clearFields();
            } else {
                BoxBlur blur = new BoxBlur(3, 3, 1);
                root.setEffect(blur);
                Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
                alert.showAndWait();
                root.setEffect(null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validateSupplier(){

        boolean matches = Pattern.matches("[S][0-9]{3,}", txtId.getText());
        if (!matches){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid supplier id");
            alert.showAndWait();
            return false;
        }

        boolean matches1 = Pattern.matches("^([ \\u00c0-\\u01ffa-zA-Z'\\-]{5,})+$", txtName.getText());
        if(!matches1){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid name");
            alert.showAndWait();
            return false;
        }

        boolean matches2 = Pattern.matches("[A-Za-z0-9'\\.\\-\\s\\,]{0,}", txtAddress.getText());
        if(!matches2){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid address");
            alert.showAndWait();
            return false;
        }

        /*boolean matches3 = Pattern.matches("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$", txtContactNumber.getText());
        if (!matches3) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid contact number");
            alert.showAndWait();
            return false;
        }*/
        return true;
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
                clearFields();
            } else {
                BoxBlur blur = new BoxBlur(3, 3, 1);
                root.setEffect(blur);
                Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
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
        String address = txtAddress.getText();
        String contact = txtContactNumber.getText();

        if(id.isEmpty() || name.isEmpty() || address.isEmpty() || contact.isEmpty()){
            BoxBlur blur = new BoxBlur(3, 3, 1);
            root.setEffect(blur);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill all fields");
            alert.showAndWait();
            root.setEffect(null);
            return;
        }
        SupplierDto dto = new SupplierDto(id, name, address, contact);


        try {
            boolean isUpdated = SupplierModel.updateSupplier(dto);
            if (isUpdated) {
                Notifications.create()
                        .graphic(new ImageView(new Image("/icons/icons8-check-mark-48.png")))
                        .text("Supplier updated")
                        .title("success")
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .show();
                loadAllSupplier();
                clearFields();
            }else {
                BoxBlur blur = new BoxBlur(3, 3, 1);
                root.setEffect(blur);
                Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
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

    private void generateNextSupplierId() {
        try {
            String supplierId = SupplierModel.generateNextSupplierId();
            txtId.setText(supplierId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void tableListener() {
        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            setData(newValue);
        });
    }

    private void setData(SupplierTm row) {
        txtId.setText(row.getSupId());
        txtName.setText(row.getSName());
        txtAddress.setText(String.valueOf(row.getAddress()));
        txtContactNumber.setText(String.valueOf(row.getContact()));
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContactNumber.setText("");
        txtSearch.setText("");
    }

}
