package edu.icet.pos.controller.layout;

import edu.icet.pos.controller.CenterController;
import edu.icet.pos.controller.auth.AuthCenterController;
import edu.icet.pos.controller.dashboard.DashboardCenterController;
import edu.icet.pos.controller.dashboard.custom.HeaderCustom;
import edu.icet.pos.controller.layout.custom.LayoutCustom;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LayoutController implements LayoutCustom {
    @FXML
    private BorderPane borderPane;

    private HeaderCustom headerCustom = DashboardCenterController.getInstance().getFxmlLoaderHeader().getController();

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
        headerCustom.authHeader();

        VBox leftVBox = new VBox();
        leftVBox.setPrefWidth(258);
        leftVBox.setStyle("-fx-background-color: #d9d9d9;");
        leftVBox.getChildren().add(DashboardCenterController.getInstance().getParentNavPanel());
        borderPane.setLeft(leftVBox);
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Objects.equals(CenterController.getInstance().getMACAddress(), "FC-77-74-7E-9C-4E")){
            loadDashboard();
        } else {
            loadLogin();
        }
    }
}
