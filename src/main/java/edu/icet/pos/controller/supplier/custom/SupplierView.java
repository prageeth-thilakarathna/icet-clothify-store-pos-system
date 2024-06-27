package edu.icet.pos.controller.supplier.custom;

import javafx.fxml.Initializable;

public interface SupplierView extends Initializable {
    void updateTbl(String name);

    void loadTable();
}
