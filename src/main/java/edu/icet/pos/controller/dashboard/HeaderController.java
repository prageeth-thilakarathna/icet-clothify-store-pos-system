package edu.icet.pos.controller.dashboard;

import edu.icet.pos.controller.auth.AuthCenterController;
import edu.icet.pos.controller.dashboard.custom.DashboardHeader;
import edu.icet.pos.controller.layout.LayoutCenterController;
import edu.icet.pos.controller.layout.custom.Layout;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class HeaderController implements DashboardHeader {
    @FXML
    private Label dspMode;
    @FXML
    private Label dspDate;
    @FXML
    private Label dspTime;

    private Layout layout;

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

    @FXML
    private void profileAction(ActionEvent actionEvent) {
    }

    @FXML
    private void logOutAction() {
        if (layout == null) {
            layout = LayoutCenterController.getInstance().getFxmlLoaderLayout().getController();
        }
        AuthCenterController.getInstance().setUserLogin(null);
        layout.loadLogin();
    }

    @FXML
    private void exitAction() {
        System.exit(0);
    }

    @Override
    public void authNotify() {
        if (AuthCenterController.isUserAssistance()) {
            dspMode.setText("User (Assistance)");
        }

        if (AuthCenterController.isUserCashier()) {
            dspMode.setText("User (Cashier)");
        }

        if (AuthCenterController.isAdmin()) {
            dspMode.setText("Admin");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
        authNotify();
    }
}
