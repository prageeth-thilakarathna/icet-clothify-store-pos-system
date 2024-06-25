package edu.icet.pos.controller.category.custom;

import javafx.fxml.Initializable;

public interface CategoryView extends Initializable {
    void updateTbl(String name);

    void loadTable();
}
