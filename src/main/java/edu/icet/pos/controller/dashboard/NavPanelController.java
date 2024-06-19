package edu.icet.pos.controller.dashboard;

import edu.icet.pos.controller.CenterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NavPanelController implements Initializable {
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
    }
}
