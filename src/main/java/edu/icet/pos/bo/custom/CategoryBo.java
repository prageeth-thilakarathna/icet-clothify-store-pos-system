package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.model.category.Category;

import java.util.List;

public interface CategoryBo extends SuperBo {
    void categoryRegister(Category category);

    Category getCategoryByName(String name);

    Category getCategory(Integer id);

    void categoryDelete(Category category);

    void categoryUpdate(Category category);

    int getCategoryCount();

    List<Category> getCategoryPerPage(int offset);
}
