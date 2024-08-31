package edu.icet.pos.controller.dashboard.employee;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.controller.dashboard.employee.custom.DashboardEmployeeChart;
import edu.icet.pos.model.employee.Employee;
import edu.icet.pos.util.BoType;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChartController implements DashboardEmployeeChart {
    @FXML
    private BarChart<String, Integer> chart;

    private final XYChart.Series<String, Integer> chartSeries = new XYChart.Series<>();
    private final UserBo userBo = BoFactory.getBo(BoType.USER);

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

    private void setDate(List<Integer> employeeCountList){
        if(chartSeries.getName()==null){
            chartSeries.setName("Employee Count");
        }

        for(int i=0; i<12; i++){
            chartSeries.getData().add(new XYChart.Data<>(getMonth().get(i), employeeCountList.get(i)));
        }
        chart.getData().add(chartSeries);
    }

    @Override
    public void loadChart(String year) {
        List<Integer> employeeCountList = new ArrayList<>(12);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < 12; i++) {
            employeeCountList.add(0);
        }

        try {
            assert userBo != null;
            List<Employee> employeeList = userBo.getEmployeeByYear(year);

            for (Employee employee : employeeList) {
                String[] month = dateFormat.format(employee.getRegisterAt()).split("-");
                Integer value = employeeCountList.get((Integer.parseInt(month[1])) - 1) + 1;
                employeeCountList.set(((Integer.parseInt(month[1])) - 1), value);
            }
            setDate(employeeCountList);
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
