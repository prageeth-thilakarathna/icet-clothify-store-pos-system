package edu.icet.pos.controller.inventory.custom;

import edu.icet.pos.model.inventory.TblInventoryView;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public interface InventoryTable extends Initializable {
    TableView<TblInventoryView> getTable();
}
