package edu.icet.pos.controller.dashboard.inventory;

import edu.icet.pos.controller.dashboard.inventory.custom.DashboardInventoryChart;
import edu.icet.pos.model.inventory.Inventory;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChartController implements DashboardInventoryChart {
    @FXML
    private CategoryAxis chartCategory;
    @FXML
    private NumberAxis chartNumber;
    @FXML
    private BarChart<String, Integer> chart;

    private final XYChart.Series<String, Integer> chartSeries = new XYChart.Series<>();

    @Override
    public List<String> getMonth(){
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

    private void setData(List<Integer> stockList){
        if(chartSeries.getName()==null){
            chartSeries.setName("Stock");
        }

        for(int i=0; i<12; i++){
            chartSeries.getData().add(new XYChart.Data<>(getMonth().get(i), stockList.get(i)));
        }
        chart.getData().add(chartSeries);
    }

    @Override
    public void loadChart(List<Inventory> inventoryList) {
        List<Integer> monthlyStock = new ArrayList<>(12);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for(int i=0; i<12; i++){
            monthlyStock.add(0);
        }

        for(Inventory inventory : inventoryList){
            String[] month = dateFormat.format(inventory.getRegisterAt()).split("-");
            Integer value = monthlyStock.get(Integer.parseInt(month[1]));
            monthlyStock.set(Integer.parseInt(month[1]), (value+inventory.getStock()));
        }
        setData(monthlyStock);

    }

    @Override
    public void clearChart() {
        chartSeries.getData().removeAll(chartSeries.getData());
        chart.getData().remove(chartSeries);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //setData();
        //chart.getData().add(chartSeries);
    }
}
