package edu.icet.pos.controller.dashboard.inventory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChartController implements Initializable {
    @FXML
    private CategoryAxis chartCategory;
    @FXML
    private NumberAxis chartNumber;
    @FXML
    private BarChart<String, Integer> chart;

    private final XYChart.Series<String, Integer> chartSeries = new XYChart.Series<>();

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
