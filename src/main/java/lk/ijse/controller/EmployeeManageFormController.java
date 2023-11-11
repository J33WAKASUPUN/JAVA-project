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
        import javafx.stage.Stage;
        import lk.ijse.dto.EmployeeDto;
        import lk.ijse.dto.tm.EmployeeTm;
        import lk.ijse.model.EmployeeModel;

        import java.io.IOException;
        import java.sql.SQLException;
        import java.util.List;

public class EmployeeManageFormController {

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

    public void initialize (){

    }

    private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
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
                                dto.getContact(),
                                dto.getPosition(),
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
        Parent parent = FXMLLoader.load(getClass().getResource("/view/AddUserForm.fxml"));
        Scene scene =new Scene(parent);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add User");
        stage.show();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String position = txtPosition.getText();
        String salary = txtSalary.getText();
        String cId = "null";

        EmployeeDto employeeDto = new EmployeeDto(id, name, address, contact, position, salary, cId);

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
        String cId = "null";

        EmployeeDto employeeDto = new EmployeeDto(id, name, address, contact, position, salary, cId);

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
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Employee Not Updated");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtEmployeeId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtPosition.setText("");
        txtSalary.setText("");
    }
}

