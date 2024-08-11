package edu.icet.pos.controller.inventory.custom;

import javafx.fxml.Initializable;

public interface InventoryView extends Initializable {
    void updateTbl(String name);

    void loadTable();
}
