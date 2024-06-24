package edu.icet.pos.controller.dashboard;

import edu.icet.pos.controller.CenterController;
import edu.icet.pos.controller.auth.AuthCenterController;
import edu.icet.pos.controller.auth.custom.LoginPanelCustom;
import edu.icet.pos.controller.dashboard.custom.NavPanelCustom;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class NavPanelController implements NavPanelCustom {
    @FXML
    private Button btnSupplier;
    @FXML
    private Button btnProduct;
    @FXML
    private Button btnInventory;
    @FXML
    private Button btnEmployee;
    @FXML
    private Button btnOrder;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox navSectionVBox;
    @FXML
    private Button btnCategory;
    @FXML
    private Button btnUser;
    @FXML
    private Button btnDashboard;

    private LoginPanelCustom loginPanelCustom = AuthCenterController.getInstance().getFxmlLoaderLoginPanel().getController();

    @FXML
    private void btnUserAction(ActionEvent actionEvent) throws IOException {
        btnUser.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");
        btnDashboard.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");

        Label pageHeader = CenterController.getInstance().getPageHeader();
        pageHeader.setText("User");

        BorderPane pageBorderPane = CenterController.getInstance().getPageBorderPane();
        VBox pageCenter = CenterController.getInstance().getPageCenter();

        Parent parentCenter = new FXMLLoader(getClass().getResource("/view/user/form.fxml")).load();
        pageCenter.getChildren().clear();
        pageCenter.getChildren().add(parentCenter);

        pageBorderPane.setCenter(pageCenter);


    }

    @FXML
    private void btnDashboardAction(ActionEvent actionEvent) {
        btnDashboard.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");
        btnUser.setStyle("-fx-background-color: #159493; -fx-background-radius: 10px;");

        Label pageHeader = CenterController.getInstance().getPageHeader();
        pageHeader.setText("Dashboard");
        //scrollPane.setPrefHeight(398);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnDashboard.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");
        if (loginPanelCustom.getLoginUser() != null && Objects.equals(loginPanelCustom.getLoginUser().getUserRole().getName(), "user")) {
            navSectionVBox.getChildren().remove(btnUser);
            navSectionVBox.getChildren().remove(btnCategory);
            navSectionVBox.getChildren().remove(btnEmployee);
            navSectionVBox.setPrefHeight(278);
            scrollPane.setPrefHeight(280);
        }

    }
}
