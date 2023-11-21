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
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.OrdersModel;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerManageFormController {
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtAddress;
    public JFXTextField txtContactNumber;
    public JFXTextField txtEmail;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private AnchorPane root;

    public  void initialize (){
        setCellValueFactory();
        generateNextCustomerId();
        loadAllCustomer();
    }

    public void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("cId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    public void loadAllCustomer() {
        var model = new CustomerModel();

        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
        try {
            List<CustomerDto> dtoList = CustomerModel.getAllCustomer();

            for(CustomerDto dto : dtoList){
                obList.add(
                        new CustomerTm(
                                dto.getCId(),
                                dto.getName(),
                                dto.getEmail(),
                                dto.getAddress(),
                                dto.getContact()
                        )
                );
            }
            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextCustomerId() {
        try {
            String customerId = CustomerModel.generateNextCustomerId();
            txtCustomerId.setText(customerId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnAddCustomerOnActoin(ActionEvent actionEvent) {
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        String id = txtCustomerId.getText();
        String name= txtCustomerName.getText();
        String address = txtAddress.getText();
        String contact = txtContactNumber.getText();
        String email = txtEmail.getText();
        String uId = "U001";

        if(id.isEmpty() || name.isEmpty() || address.isEmpty() || contact.isEmpty() || email.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill all fields");
            BoxBlur blur = new BoxBlur(3, 3, 1);
            root.setEffect(blur);
            alert.showAndWait();
            root.setEffect(null);
            return;
        }
        if(validateCustomer()) {
            CustomerDto dto = new CustomerDto(id, name, email, address, contact, uId);

            try {
                boolean isSaved = CustomerModel.setCustomer(dto);
                if (isSaved) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Succsess");
                    BoxBlur blur = new BoxBlur(5, 5, 2);
                    root.setEffect(blur);
                    alert.showAndWait();
                    root.setEffect(null);
                    loadAllCustomer();
                    clearFields();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Somthing went wrong");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateCustomer(){

        boolean matches = Pattern.matches("[C][0-9]{3,}", txtCustomerId.getText());
        if (!matches){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid customer id");
            alert.showAndWait();
            return false;
        }

        boolean matches1 = Pattern.matches("^([ \\u00c0-\\u01ffa-zA-Z'\\-]{5,})+$", txtCustomerName.getText());
        if(!matches1){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid name");
            alert.showAndWait();
            return false;
        }

        boolean matches3 = Pattern.matches("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$", txtContactNumber.getText());
        if (!matches3) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid contact number");
            alert.showAndWait();
            return false;
        }

        boolean matches4 = Pattern.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", txtEmail.getText());
        if (!matches4) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid email");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtCustomerId.getText();

        try {
            boolean isDeleted = CustomerModel.deleteCustomer(id);
            if (isDeleted) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Success");
                alert.showAndWait();
                loadAllCustomer();
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
        String id = txtCustomerId.getText();
        String name= txtCustomerName.getText();
        String address = txtAddress.getText();
        String contact = txtContactNumber.getText();
        String email = txtEmail.getText();
        String uId = "U001";

        if(id.isEmpty() || name.isEmpty() || address.isEmpty() || contact.isEmpty() || email.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill all fields");
            alert.showAndWait();
            return;
        }
        CustomerDto dto = new CustomerDto(id, name, address, contact, email, uId);

        try {
            boolean isAdded = CustomerModel.updateCustomer(dto);
            if(isAdded){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Succsess");
                alert.showAndWait();
                loadAllCustomer();
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
    void btnSearchOnAction(ActionEvent event) {
        String id = txtSearch.getText();

        if (id!= null &&!id.isEmpty()) {
            try {
                CustomerDto customerDto = CustomerModel.getCustomer(id);

                if(customerDto != null){
                txtCustomerId.setText(customerDto.getCId());
                txtCustomerName.setText(customerDto.getName());
                txtAddress.setText(customerDto.getAddress());
                txtContactNumber.setText(customerDto.getContact());
                txtEmail.setText(customerDto.getEmail());
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
        txtCustomerId.setText("");
        txtCustomerName.setText("");
        txtAddress.setText("");
        txtContactNumber.setText("");
        txtEmail.setText("");
    }
}
