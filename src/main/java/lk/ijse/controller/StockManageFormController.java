package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.tm.StockTm;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.ItemModel;
import lk.ijse.model.OrdersModel;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class StockManageFormController {

    @FXML
    private AnchorPane root;

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

    @FXML
    private JFXTextField txtSearch;

    public void initialize(){
        setCellValueFactory();
        generateNextItemId();
        loadAllItems();
        tableListener();
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
                                dto.getQtyOnHand(),
                                dto.getCost(),
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

        if(!validateItem()){
            return;
        }

        ItemDto itemDto = new ItemDto(id, name, qty, cost, unitPrice);

        try {
            boolean isSaved = ItemModel.setItem(itemDto);
            if (isSaved) {
                Notifications.create()
                        .graphic(new ImageView(new Image("/icons/icons8-check-mark-48.png")))
                        .text("Item Added")
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

    private boolean validateItem(){

        boolean matches = Pattern.matches("[I][0-9]{3,}", txtId.getText());
        if (!matches){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid item id");
            alert.showAndWait();
            return false;
        }

        boolean matches2 = Pattern.matches("^([ \\u00c0-\\u01ffa-zA-Z'\\-]{2,})+$", txtName.getText());
        if (!matches2) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid name");
            alert.showAndWait();
            return false;
        }

        boolean matches3 = Pattern.matches("\\d{0,}", txtQty.getText());
        if (!matches3) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid qty");
            alert.showAndWait();
            return false;
        }

        boolean matches4 = Pattern.matches("\\d{1,}(?:[.,]\\d{1})*(?:[.,]\\d{2})?", txtCost.getText());
        if (!matches4) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid cost");
            alert.showAndWait();
            return false;
        }

        boolean matches5 = Pattern.matches("\\d{1,}(?:[.,]\\d{1})*(?:[.,]\\d{2})?", txtUnitPrice.getText());
        if (!matches5) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid unitprice");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        generateNextItemId();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            boolean isDeleted = ItemModel.deleteItem(id);
            if (isDeleted) {
                BoxBlur blur = new BoxBlur(3, 3, 1);
                root.setEffect(blur);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Success");
                alert.showAndWait();
                root.setEffect(null);
                loadAllItems();
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
        String cost = txtCost.getText();
        String qty = txtQty.getText();
        String unitPrice = txtUnitPrice.getText();

        if(id.isEmpty() || name.isEmpty() || cost.isEmpty() || qty.isEmpty() || unitPrice.isEmpty()){
            BoxBlur blur = new BoxBlur(3, 3, 1);
            root.setEffect(blur);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill all fields");
            alert.showAndWait();
            root.setEffect(null);
            return;
        }
        ItemDto dto = new ItemDto(id, name, cost, qty, unitPrice);

        try {
            boolean isUpdated = ItemModel.updateItem(dto);

            if (isUpdated){
                Notifications.create()
                        .graphic(new ImageView(new Image("/icons/icons8-check-mark-48.png")))
                        .text("Item updated")
                        .title("success")
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .show();
                clearFields();
                loadAllItems();
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
                ItemDto itemDto = ItemModel.getItemById(id);

                if(itemDto != null){
                    txtId.setText(itemDto.getItemId());
                    txtName.setText(itemDto.getItemName());
                    txtQty.setText(itemDto.getQtyOnHand());
                    txtCost.setText(itemDto.getCost());
                    txtUnitPrice.setText(itemDto.getUnitPrice());
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

    @FXML
    void btnReStockOnAction(ActionEvent event) throws IOException {
        BoxBlur blur = new BoxBlur(3, 3, 1);
        root.setEffect(blur);
        Parent parent = FXMLLoader.load(getClass().getResource("/view/ReStockForm.fxml"));
        Scene scene =new Scene(parent);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
        root.setEffect(null);
        stage.setOnCloseRequest(e -> {
            root.setEffect(null);
        });
        loadAllItems();
        generateNextItemId();
    }

    private void generateNextItemId() {
        try {
            String itemId = ItemModel.generateNextItemId();
            txtId.setText(itemId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void tableListener() {
        tblItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            setData(newValue);
        });
    }

    private void setData(StockTm row) {
        txtId.setText(row.getItemId());
        txtName.setText(row.getItemName());
        txtUnitPrice.setText(String.valueOf(row.getUnitPrice()));
        txtQty.setText(String.valueOf(row.getQtyOnHand()));
        txtCost.setText(String.valueOf(row.getCost()));
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtCost.setText("");
        txtQty.setText("");
        txtUnitPrice.setText("");
        txtSearch.setText("");
        generateNextItemId();
    }

}
