package edu.icet.pos.controller.dashboard.employee;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.controller.dashboard.DashboardCenterController;
import edu.icet.pos.controller.dashboard.employee.custom.DashboardEmployeeChart;
import edu.icet.pos.controller.dashboard.employee.custom.DashboardEmployeeHeader;
import edu.icet.pos.model.employee.Employee;
import edu.icet.pos.model.report.EmployeeReport;
import edu.icet.pos.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class HeaderController implements DashboardEmployeeHeader {
    @FXML
    private JFXComboBox<String> optYear;
    @FXML
    private Button btnGenReport;

    private final UserBo userBo = BoFactory.getBo(BoType.USER);
    private DashboardEmployeeChart dashboardEmployeeChart;

    @FXML
    private void btnGenReportAction() {
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("report/employee-report.jrxml");
            JasperReport report = JasperCompileManager.compileReport(stream);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(getEmployeeReport());
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("YEAR", optYear.getValue());

            JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);
            JasperViewer viewer = new JasperViewer(print, false);
            viewer.setVisible(true);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private List<EmployeeReport> getEmployeeReport() {
        List<EmployeeReport> employeeReportList = new ArrayList<>();

        assert userBo != null;
        List<Employee> employeeList = userBo.getEmployeeByYear(optYear.getValue());

        for (Employee employee : employeeList) {
            EmployeeReport employeeReport = new EmployeeReport(
                    String.valueOf(employee.getId()),
                    employee.getTitle() + ". " + employee.getFirstName() + " " + employee.getLastName(),
                    employee.getContact()
            );
            employeeReportList.add(employeeReport);
        }
        return employeeReportList;
    }

    @FXML
    private void optYearAction() {
        if (dashboardEmployeeChart == null) {
            dashboardEmployeeChart = DashboardCenterController.getInstance().getFxmlLoaderEmployeeChart().getController();
        }
        dashboardEmployeeChart.clearChart();
        dashboardEmployeeChart.loadChart(optYear.getValue());
    }

    private void setYears() {
        try {
            assert userBo != null;
            List<Employee> firstEmployee = userBo.getFirstEmployee();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String[] date = simpleDateFormat.format(firstEmployee.get(0).getRegisterAt()).split("-");

            ObservableList<String> yearList = FXCollections.observableArrayList();

            String[] currentDate = simpleDateFormat.format(new Date()).split("-");

            for (int i = Integer.parseInt(date[0]); i <= Integer.parseInt(currentDate[0]); i++) {
                yearList.add(String.valueOf(i));
            }
            optYear.setItems(yearList);
            optYear.setValue(yearList.get(0));
            if (dashboardEmployeeChart == null) {
                dashboardEmployeeChart = DashboardCenterController.getInstance().getFxmlLoaderEmployeeChart().getController();
            }
            dashboardEmployeeChart.loadChart(optYear.getValue());

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @Override
    public void loadHeader() {
        setYears();
        if (optYear.getItems().isEmpty()) {
            btnGenReport.setDisable(true);
        }
    }
}
