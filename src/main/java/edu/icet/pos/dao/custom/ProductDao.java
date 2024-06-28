package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.SuperDao;
import edu.icet.pos.entity.ProductEntity;

public interface ProductDao extends SuperDao {
    void save(ProductEntity productEntity);
}
