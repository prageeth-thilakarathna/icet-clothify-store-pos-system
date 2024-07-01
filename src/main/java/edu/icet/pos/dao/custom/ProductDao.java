package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.SuperDao;
import edu.icet.pos.entity.ProductEntity;

public interface ProductDao extends SuperDao {
    ProductEntity save(ProductEntity productEntity);

    ProductEntity get(Integer id);

    void update(ProductEntity productEntity);

    void delete(ProductEntity productEntity);
}
