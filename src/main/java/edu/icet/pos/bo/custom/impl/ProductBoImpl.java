package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.ProductBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.ProductDao;
import edu.icet.pos.entity.ProductEntity;
import edu.icet.pos.model.product.Product;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

public class ProductBoImpl implements ProductBo {
    private final ProductDao productDao = DaoFactory.getDao(DaoType.PRODUCT);

    @Override
    public Product productRegister(Product product) {
        assert productDao != null;
        return new ModelMapper().map(productDao.save(new ModelMapper().map(product, ProductEntity.class)), Product.class);
    }

    @Override
    public Product getProduct(Integer id) {
        assert productDao != null;
        return new ModelMapper().map(productDao.get(id), Product.class);
    }

    @Override
    public void productUpdate(Product product) {
        assert productDao != null;
        productDao.update(new ModelMapper().map(product, ProductEntity.class));
    }

    @Override
    public void productDelete(Product product) {
        assert productDao != null;
        productDao.delete(new ModelMapper().map(product, ProductEntity.class));
    }

    @Override
    public int getProductCount() {
        assert productDao != null;
        return productDao.count();
    }

    @Override
    public List<Product> getProductPerPage(int offset) {
        assert productDao != null;
        return new ModelMapper().map(productDao.getPerPage(offset), new TypeToken<List<Product>>() {}.getType());
    }
}
