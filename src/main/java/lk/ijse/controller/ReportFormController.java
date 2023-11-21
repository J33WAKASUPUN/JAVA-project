package lk.ijse.controller;

import javafx.fxml.FXML;
import lk.ijse.db.DBConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.event.ActionEvent;
import java.io.InputStream;

public class ReportFormController {

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
}
