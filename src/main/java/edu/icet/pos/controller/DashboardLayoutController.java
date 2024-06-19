package edu.icet.pos.controller;

import edu.icet.pos.controller.user.FormController;
import edu.icet.pos.controller.user.UserCenterController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class DashboardLayoutController implements Initializable {
    @FXML
    private Label dspDate;
    @FXML
    private Label dspTime;
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

    //private FXMLLoader fxmlLoaderForm;

    @FXML
    private void btnUserAction() throws IOException {

        /*btnUser.setStyle("-fx-background-color: #0c7675; -fx-background-radius: 10px;");
        centerVBox.getChildren().clear();
        centerVBox.getChildren().add(parent);*/
    }

    // set data and time
    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dspDate.setText(simpleDateFormat.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime localTime = LocalTime.now();
            dspTime.setText(localTime.getHour() + ":" + localTime.getMinute() + ":" + localTime.getSecond());
        }), new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        header.setText("User");
        loadDateAndTime();
        try{
            //FormController formController = FormController.getInstance();
            FXMLLoader fxmlLoaderForm = UserCenterController.getInstance().getFxmlLoaderForm();
            //fxmlLoaderForm = new FXMLLoader(getClass().getResource("/view/user/form.fxml"));
            //fxmlLoaderForm.setController(formController);

            Parent parentForm = fxmlLoaderForm.load();

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
            System.out.println(e.getMessage());
        }
    }
}
