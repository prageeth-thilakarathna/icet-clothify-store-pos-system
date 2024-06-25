package edu.icet.pos.controller.user.custom;

import edu.icet.pos.model.user.TblUserView;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public interface UserTable extends Initializable {
    TableView<TblUserView> getTable();
}
