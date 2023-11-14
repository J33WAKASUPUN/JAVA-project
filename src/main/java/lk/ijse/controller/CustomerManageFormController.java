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
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.model.CustomerModel;

import java.sql.SQLException;
import java.util.List;

public class CustomerManageFormController {
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtAddress;
    public JFXTextField txtContactNumber;
    public JFXTextField txtEmail;
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

    public  void initialize (){
        setCellValueFactory();
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
            alert.showAndWait();
            return;
        }
        CustomerDto dto = new CustomerDto(id, name, email,address,contact,uId);

        try {
            boolean isSaved = CustomerModel.setCustomer(dto);
            if(isSaved){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Succsess");
                alert.showAndWait();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "Somthing went wrong");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    private void clearFields() {
        txtCustomerId.setText("");
        txtCustomerName.setText("");
        txtAddress.setText("");
        txtContactNumber.setText("");
        txtEmail.setText("");
    }
}
