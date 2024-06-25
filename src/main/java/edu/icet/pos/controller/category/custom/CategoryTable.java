package edu.icet.pos.controller.category.custom;

import edu.icet.pos.model.category.TblCategoryView;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public interface CategoryTable extends Initializable {
    TableView<TblCategoryView> getTable();
}
