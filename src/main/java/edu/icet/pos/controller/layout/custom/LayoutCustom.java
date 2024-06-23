package edu.icet.pos.controller.layout.custom;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public interface LayoutCustom extends Initializable {
    BorderPane getBorderPane();

    void loadDashboard();

    void loadLogin();
}
