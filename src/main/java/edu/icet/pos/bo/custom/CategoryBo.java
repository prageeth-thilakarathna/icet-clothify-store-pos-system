package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.model.Category;

public interface CategoryBo extends SuperBo {
    void categoryRegister(Category category);

    Category getCategoryByName(String name);
}
