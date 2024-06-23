package edu.icet.pos.controller.dashboard;

import edu.icet.pos.controller.CenterController;
import edu.icet.pos.controller.auth.AuthCenterController;
import edu.icet.pos.controller.auth.custom.LoginPanelCustom;
import edu.icet.pos.controller.dashboard.custom.HeaderCustom;
import edu.icet.pos.controller.layout.LayoutCenterController;
import edu.icet.pos.controller.layout.custom.LayoutCustom;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class HeaderController implements HeaderCustom {
    @FXML
    private Separator separatorDev;
    @FXML
    private Button btnSwitchToDev;
    @FXML
    private HBox hBox;
    @FXML
    private Label dspMode;
    @FXML
    private Label dspDate;
    @FXML
    private Label dspTime;

    private LayoutCustom layoutCustom;
    private final LoginPanelCustom loginPanelCustom = AuthCenterController.getInstance().getFxmlLoaderLogin().getController();

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
        if(layoutCustom==null){
            layoutCustom = LayoutCenterController.getInstance().getFxmlLoaderLayout().getController();
        }
        loginPanelCustom.clearUser();
        layoutCustom.loadLogin();
    }

    @FXML
    private void switchToDevAction() {
        if(layoutCustom==null){
            layoutCustom = LayoutCenterController.getInstance().getFxmlLoaderLayout().getController();
        }
        loginPanelCustom.clearUser();
        layoutCustom.loadDashboard();
    }

    @FXML
    private void exitAction() {
        System.exit(0);
    }

    @Override
    public void authHeader() {
        if (loginPanelCustom.getLoginUser() == null && (Objects.equals(CenterController.getInstance().getMACAddress(), "FC-77-74-7E-9C-4E"))) {
            dspMode.setText("Developer");
            btnSwitchToDev.setVisible(false);
            separatorDev.setVisible(false);
        } else {
            dspMode.setText(loginPanelCustom.getLoginUser().getUserRole().getName().substring(0, 1).toUpperCase() + loginPanelCustom.getLoginUser().getUserRole().getName().substring(1));
            if(Objects.equals(CenterController.getInstance().getMACAddress(), "FC-77-74-7E-9C-4E")){
                btnSwitchToDev.setVisible(true);
                separatorDev.setVisible(true);
            } else {
                btnSwitchToDev.setVisible(false);
                separatorDev.setVisible(false);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
        authHeader();
    }
}
