package edu.icet.pos.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardLayoutController implements Initializable {
    @FXML
    private VBox bottomVBox;
    @FXML
    private VBox rightVBox;
    @FXML
    private VBox centerVBox;
    @FXML
    private Label header;
    @FXML
    private Button btnUser;

    @FXML
    private void btnUserAction() throws IOException {

        /*btnUser.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");
        centerVBox.getChildren().clear();
        centerVBox.getChildren().add(parent);*/
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        header.setText("User");
        try{
            Parent parentForm = new FXMLLoader(getClass().getResource("/view/user/form.fxml")).load();
            centerVBox.getChildren().clear();
            centerVBox.getChildren().add(parentForm);
            btnUser.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");

            Parent parentSearch = new FXMLLoader(getClass().getResource("/view/user/search.fxml")).load();
            rightVBox.getChildren().clear();
            rightVBox.getChildren().add(parentSearch);

            Parent parentView = new FXMLLoader(getClass().getResource("/view/user/view.fxml")).load();
            bottomVBox.getChildren().clear();
            bottomVBox.getChildren().add(parentView);
        } catch (IOException e) {

        }
    }
}
