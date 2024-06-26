package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.model.sub_category.SubCategory;

public interface SubCategoryBo extends SuperBo {
    void subCategoryRegister(SubCategory subCategory);

    SubCategory getSubCategoryByName(String name);
}
