package edu.icet.pos.controller.layout.custom;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public interface Layout extends Initializable {
    BorderPane getBorderPane();

    void loadDashboard();

    void loadLogin();

    FXMLLoader getNavPanel();
}
