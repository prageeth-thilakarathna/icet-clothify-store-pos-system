package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.ProductBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.ProductDao;
import edu.icet.pos.entity.ProductEntity;
import edu.icet.pos.model.product.Product;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;

public class ProductBoImpl implements ProductBo {
    private final ProductDao productDao = DaoFactory.getDao(DaoType.PRODUCT);

    @Override
    public void productRegister(Product product) {
        assert productDao != null;
        productDao.save(new ModelMapper().map(product, ProductEntity.class));
    }
}
