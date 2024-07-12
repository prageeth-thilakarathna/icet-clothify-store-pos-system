package edu.icet.pos.controller.dashboard;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.io.IOException;

@Getter
public class DashboardCenterController {
    @Getter
    private static final DashboardCenterController instance;
    private final FXMLLoader fxmlLoaderHeader = new FXMLLoader(getClass().getResource("/view/dashboard/header.fxml"));
    private final Parent parentHeader;
    private final FXMLLoader fxmlLoaderInventory = new FXMLLoader(getClass().getResource("/view/dashboard/inventory-report.fxml"));
    private final Parent parentInventory;
    private final FXMLLoader fxmlLoaderReportNav = new FXMLLoader(getClass().getResource("/view/dashboard/report-nav.fxml"));
    private final Parent parentReportNav;
    private final BorderPane dashboardBorderPane = new BorderPane();
    private final VBox dashboardNavPanel = new VBox();

    private DashboardCenterController() throws IOException {
        parentHeader = fxmlLoaderHeader.load();
        parentInventory = fxmlLoaderInventory.load();
        parentReportNav = fxmlLoaderReportNav.load();

        dashboardNavPanel.setPrefWidth(896);
        dashboardNavPanel.setPrefHeight(58);
        dashboardNavPanel.setAlignment(Pos.TOP_CENTER);
    }

    static {
        try{
            instance = new DashboardCenterController();
        } catch (Exception e){
            throw new RuntimeException("Exception occurred in creating DashboardCenterController singleton instance");
        }
    }

}
