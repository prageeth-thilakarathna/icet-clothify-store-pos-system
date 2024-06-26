package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.SuperDao;
import edu.icet.pos.entity.SubCategoryEntity;

public interface SubCategoryDao extends SuperDao {
    void save(SubCategoryEntity subCategoryEntity);

    SubCategoryEntity getByName(String name);
}
