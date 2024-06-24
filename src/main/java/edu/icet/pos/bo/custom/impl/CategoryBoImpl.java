package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.CategoryBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.CategoryDao;
import edu.icet.pos.entity.CategoryEntity;
import edu.icet.pos.model.Category;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;

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
}
