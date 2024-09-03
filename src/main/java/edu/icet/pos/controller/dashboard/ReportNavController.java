package edu.icet.pos.controller.dashboard;

import edu.icet.pos.controller.auth.AuthCenterController;
import edu.icet.pos.controller.dashboard.custom.DashboardReportNav;
import edu.icet.pos.controller.dashboard.employee.custom.DashboardEmployeeHeader;
import edu.icet.pos.controller.dashboard.sales.custom.DashboardSalesHeader;
import edu.icet.pos.controller.dashboard.supplier.custom.DashboardSupplierHeader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportNavController implements DashboardReportNav {
    @FXML
    private HBox hBox;
    @FXML
    private Button btnInventory;
    @FXML
    private Button btnEmployee;
    @FXML
    private Button btnSupplier;
    @FXML
    private Button btnSales;

    @FXML
    private void btnInventoryAction() {
        btnInventory.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");
        btnEmployee.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnSupplier.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnSales.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        BorderPane reportBorderPane = DashboardCenterController.getInstance().getReportBorderPane();
        reportBorderPane.getChildren().removeAll(reportBorderPane.getChildren());

        reportBorderPane.setTop(DashboardCenterController.getInstance().getParentInventoryHeader());
        reportBorderPane.setCenter(DashboardCenterController.getInstance().getParentInventoryChart());
    }

    @FXML
    private void btnEmployeeAction() {
        btnEmployee.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");
        btnInventory.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnSupplier.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnSales.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        BorderPane reportBorderPane = DashboardCenterController.getInstance().getReportBorderPane();
        reportBorderPane.getChildren().removeAll(reportBorderPane.getChildren());

        reportBorderPane.setTop(DashboardCenterController.getInstance().getParentEmployeeHeader());
        reportBorderPane.setCenter(DashboardCenterController.getInstance().getParentEmployeeChart());

        DashboardEmployeeHeader dashboardEmployeeHeader = DashboardCenterController.getInstance().getFxmlLoaderEmployeeHeader().getController();
        dashboardEmployeeHeader.loadHeader();
    }

    @FXML
    private void btnSupplierAction() {
        btnSupplier.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");
        btnEmployee.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnInventory.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnSales.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        BorderPane reportBorderPane = DashboardCenterController.getInstance().getReportBorderPane();
        reportBorderPane.getChildren().removeAll(reportBorderPane.getChildren());

        reportBorderPane.setTop(DashboardCenterController.getInstance().getParentSupplierHeader());
        reportBorderPane.setCenter(DashboardCenterController.getInstance().getParentSupplierChart());

        DashboardSupplierHeader dashboardSupplierHeader = DashboardCenterController.getInstance().getFxmlLoaderSupplierHeader().getController();
        dashboardSupplierHeader.loadHeader();
    }

    @FXML
    private void btnSalesAction() {
        btnSales.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");
        btnEmployee.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnInventory.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        btnSupplier.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");
        BorderPane reportBorderPane = DashboardCenterController.getInstance().getReportBorderPane();
        reportBorderPane.getChildren().removeAll(reportBorderPane.getChildren());

        reportBorderPane.setTop(DashboardCenterController.getInstance().getParentSalesHeader());
        reportBorderPane.setCenter(DashboardCenterController.getInstance().getParentSalesChart());

        DashboardSalesHeader dashboardSalesHeader = DashboardCenterController.getInstance().getFxmlLoaderSalesHeader().getController();
        dashboardSalesHeader.loadHeader();
    }

    @Override
    public void setInventory() {
        btnInventoryAction();
    }

    @Override
    public void authNotify() {
        if(!AuthCenterController.isAdmin()){
            hBox.getChildren().remove(btnSales);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*ScrollPane scrollPaneDashboard = new ScrollPane();
        scrollPaneDashboard.setStyle("-fx-focus-color: transparent; -fx-background-color: transparent;");

        BorderPane dashboardBorderPane = DashboardCenterController.getInstance().getDashboardBorderPane();
        dashboardBorderPane.setCenter(scrollPaneDashboard);

        VBox pageCenter = CenterController.getInstance().getPageCenter();
        pageCenter.getChildren().clear();
        pageCenter.setPrefWidth(896);
        pageCenter.setPrefHeight(495);
        pageCenter.setAlignment(Pos.TOP_CENTER);
        scrollPaneDashboard.setContent(pageCenter);*/

        //btnInventoryAction();
    }
}
