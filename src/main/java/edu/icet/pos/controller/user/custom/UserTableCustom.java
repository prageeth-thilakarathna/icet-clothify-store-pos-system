package edu.icet.pos.controller.user.custom;

import edu.icet.pos.model.TblUserView;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public interface UserTableCustom extends Initializable {
    TableView<TblUserView> getTable();
}
