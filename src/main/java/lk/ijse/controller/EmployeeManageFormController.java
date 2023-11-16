package lk.ijse.controller;

        import com.jfoenix.controls.JFXTextField;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Alert;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.effect.BoxBlur;
        import javafx.scene.layout.AnchorPane;
        import javafx.stage.Stage;
        import lk.ijse.dto.CustomerDto;
        import lk.ijse.dto.EmployeeDto;
        import lk.ijse.dto.tm.EmployeeTm;
        import lk.ijse.model.CustomerModel;
        import lk.ijse.model.EmployeeModel;
        import lk.ijse.model.OrdersModel;

        import java.io.IOException;
        import java.sql.SQLException;
        import java.util.List;

public class EmployeeManageFormController {

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
    private TableColumn<?, ?> colPosition;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtEmployeeId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPosition;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXTextField txtSearch;

    public void initialize (){
        setCellValueFactory();
        generateNextEmployeeId();
        loadAllEmployee();
    }

    private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    private void loadAllEmployee(){
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> dtoList = EmployeeModel.getAllEmployees();

            for (EmployeeDto dto : dtoList) {
                obList.add(
                        new EmployeeTm(
                                dto.getEmpId(),
                                dto.getName(),
                                dto.getAddress(),
                                dto.getPosition(),
                                dto.getContact(),
                                dto.getSalary()
                        )
                );
            }
            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddAsUserOnAction(ActionEvent event) throws IOException {
        BoxBlur blur = new BoxBlur(3, 3, 1);
        root.setEffect(blur);
        Parent parent = FXMLLoader.load(getClass().getResource("/view/AddUserForm.fxml"));
        Scene scene =new Scene(parent);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add User");
        stage.showAndWait();
        root.setEffect(null);
        stage.setOnCloseRequest(e -> {
            root.setEffect(null);
        });
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String position = txtPosition.getText();
        String salary = txtSalary.getText();
        String userId = "U001";

        EmployeeDto employeeDto = new EmployeeDto(id, name, address,position, contact, salary, userId);

        if(id.isEmpty() || name.isEmpty() || address.isEmpty() || contact.isEmpty() || position.isEmpty() || salary.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Fill all fields");
            alert.showAndWait();
            return;
        }

        try {
            boolean isAdded = EmployeeModel.saveEmployee(employeeDto);
            if (isAdded) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Employee Added");
                alert.showAndWait();
                clearFields();
                loadAllEmployee();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Employee Not Added");
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
        String id = txtEmployeeId.getText();

        try {
            boolean isDeleted = EmployeeModel.deleteEmployee(id);
            if (isDeleted) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Employee Deleted");
                alert.showAndWait();
                clearFields();
                loadAllEmployee();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Employee Not Deleted");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String position = txtPosition.getText();
        String salary = txtSalary.getText();
        String userId = "U001";

        EmployeeDto employeeDto = new EmployeeDto(id, name, address, contact, position, salary, userId);

        if(id.isEmpty() || name.isEmpty() || address.isEmpty() || contact.isEmpty() || position.isEmpty() || salary.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Fill all fields");
            alert.showAndWait();
            return;
        }

        try {
            Boolean isUpdated = EmployeeModel.updateEmployee(employeeDto);
            if (isUpdated) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Employee Updated");
                alert.showAndWait();
                clearFields();
                loadAllEmployee();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Employee Not Updated");
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
                EmployeeDto employeeDto = EmployeeModel.getEmployee(id);

                if(employeeDto != null){
                    txtEmployeeId.setText(employeeDto.getEmpId());
                    txtName.setText(employeeDto.getName());
                    txtAddress.setText(employeeDto.getAddress());
                    txtContact.setText(employeeDto.getContact());
                    txtPosition.setText(employeeDto.getPosition());
                    txtSalary.setText(employeeDto.getSalary());
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

    private void generateNextEmployeeId() {
        try {
            String employeeId = EmployeeModel.generateNextEmployeeId();
            txtEmployeeId.setText(employeeId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtEmployeeId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtPosition.setText("");
        txtSalary.setText("");
        txtSearch.setText("");
    }
}

