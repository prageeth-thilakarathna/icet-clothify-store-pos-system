package edu.icet.pos.controller.dashboard.supplier;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.SupplierBo;
import edu.icet.pos.controller.dashboard.DashboardCenterController;
import edu.icet.pos.controller.dashboard.supplier.custom.DashboardSupplierChart;
import edu.icet.pos.controller.dashboard.supplier.custom.DashboardSupplierHeader;
import edu.icet.pos.model.report.SupplierReport;
import edu.icet.pos.model.supplier.Supplier;
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

public class HeaderController implements DashboardSupplierHeader {
    @FXML
    private JFXComboBox<String> optYear;
    @FXML
    private Button btnGenReport;

    private final SupplierBo supplierBo = BoFactory.getBo(BoType.SUPPLIER);
    private DashboardSupplierChart dashboardSupplierChart;

    @FXML
    private void optYearAction() {
        if (dashboardSupplierChart == null) {
            dashboardSupplierChart = DashboardCenterController.getInstance().getFxmlLoaderSupplierChart().getController();
        }
        dashboardSupplierChart.clearChart();
        dashboardSupplierChart.loadChart(optYear.getValue());
    }

    @FXML
    private void btnGenReportAction() {
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("report/supplier-report.jrxml");
            JasperReport report = JasperCompileManager.compileReport(stream);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(getSupplierReport());
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

    private List<SupplierReport> getSupplierReport() {
        List<SupplierReport> supplierReportList = new ArrayList<>();

        assert supplierBo != null;
        List<Supplier> supplierList = supplierBo.getSupplierByYear(optYear.getValue());

        for (Supplier supplier : supplierList) {
            SupplierReport supplierReport = new SupplierReport(
                    String.valueOf(supplier.getId()),
                    supplier.getTitle() + ". " + supplier.getFirstName() + " " + supplier.getLastName(),
                    supplier.getContact()
            );
            supplierReportList.add(supplierReport);
        }
        return supplierReportList;
    }

    private void setYears() {
        try {
            assert supplierBo != null;
            List<Supplier> firstSupplier = supplierBo.getFirstSupplier();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String[] date = simpleDateFormat.format(firstSupplier.get(0).getRegisterAt()).split("-");

            ObservableList<String> yearList = FXCollections.observableArrayList();

            String[] currentDate = simpleDateFormat.format(new Date()).split("-");

            for (int i = Integer.parseInt(date[0]); i <= Integer.parseInt(currentDate[0]); i++) {
                yearList.add(String.valueOf(i));
            }
            optYear.setItems(yearList);
            optYear.setValue(yearList.get(0));
            if (dashboardSupplierChart == null) {
                dashboardSupplierChart = DashboardCenterController.getInstance().getFxmlLoaderSupplierChart().getController();
            }
            dashboardSupplierChart.loadChart(optYear.getValue());

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
