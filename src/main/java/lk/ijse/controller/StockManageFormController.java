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
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.tm.StockTm;
import lk.ijse.model.ItemModel;

import java.sql.SQLException;
import java.util.List;

public class StockManageFormController {

    @FXML
    private TableColumn<?, ?> colCost;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colqtyOnHand;

    @FXML
    private TableView<StockTm> tblItems;

    @FXML
    private JFXTextField txtCost;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtUnitPrice;

    public void initialize(){
        setCellValueFactory();
        loadAllItems();
    }

    public void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colqtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
    }

    public void loadAllItems() {
        var model = new ItemModel();

        ObservableList<StockTm> obList = FXCollections.observableArrayList();
        try {
            List<ItemDto> dtoList = ItemModel.getAllItems();

            for(ItemDto dto : dtoList){
                obList.add(
                        new StockTm(
                                dto.getItemId(),
                                dto.getItemName(),
                                dto.getCost(),
                                dto.getQtyOnHand(),
                                dto.getUnitPrice()
                        )
                );
            }
            tblItems.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String cost = txtCost.getText();
        String qty = txtQty.getText();
        String unitPrice = txtUnitPrice.getText();

        if(id.isEmpty() || name.isEmpty() || cost.isEmpty() || qty.isEmpty() || unitPrice.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill all fields");
            alert.showAndWait();
            return;
        }
        ItemDto itemDto = new ItemDto(id, name, cost, qty, unitPrice);

        try {
            boolean isSaved = ItemModel.setItem(itemDto);
            if (isSaved) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Succsess");
                alert.showAndWait();
                loadAllItems();
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
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            boolean isDeleted = ItemModel.deleteItem(id);
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
        String cost = txtCost.getText();
        String qty = txtQty.getText();
        String unitPrice = txtUnitPrice.getText();

        if(id.isEmpty() || name.isEmpty() || cost.isEmpty() || qty.isEmpty() || unitPrice.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill all fields");
            alert.showAndWait();
            return;
        }
        ItemDto dto = new ItemDto(id, name, cost, qty, unitPrice);

        try {
            boolean isUpdated = ItemModel.updateItem(dto);

            if (isUpdated){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Success");
                alert.showAndWait();
                clearFields();
                loadAllItems();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtCost.setText("");
        txtQty.setText("");
        txtUnitPrice.setText("");
    }

}
