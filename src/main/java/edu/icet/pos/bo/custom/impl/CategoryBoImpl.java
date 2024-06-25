package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.CategoryBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.CategoryDao;
import edu.icet.pos.entity.CategoryEntity;
import edu.icet.pos.model.category.Category;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

public class CategoryBoImpl implements CategoryBo {
    private final CategoryDao categoryDao = DaoFactory.getDao(DaoType.CATEGORY);

    @Override
    public void categoryRegister(Category category) {
        assert categoryDao != null;
        categoryDao.save(new ModelMapper().map(category, CategoryEntity.class));
    }

    @Override
    public Category getCategoryByName(String name) {
        assert categoryDao != null;
        return new ModelMapper().map(categoryDao.getByName(name), Category.class);
    }

    @Override
    public Category getCategory(Integer id) {
        assert categoryDao != null;
        return new ModelMapper().map(categoryDao.get(id), Category.class);
    }

    @Override
    public void categoryDelete(Category category) {
        assert categoryDao != null;
        categoryDao.delete(new ModelMapper().map(category, CategoryEntity.class));
    }

    @Override
    public void categoryUpdate(Category category) {
        assert categoryDao != null;
        categoryDao.update(new ModelMapper().map(category, CategoryEntity.class));
    }

    @Override
    public int getCategoryCount() {
        assert categoryDao != null;
        return categoryDao.count();
    }

    @Override
    public List<Category> getCategoryPerPage(int offset) {
        assert categoryDao != null;
        return new ModelMapper().map(categoryDao.getPerPage(offset), new TypeToken<List<Category>>() {}.getType());
    }
}
