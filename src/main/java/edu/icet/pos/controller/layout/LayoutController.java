package edu.icet.pos.controller.layout;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.controller.CenterController;
import edu.icet.pos.controller.auth.AuthCenterController;
import edu.icet.pos.controller.custom.SuperController;
import edu.icet.pos.controller.dashboard.DashboardCenterController;
import edu.icet.pos.controller.dashboard.custom.DashboardNavPanel;
import edu.icet.pos.controller.layout.custom.Layout;
import edu.icet.pos.util.BoType;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LayoutController implements Layout {
    @FXML
    private BorderPane borderPane;

    @Override
    public BorderPane getBorderPane() {
        return borderPane;
    }

    @Override
    public void loadDashboard() {
        List<SuperController> superControllerList = CenterController.getInstance().getSuperControllerList();
        borderPane.getChildren().removeAll(borderPane.getChildren());

        borderPane.setTop(DashboardCenterController.getInstance().getParentHeader());

        try {
            borderPane.setLeft(DashboardCenterController.getInstance().getParentNav());
            borderPane.setCenter(CenterController.getInstance().getPageBorderPane());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }

        for (SuperController superController : superControllerList) {
            superController.authNotify();
        }
        DashboardNavPanel dashboardNavPanel = DashboardCenterController.getInstance().getFxmlLoaderNav().getController();
        dashboardNavPanel.loadInitializer();
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
        //loadLogin();
        UserBo userBo = BoFactory.getBo(BoType.USER);
        assert userBo != null;
        AuthCenterController.getInstance().setUserLogin(userBo.getUserByEmail("user@email.com"));
        loadDashboard();
    }
}
