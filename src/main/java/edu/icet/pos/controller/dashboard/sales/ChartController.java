package edu.icet.pos.controller.dashboard.sales;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.OrderBo;
import edu.icet.pos.controller.dashboard.sales.custom.DashboardSalesChart;
import edu.icet.pos.model.order.Order;
import edu.icet.pos.util.BoType;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChartController implements DashboardSalesChart {
    @FXML
    private BarChart<String, Integer> chart;
    @FXML
    private CategoryAxis chartCategory;

    private final XYChart.Series<String, Integer> chartSeries = new XYChart.Series<>();
    private final OrderBo orderBo = BoFactory.getBo(BoType.ORDER);

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

    private void setData(List<Integer> salesTotalList){
        if(chartSeries.getName()==null){
            chartSeries.setName("Stock");
        }

        for(int i=0; i<12; i++){
            chartSeries.getData().add(new XYChart.Data<>(getMonth().get(i), salesTotalList.get(i)));
        }
        chart.getData().add(chartSeries);
    }

    @Override
    public void loadChart(String year) {
        List<Integer> salesTotalList = new ArrayList<>(12);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < 12; i++) {
            salesTotalList.add(0);
        }

        try {
            assert orderBo != null;
            List<Order> orderList = orderBo.getOrderByYear(year);

            for (Order order : orderList) {
                String[] month = dateFormat.format(order.getRegisterAt()).split("-");
                Integer value = salesTotalList.get((Integer.parseInt(month[1])) - 1) + 1;
                salesTotalList.set(((Integer.parseInt(month[1])) - 1), value);
            }
            setData(salesTotalList);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @Override
    public void clearChart() {
        chartSeries.getData().removeAll(chartSeries.getData());
        chart.getData().remove(chartSeries);
    }
}
