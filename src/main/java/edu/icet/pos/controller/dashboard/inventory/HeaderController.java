package edu.icet.pos.controller.dashboard.inventory;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.InventoryBo;
import edu.icet.pos.controller.dashboard.DashboardCenterController;
import edu.icet.pos.controller.dashboard.inventory.custom.DashboardInventoryChart;
import edu.icet.pos.controller.dashboard.inventory.custom.DashboardInventoryHeader;
import edu.icet.pos.model.inventory.Inventory;
import edu.icet.pos.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HeaderController implements DashboardInventoryHeader {
    @FXML
    private TextField txtProductId;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnCancel;
    @FXML
    private JFXComboBox<String> optYear;
    @FXML
    private Button btnGenReport;

    private final InventoryBo inventoryBo = BoFactory.getBo(BoType.INVENTORY);
    private DashboardInventoryChart dashboardInventoryChart;
    private List<Inventory> searchList = new ArrayList<>();

    @FXML
    private void productIdKeyTyped() {
        validateInputs();
    }

    @FXML
    private void btnSearchAction() {
        try {
            assert inventoryBo != null;
            List<Inventory> inventoryList =
                    inventoryBo.getStockByProductId(Integer.parseInt(txtProductId.getText()));
            if (!inventoryList.isEmpty()) {
                searchList = inventoryList;
                txtProductId.setDisable(true);
                setYears(inventoryList);
                btnSearch.setDisable(true);
                btnCancel.setDisable(false);
                optYear.setDisable(false);
            }
            if (inventoryList.isEmpty()) {
                throw new NullPointerException("Please, Enter the Correct Product ID.");
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void btnCancelAction() {
        clearForm();
    }

    @FXML
    private void optYearAction() {
        List<Inventory> selectedInventoryList = new ArrayList<>();

        for (Inventory inventory : searchList) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String[] year = dateFormat.format(inventory.getRegisterAt()).split("-");

            if (Objects.equals(year[0], optYear.getValue())) {
                selectedInventoryList.add(inventory);
            }
        }
        if (dashboardInventoryChart == null) {
            dashboardInventoryChart = DashboardCenterController.getInstance().getFxmlLoaderInventoryChart().getController();
        }
        if (!selectedInventoryList.isEmpty()) {
            dashboardInventoryChart.loadChart(selectedInventoryList);
        }
    }

    @FXML
    private void btnGenReportAction() {
    }

    private void setYears(List<Inventory> inventoryList) {
        ObservableList<String> yearList = FXCollections.observableArrayList();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String[] yearMin = dateFormat.format(inventoryList.get(0).getRegisterAt()).split("-");

        int min = Integer.parseInt(yearMin[0]);
        int max = Integer.parseInt(yearMin[0]);

        for (int i = 1; i < inventoryList.size(); i++) {
            String[] year = dateFormat.format(inventoryList.get(i).getRegisterAt()).split("-");
            if (min > Integer.parseInt(year[0])) {
                min = Integer.parseInt(year[0]);
            }
            if (max < Integer.parseInt(year[0])) {
                max = Integer.parseInt(year[0]);
            }
        }

        for (int i = min; i <= max; i++) {
            yearList.add(String.valueOf(i));
        }
        optYear.setItems(yearList);
        if (!yearList.isEmpty()) {
            optYear.setValue(yearList.get(0));
            setMonths();
        }
    }

    private void setMonths() {
        if (dashboardInventoryChart == null) {
            dashboardInventoryChart = DashboardCenterController.getInstance().getFxmlLoaderInventoryChart().getController();
        }
        List<String> monthList = dashboardInventoryChart.getMonth();
        ObservableList<String> monthOptList = FXCollections.observableArrayList();
        monthOptList.addAll(monthList);
    }

    private void validateInputs() {
        btnSearch.setDisable(txtProductId.getLength() == 0);
        btnCancel.setDisable(searchList.isEmpty());
    }

    private void clearForm() {
        searchList = new ArrayList<>();
        txtProductId.setText("");
        txtProductId.setDisable(false);
        optYear.setItems(null);
        optYear.setPromptText("   Select a Year");
        optYear.setDisable(true);
        validateInputs();
        if (dashboardInventoryChart == null) {
            dashboardInventoryChart = DashboardCenterController.getInstance().getFxmlLoaderInventoryChart().getController();
        }
        dashboardInventoryChart.clearChart();
    }

    @Override
    public void loadHeader() {
        btnSearch.setDisable(true);
        btnCancel.setDisable(true);
        btnGenReport.setDisable(true);
        optYear.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadHeader();
    }
}
