package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.SuperDao;
import edu.icet.pos.entity.CategoryEntity;

import java.util.List;

public interface CategoryDao extends SuperDao {
    void save(CategoryEntity categoryEntity);

    CategoryEntity getByName(String name);

    CategoryEntity get(Integer id);

    void delete(CategoryEntity categoryEntity);

    void update(CategoryEntity categoryEntity);

    int count();

    List<CategoryEntity> getPerPage(int offset);

    List<CategoryEntity> getAll();
}
