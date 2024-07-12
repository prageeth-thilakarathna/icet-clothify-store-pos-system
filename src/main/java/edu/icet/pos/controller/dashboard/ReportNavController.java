package edu.icet.pos.controller.dashboard;

import edu.icet.pos.controller.CenterController;
import edu.icet.pos.controller.dashboard.custom.DashboardReportNav;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportNavController implements DashboardReportNav {
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
        VBox pageCenter = CenterController.getInstance().getPageCenter();
        pageCenter.getChildren().clear();
        pageCenter.getChildren().add(DashboardCenterController.getInstance().getParentInventory());
    }

    @FXML
    private void btnEmployeeAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnSupplierAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnSalesAction(ActionEvent actionEvent) {
    }

    @Override
    public void setInventory() {
        btnInventoryAction();
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
