package edu.icet.pos.controller.layout;

import edu.icet.pos.controller.CenterController;
import edu.icet.pos.controller.auth.AuthCenterController;
import edu.icet.pos.controller.dashboard.DashboardCenterController;
import edu.icet.pos.controller.dashboard.custom.DashboardHeader;
import edu.icet.pos.controller.layout.custom.Layout;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LayoutController implements Layout {
    @FXML
    private BorderPane borderPane;

    private final DashboardHeader dashboardHeader = DashboardCenterController.getInstance().getFxmlLoaderHeader().getController();
    private FXMLLoader fxmlLoaderNavPanel;

    @Override
    public BorderPane getBorderPane() {
        return borderPane;
    }

    @Override
    public void loadDashboard() {
        borderPane.getChildren().removeAll(borderPane.getChildren());

        VBox topVBox = new VBox();
        topVBox.setPrefWidth(1169);
        topVBox.setPrefHeight(60);
        topVBox.getChildren().add(DashboardCenterController.getInstance().getParentHeader());
        borderPane.setTop(topVBox);
        dashboardHeader.authHeader();

        VBox leftVBox = new VBox();
        leftVBox.setPrefWidth(258);
        leftVBox.setStyle("-fx-background-color: #d9d9d9;");

        try{
            FXMLLoader fxmlLoaderNav = new FXMLLoader(getClass().getResource("/view/dashboard/navPanel.fxml"));
            fxmlLoaderNavPanel = fxmlLoaderNav;
            Parent parentNavPanel = fxmlLoaderNav.load();
            leftVBox.getChildren().add(parentNavPanel);
            borderPane.setLeft(leftVBox);
            borderPane.setCenter(CenterController.getInstance().getPageBorderPane());
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @Override
    public void loadLogin() {
        borderPane.getChildren().removeAll(borderPane.getChildren());

        VBox centerVBox = new VBox();
        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.setStyle("-fx-background-color: #353c52;");
        centerVBox.getChildren().add(AuthCenterController.getInstance().getParentBanner());
        borderPane.setCenter(centerVBox);

        VBox rightVBox = new VBox();
        rightVBox.setAlignment(Pos.BOTTOM_RIGHT);
        rightVBox.setPrefWidth(585);
        rightVBox.setPrefHeight(658);
        rightVBox.getChildren().add(AuthCenterController.getInstance().getParentLoginPanel());
        borderPane.setRight(rightVBox);
    }

    @Override
    public FXMLLoader getNavPanel() {
        return fxmlLoaderNavPanel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Objects.equals(CenterController.getInstance().getMACAddress(), "FC-77-74-7E-9C-4E")){
            loadDashboard();
        } else {
            loadLogin();
        }
    }
}
