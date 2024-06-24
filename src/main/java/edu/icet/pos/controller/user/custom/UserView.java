package edu.icet.pos.controller.user.custom;

import javafx.fxml.Initializable;

public interface UserView extends Initializable {
    void updateTbl(String name);

    void loadTable();
}
