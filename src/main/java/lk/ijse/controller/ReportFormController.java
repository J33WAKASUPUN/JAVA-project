package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import lk.ijse.db.DBConnection;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.nio.file.FileSystems;
import java.time.LocalDate;
import java.util.Date;

public class ReportFormController {

    @FXML
    private JFXComboBox<String> cmbType;

    @FXML
    private JFXButton btnOrderOk;

    @FXML
    private DatePicker dtpFrom;

    @FXML
    private DatePicker dtpTo;

    public void initialize() {
        dtpFrom.setDisable(true);
        dtpTo.setDisable(true);
        btnOrderOk.setDisable(true);
        setCmbType();
    }
    public void btnShowReportOnAction(javafx.event.ActionEvent actionEvent) throws JRException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/report/Stock_Report.jrxml");
        JasperDesign jasperDesign = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                null,
                DBConnection.getInstance().getConnection()
        );
        JasperViewer.viewReport(jasperPrint, false);
    }
    public void btnOrderReportOkOnAction(javafx.event.ActionEvent actionEvent) throws JRException {
        String comboBox = cmbType.getValue();

        if (comboBox.equals("All")) {
            InputStream resourceAsStream = getClass().getResourceAsStream("/report/All_Orders.jrxml");
            JasperDesign jasperDesign = JRXmlLoader.load(resourceAsStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    null,
                    DBConnection.getInstance().getConnection()
            );
            JasperViewer.viewReport(jasperPrint, false);
        } else {
            LocalDate dateFrom = dtpFrom.getValue();
            LocalDate dateTo = dtpTo.getValue();

            Thread thread=new Thread(){
                @SneakyThrows
                public void run(){
                    String billPath = "C:\\Users\\ASUS\\Documents\\GitHub\\serisara-networks\\src\\main\\resources\\report\\All_Orders.jrxml";
                    String sql="select o.orderId, o.date, o.cId, i.itemId, i.unitPrice, i.qty from orders o join orderItemDetail i on o.orderId = i.orderId where date > '"+dateFrom+"' AND date < '"+dateTo+"'";
                    String path = FileSystems.getDefault().getPath("/report/All_Orders.jrxml").toAbsolutePath().toString();
                    JasperDesign jasdi = JRXmlLoader.load(billPath);
                    JRDesignQuery newQuery = new JRDesignQuery();
                    newQuery.setText(sql);
                    jasdi.setQuery(newQuery);
                    JasperReport js = JasperCompileManager.compileReport(jasdi);
                    JasperPrint jp = JasperFillManager.fillReport(js, null, DBConnection.getInstance().getConnection());
                    JasperViewer viewer = new JasperViewer(jp, false);
                    viewer.show();
                }
            };
            thread.start();
        }
    }

    public void btnEmployeeGenerateReportOnAction(ActionEvent actionEvent) throws JRException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/report/Employee_report.jrxml");
        JasperDesign jasperDesign = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                null,
                DBConnection.getInstance().getConnection()
        );
        JasperViewer.viewReport(jasperPrint, false);
    }

    public void setCmbType(){
        ObservableList<String> obList = FXCollections.observableArrayList();

        String repairing = "All";
        String seletctDate = "Select date";

        obList.add(repairing);
        obList.add(seletctDate);

        cmbType.setItems(obList);
    }

    public void cmbTypeOnAction(ActionEvent actionEvent) {
        String comboBox = cmbType.getValue();

        if(comboBox.equals("Select date")) {
            dtpFrom.setDisable(false);
            dtpTo.setDisable(false);
            btnOrderOk.setDisable(false);
        } else{
            dtpFrom.setDisable(true);
            dtpTo.setDisable(true);
            btnOrderOk.setDisable(false);
        }
    }
}
