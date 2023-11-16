package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.OrderDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.dto.tm.OrderTm;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.ItemModel;
import lk.ijse.model.OrdersModel;
import lk.ijse.model.PlaceOrderModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderManageFormController {

    @FXML
    private JFXComboBox<String> cmbItem;

    @FXML
    private JFXComboBox<String> cmbCustomerName;

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
    private JFXTextField txtRepairPrice;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private JFXTextField txtCustomerID;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblOrderDate;

    public void initialize(){
        setCellValueFactory();
        loadItemNames();
        setDate();
        loadCustomerNames();
        generateNextOrderId();
        lblPrice.setDisable(true);
        txtRepairPrice.setDisable(true);
    }

    ObservableList<OrderTm> obList = FXCollections.observableArrayList();

    public void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItem.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void generateNextOrderId() {
        try {
            String orderId = OrdersModel.generateNextOrderId();
            txtId.setText(orderId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadItemNames() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<ItemDto> itemDtos = ItemModel.getAllItems();

            for (ItemDto dto : itemDtos) {
                obList.add(dto.getItemName());
            }
            cmbItem.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbItemNameOnAction(ActionEvent event) {
        String name = cmbItem.getValue();

        if (name != null && !name.isEmpty()) {
            try {
                ItemDto dto = ItemModel.getItem(name);
                txtItemCode.setText(dto.getItemId());
                txtUnitPrice.setText(dto.getUnitPrice());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Item name is null or empty");
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

    @FXML
    void btnAddOnAction() {
        String id = txtId.getText();
        String itemName = cmbItem.getValue();
        String itemId = txtItemCode.getText();
        String quantity = txtQuantity.getText();
        String unitPrice = txtUnitPrice.getText();
        String date = "2023/11/13";
        Button btn = new Button("Remove");
        int qty = Integer.parseInt(txtQuantity.getText());
        double unitPriceint=Integer.parseInt(txtUnitPrice.getText());
        double tot = unitPriceint * qty;


        setRemoveBtnAction(btn);
        btn.setCursor(Cursor.HAND);


        if (!obList.isEmpty()) {
            for (int i = 0; i < tblOrders.getItems().size(); i++) {
                if (colItem.getCellData(i).equals(itemId)) {
                    int col_qty = (int) colQty.getCellData(i);
                    qty += col_qty;
                    tot = unitPriceint * qty;

                    obList.get(i).setQty(qty);
                    obList.get(i).setPrice(tot);

                    calculateTotal();
                    tblOrders.refresh();
                    return;
                }
            }
        }
       var orderTm = new OrderTm(itemId, itemName, qty, tot, unitPriceint,  btn);

       obList.add(orderTm);

        tblOrders.setItems(obList);
        calculateTotal();
    }

    private void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                OrderTm selectedIndex = tblOrders.getSelectionModel().getSelectedItem();

                obList. remove(selectedIndex);
                tblOrders.refresh();
                calculateTotal();
            }
        });
    }

    private void calculateTotal() {
        double total = 0;
        for (int i = 0; i < tblOrders.getItems().size(); i++) {
            total += (double) colPrice.getCellData(i);
        }
        lblTotal.setText(String.valueOf(total));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderId = txtId.getText();
        LocalDate date = LocalDate.parse(lblOrderDate.getText());
        String customerId = txtCustomerID.getText();

        List<OrderTm> orderTmList = new ArrayList<>();
        for (int i = 0; i < tblOrders.getItems().size(); i++) {
            OrderTm cartTm = obList.get(i);

            orderTmList.add(cartTm);
        }

        System.out.println("Place order form controller: " + orderTmList);
        var placeOrderDto = new OrderDto(orderId, date, customerId, orderTmList);
        try {
            boolean isSuccess = PlaceOrderModel.placeOrder(placeOrderDto);
            if (isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Success!").show();
            } else{
                new Alert(Alert.AlertType.ERROR, "Order Failed!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDate() {
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
    }

}
