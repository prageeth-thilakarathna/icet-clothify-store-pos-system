package edu.icet.pos.controller.dashboard.supplier;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.SupplierBo;
import edu.icet.pos.controller.dashboard.supplier.custom.DashboardSupplierChart;
import edu.icet.pos.model.supplier.Supplier;
import edu.icet.pos.util.BoType;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChartController implements DashboardSupplierChart {
    @FXML
    private BarChart<String, Integer> chart;

    private final XYChart.Series<String, Integer> chartSeries = new XYChart.Series<>();
    private final SupplierBo supplierBo = BoFactory.getBo(BoType.SUPPLIER);

    private List<String> getMonth() {
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

    private void setDate(List<Integer> supplierCountList){
        if(chartSeries.getName()==null){
            chartSeries.setName("Supplier Count");
        }

        for(int i=0; i<12; i++){
            chartSeries.getData().add(new XYChart.Data<>(getMonth().get(i), supplierCountList.get(i)));
        }
        chart.getData().add(chartSeries);
    }

    @Override
    public void loadChart(String year) {
        List<Integer> supplierCountList = new ArrayList<>(12);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < 12; i++) {
            supplierCountList.add(0);
        }

        try {
            assert supplierBo != null;
            List<Supplier> supplierList = supplierBo.getSupplierByYear(year);

            for (Supplier supplier : supplierList) {
                String[] month = dateFormat.format(supplier.getRegisterAt()).split("-");
                Integer value = supplierCountList.get((Integer.parseInt(month[1])) - 1) + 1;
                supplierCountList.set(((Integer.parseInt(month[1])) - 1), value);
            }
            setDate(supplierCountList);
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
