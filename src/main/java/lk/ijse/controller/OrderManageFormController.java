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
import lk.ijse.dto.PaymentDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.dto.tm.OrderTm;
import lk.ijse.model.*;

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

    @FXML
    private Label lblQuantity;

    public void initialize(){
        setCellValueFactory();
        loadItemNames();
        setDate();
        loadCustomerNames();
        generateNextOrderId();
        lblPrice.setDisable(true);
        txtRepairPrice.setDisable(true);
        txtQuantity.setDisable(false);
        lblQuantity.setDisable(false);
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
                if(name.equals("Device repair")) {
                    txtRepairPrice.setDisable(false);
                    lblPrice.setDisable(false);
                    txtQuantity.setDisable(true);
                    lblQuantity.setDisable(true);
                }else {
                    txtRepairPrice.setDisable(true);
                    lblPrice.setDisable(true);
                    txtQuantity.setDisable(false);
                    lblQuantity.setDisable(false);
                }

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

        if(!itemName.equals("Device repair")) {
            int quantity = Integer.parseInt(txtQuantity.getText());
            Double unitPrice = Double.valueOf(txtUnitPrice.getText());
            String date = lblOrderDate.getText();
            Button btn = new Button("Remove");
            double tot = unitPrice * quantity;


            setRemoveBtnAction(btn);
            btn.setCursor(Cursor.HAND);

            txtRepairPrice.setDisable(true);
            lblPrice.setDisable(true);
            txtQuantity.setDisable(false);
            lblQuantity.setDisable(false);

            if (!obList.isEmpty()) {
                for (int i = 0; i < tblOrders.getItems().size(); i++) {
                    if (colId.getCellData(i).equals(id)) {
                        int col_qty = (int) colQty.getCellData(i);
                        quantity += col_qty;
                        tot = unitPrice * quantity;

                        obList.get(i).setQty(quantity);
                        obList.get(i).setPrice(tot);

                        calculateTotal();
                        tblOrders.refresh();
                        return;
                    }
                }
            }
            var cartTm = new OrderTm(itemId, itemName, quantity, tot, unitPrice, btn);

            obList.add(cartTm);
            tblOrders.setItems(obList);
            calculateTotal();
        }else{
            String date = lblOrderDate.getText();
            Button btn = new Button("Remove");
            int qty = 0;
            double unitPriceInt = 0;
            double tot = 0;

            setRemoveBtnAction(btn);
            btn.setCursor(Cursor.HAND);

            txtRepairPrice.setDisable(false);
            lblPrice.setDisable(false);
            txtQuantity.setDisable(true);
            lblQuantity.setDisable(true);

            int devicePrice = Integer.parseInt(txtRepairPrice.getText());

            qty = 0;
            tot = devicePrice;

            var cartTm = new OrderTm(itemId, itemName, qty, tot, unitPriceInt, btn);

            obList.add(cartTm);
            tblOrders.setItems(obList);
            calculateTotal();
        }
    }

    private void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int selectedIndex = tblOrders.getSelectionModel().getSelectedIndex();

                if (selectedIndex >= 0) {
                    obList.remove(selectedIndex);
                    tblOrders.refresh();
                    calculateTotal();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please select row to remove");
                    alert.showAndWait();
                }
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
        clearFields();
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException {
        String orderId = txtId.getText();
        LocalDate date = LocalDate.parse(lblOrderDate.getText());
        String customerId = txtCustomerID.getText();
        String paymentId = PaymentModel.generateNextPaymentId();
        double amount = Double.parseDouble((lblTotal.getText()));
        String status = "paid";

        List<OrderTm> orderTmList = new ArrayList<>();
        for (int i = 0; i < tblOrders.getItems().size(); i++) {
            OrderTm cartTm = obList.get(i);

            orderTmList.add(cartTm);
        }

        var placeOrderDto = new OrderDto(orderId, date, customerId, orderTmList);
        PaymentDto paymentDto = new PaymentDto(paymentId, amount, status, date, orderId);

        try {
            boolean isSuccess = PlaceOrderModel.placeOrder(placeOrderDto);
            if (isSuccess) {
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION, "Order Success!");
                alert.showAndWait();
                boolean isSaved = PaymentModel.setPayment(paymentDto);
                if (!isSaved) {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR, "Payment details not saved");
                    alert.showAndWait();
                }
                obList.clear();
                generateNextOrderId();
            } else{
                Alert alert = new Alert(Alert.AlertType.ERROR,"Oder not saved");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDate() {
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        tblOrders.refresh();
    }

    private void clearFields() {
        txtQuantity.setText("");
        txtRepairPrice.setText("");
    }

}
