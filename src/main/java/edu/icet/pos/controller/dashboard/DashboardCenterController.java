package edu.icet.pos.controller.dashboard;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;

import java.io.IOException;

@Getter
public class DashboardCenterController {
    @Getter
    private static final DashboardCenterController instance;
    private final FXMLLoader fxmlLoaderHeader = new FXMLLoader(getClass().getResource("/view/dashboard/header.fxml"));
    private final Parent parentHeader;
    private final FXMLLoader fxmlLoaderNavPanel = new FXMLLoader(getClass().getResource("/view/dashboard/navPanel.fxml"));
    private final Parent parentNavPanel;

    private DashboardCenterController() throws IOException {
        parentHeader = fxmlLoaderHeader.load();
        parentNavPanel = fxmlLoaderNavPanel.load();
    }

    static {
        try{
            instance = new DashboardCenterController();
        } catch (Exception e){
            throw new RuntimeException("Exception occurred in creating DashboardCenterController singleton instance");
        }
    }

}
