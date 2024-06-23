package edu.icet.pos.main;

import edu.icet.pos.controller.layout.LayoutCenterController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(LayoutCenterController.getInstance().getParentLayout()));
        stage.setMaximized(true);
        stage.setTitle("Clothify Store");
        stage.show();
    }
}