package edu.icet.pos.controller.sub_category.custom;

import edu.icet.pos.model.sub_category.SubCategory;
import javafx.fxml.Initializable;

public interface SubCategoryForm extends Initializable {
    void loadSubCategoryToForm(SubCategory subCategory);

    void clearSubCategory();
}
