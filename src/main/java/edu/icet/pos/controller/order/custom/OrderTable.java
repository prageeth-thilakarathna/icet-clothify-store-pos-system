package edu.icet.pos.controller.order.custom;

import edu.icet.pos.model.order.TblOrderView;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public interface OrderTable extends Initializable {
    TableView<TblOrderView> getTable();
}
