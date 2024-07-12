package edu.icet.pos.controller.dashboard;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lombok.Getter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InventoryReportController implements Initializable {
    @FXML
    private TextField txtProductId;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnCancel;
    @FXML
    private JFXComboBox optYear;
    @FXML
    private JFXComboBox optMonth;
    @FXML
    private Button btnGenReport;
    @FXML
    private BarChart<String, Integer> chart;
    @FXML
    private CategoryAxis chartCategory;
    @FXML
    private NumberAxis chartNumber;

    private final XYChart.Series<String, Integer> chartSeries = new XYChart.Series<>();

    @FXML
    private void productIdKeyTyped(KeyEvent keyEvent) {
    }

    @FXML
    private void btnSearchAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnCancelAction(ActionEvent actionEvent) {
    }

    @FXML
    private void optYearAction(ActionEvent actionEvent) {
    }

    @FXML
    private void optMonthAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnGenReportAction(ActionEvent actionEvent) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("report/jasper_report_template.jrxml");
            JasperCompileManager.compileReport(inputStream);
        } catch (JRException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private List<String> getMonth(){
        List<String> monthList = new ArrayList<>();
        monthList.add("January");
        monthList.add("February");
        monthList.add("March");
        monthList.add("April");
        monthList.add("May");
        monthList.add("June");
        monthList.add("July");
        monthList.add("August");
        monthList.add("September");
        monthList.add("October");
        monthList.add("November");
        monthList.add("December");
        return monthList;
    }

    private void setData(){
        chartSeries.setName("Stock");
        chartSeries.getData().add(new XYChart.Data<>(getMonth().get(0), 2500));
        chartSeries.getData().add(new XYChart.Data<>(getMonth().get(1), 1300));
        chartSeries.getData().add(new XYChart.Data<>(getMonth().get(2), 3500));
        chartSeries.getData().add(new XYChart.Data<>(getMonth().get(3), 1500));
        chartSeries.getData().add(new XYChart.Data<>(getMonth().get(4), 8500));
        chartSeries.getData().add(new XYChart.Data<>(getMonth().get(5), 4500));
        chartSeries.getData().add(new XYChart.Data<>(getMonth().get(6), 9500));
        chartSeries.getData().add(new XYChart.Data<>(getMonth().get(7), 1200));
        chartSeries.getData().add(new XYChart.Data<>(getMonth().get(8), 800));
        chartSeries.getData().add(new XYChart.Data<>(getMonth().get(9), 5400));
        chartSeries.getData().add(new XYChart.Data<>(getMonth().get(10), 2800));
        chartSeries.getData().add(new XYChart.Data<>(getMonth().get(11), 7500));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData();
        chart.getData().add(chartSeries);
    }
}
