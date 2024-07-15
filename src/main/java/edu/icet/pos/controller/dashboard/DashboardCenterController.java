package edu.icet.pos.controller.dashboard;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
public class DashboardCenterController {
    @Getter
    private static final DashboardCenterController instance;
    private final FXMLLoader fxmlLoaderHeader = new FXMLLoader(getClass().getResource("/view/dashboard/header.fxml"));
    private final Parent parentHeader;

    @Setter
    private FXMLLoader fxmlLoaderNav;
    @Setter
    private Parent parentNav;

    private final FXMLLoader fxmlLoaderReportNav = new FXMLLoader(getClass().getResource("/view/dashboard/report-nav.fxml"));
    private final Parent parentReportNav;
    private final BorderPane dashboardBorderPane = new BorderPane();
    private final VBox dashboardNavPanel = new VBox();
    private final BorderPane reportBorderPane = new BorderPane();
    private final FXMLLoader fxmlLoaderInventoryHeader = new FXMLLoader(getClass().getResource("/view/dashboard/inventory/header.fxml"));
    private final Parent parentInventoryHeader;
    private final FXMLLoader fxmlLoaderInventoryChart = new FXMLLoader(getClass().getResource("/view/dashboard/inventory/chart.fxml"));
    private final Parent parentInventoryChart;

    private DashboardCenterController() throws IOException {
        parentHeader = fxmlLoaderHeader.load();
        parentReportNav = fxmlLoaderReportNav.load();
        parentInventoryHeader = fxmlLoaderInventoryHeader.load();
        parentInventoryChart = fxmlLoaderInventoryChart.load();

        dashboardNavPanel.setPrefWidth(896);
        dashboardNavPanel.setPrefHeight(58);
        dashboardNavPanel.setAlignment(Pos.TOP_CENTER);

        reportBorderPane.setTop(parentInventoryHeader);
        reportBorderPane.setCenter(parentInventoryChart);

        dashboardBorderPane.setTop(dashboardNavPanel);
        dashboardBorderPane.setCenter(reportBorderPane);
    }

    static {
        try{
            instance = new DashboardCenterController();
        } catch (Exception e){
            throw new RuntimeException("Exception occurred in creating DashboardCenterController singleton instance");
        }
    }

}
