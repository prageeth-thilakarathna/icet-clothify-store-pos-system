package edu.icet.pos.controller.dashboard.sales;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.OrderBo;
import edu.icet.pos.controller.dashboard.DashboardCenterController;
import edu.icet.pos.controller.dashboard.sales.custom.DashboardSalesChart;
import edu.icet.pos.controller.dashboard.sales.custom.DashboardSalesHeader;
import edu.icet.pos.model.order.Order;
import edu.icet.pos.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HeaderController implements DashboardSalesHeader {
    @FXML
    private JFXComboBox<String> optReport;
    @FXML
    private JFXComboBox<String> optYear;
    @FXML
    private JFXComboBox optMonth;
    @FXML
    private JFXComboBox optDay;
    @FXML
    private Button btnGenReport;

    private final OrderBo orderBo = BoFactory.getBo(BoType.ORDER);
    private DashboardSalesChart dashboardSalesChart;

    @FXML
    private void optReportAction() {
        try {
            assert orderBo != null;
            List<Order> firstOrder = orderBo.getFirstOrder();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String[] date = simpleDateFormat.format(firstOrder.get(0).getRegisterAt()).split("-");

            ObservableList<String> yearList = FXCollections.observableArrayList();

            String[] currentDate = simpleDateFormat.format(new Date()).split("-");

            for (int i = Integer.parseInt(date[0]); i <= Integer.parseInt(currentDate[0]); i++) {
                yearList.add(String.valueOf(i));
            }
            optYear.setItems(yearList);
            optYear.setValue(yearList.get(yearList.size()-1));
            if (dashboardSalesChart == null) {
                dashboardSalesChart = DashboardCenterController.getInstance().getFxmlLoaderSalesChart().getController();
            }
            dashboardSalesChart.loadChart(optYear.getValue());

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void optYearAction(ActionEvent actionEvent) {
    }

    @FXML
    private void optMonthAction(ActionEvent actionEvent) {
    }

    @FXML
    private void optDayAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnGenReportAction(ActionEvent actionEvent) {
    }

    private ObservableList<String> getReportOption(){
        ObservableList<String> reportOption = FXCollections.observableArrayList();
        reportOption.add("Annual");
        reportOption.add("Monthly");
        reportOption.add("Daily");
        return reportOption;
    }

    @Override
    public void loadHeader() {
        optReport.setItems(getReportOption());
        optReport.setValue(getReportOption().get(0));
        optReportAction();
    }
}
