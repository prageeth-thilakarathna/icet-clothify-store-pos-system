package edu.icet.pos.controller.supplier.custom;

import edu.icet.pos.model.supplier.TblSupplierView;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public interface SupplierTable extends Initializable {
    TableView<TblSupplierView> getTable();
}
