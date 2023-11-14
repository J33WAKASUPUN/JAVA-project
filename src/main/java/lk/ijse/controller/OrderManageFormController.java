package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dto.OrderDto;
import lk.ijse.dto.tm.OrderTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderManageFormController {

    @FXML
    private JFXComboBox<?> cmbItem;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colItem;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableView<OrderTm> tblOrders;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQuantity;

    @FXML
    private JFXTextField txtUnitPrice;

    public void initialize(){

    }

    public void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItem.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    @FXML
    void btnAddOnAction() {
        String id = txtId.getText();
        String itemCode = txtItemCode.getText();
        String itemName = "Sample item";
        //String itemName = cmbItem.get
        String price = "akn";
        String quantity = txtQuantity.getText();
        String unitPrice = txtUnitPrice.getText();
        String date = "2023/11/13";

        if(id.isEmpty() || itemCode.isEmpty() || price.isEmpty() || quantity.isEmpty() || unitPrice.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill all fields");
            alert.showAndWait();
            return;
        }

        ObservableList<OrderTm> obList = FXCollections.observableArrayList();

        List<OrderDto> dtoList = new ArrayList<>();
        OrderDto dto = new OrderDto(id, itemCode, itemName, price, quantity, unitPrice, date);

        obList.add(
                new OrderTm(
                        id,
                        itemName,
                        quantity,
                        price,
                        date

                )
        );
        tblOrders.setItems(obList);

        for (int i = 0; i < obList.size(); i++) {
            //List<TaskDto> finalDtoList = dtoList;
            int finalI = i;
            obList.get(i).getBtnRemove().setOnAction((ActionEvent event) -> {
            });
        }
        tblOrders.setItems(obList);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnComfirmOnAction(ActionEvent event) {

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }

}
