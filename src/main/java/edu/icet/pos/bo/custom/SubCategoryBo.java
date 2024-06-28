package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.model.category.Category;
import edu.icet.pos.model.sub_category.SubCategory;

import java.util.List;

public interface SubCategoryBo extends SuperBo {
    void subCategoryRegister(SubCategory subCategory);

    SubCategory getSubCategoryByName(String name);

    SubCategory getSubCategory(Integer id);

    void subCategoryUpdate(SubCategory subCategory);

    void subCategoryDelete(SubCategory subCategory);

    int getSubCategoryCount();

    List<SubCategory> getSubCategoryPerPage(int offset);

    List<SubCategory> getSubCategoryByCategory(Category category);
}
