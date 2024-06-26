package edu.icet.pos.controller.sub_category.custom;

import edu.icet.pos.model.sub_category.TblSubCategoryView;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public interface SubCategoryTable extends Initializable {
    TableView<TblSubCategoryView> getTable();
}
