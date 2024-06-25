package edu.icet.pos.controller.category.custom;

import edu.icet.pos.model.category.Category;
import javafx.fxml.Initializable;

public interface CategoryForm extends Initializable {
    void loadCategoryToForm(Category category);

    void clearCategory();
}
