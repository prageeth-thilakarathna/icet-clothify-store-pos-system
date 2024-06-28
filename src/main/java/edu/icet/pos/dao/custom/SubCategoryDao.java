package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.SuperDao;
import edu.icet.pos.entity.CategoryEntity;
import edu.icet.pos.entity.SubCategoryEntity;

import java.util.List;

public interface SubCategoryDao extends SuperDao {
    void save(SubCategoryEntity subCategoryEntity);

    SubCategoryEntity getByName(String name);

    SubCategoryEntity get(Integer id);

    void update(SubCategoryEntity subCategoryEntity);

    void delete(SubCategoryEntity subCategoryEntity);

    int count();

    List<SubCategoryEntity> getPerPage(int offset);

    List<SubCategoryEntity> getByCategory(CategoryEntity categoryEntity);
}
