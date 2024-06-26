package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.SubCategoryBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.SubCategoryDao;
import edu.icet.pos.entity.SubCategoryEntity;
import edu.icet.pos.model.sub_category.SubCategory;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;

public class SubCategoryBoImpl implements SubCategoryBo {
    private final SubCategoryDao subCategoryDao = DaoFactory.getDao(DaoType.SUB_CATEGORY);

    @Override
    public void subCategoryRegister(SubCategory subCategory) {
        assert subCategoryDao != null;
        subCategoryDao.save(new ModelMapper().map(subCategory, SubCategoryEntity.class));
    }

    @Override
    public SubCategory getSubCategoryByName(String name) {
        assert subCategoryDao != null;
        return new ModelMapper().map(subCategoryDao.getByName(name), SubCategory.class);
    }

    @Override
    public SubCategory getSubCategory(Integer id) {
        assert subCategoryDao != null;
        return new ModelMapper().map(subCategoryDao.get(id), SubCategory.class);
    }

    @Override
    public void subCategoryUpdate(SubCategory subCategory) {
        assert subCategoryDao != null;
        subCategoryDao.update(new ModelMapper().map(subCategory, SubCategoryEntity.class));
    }

    @Override
    public void subCategoryDelete(SubCategory subCategory) {
        assert subCategoryDao != null;
        subCategoryDao.delete(new ModelMapper().map(subCategory, SubCategoryEntity.class));
    }
}
