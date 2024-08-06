package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.SuperDao;
import edu.icet.pos.entity.ProductEntity;

import java.util.List;

public interface ProductDao extends SuperDao {
    ProductEntity save(ProductEntity productEntity);

    ProductEntity get(Integer id);

    void update(ProductEntity productEntity);

    void delete(ProductEntity productEntity);

    int count(boolean filter);

    List<ProductEntity> getPerPage(boolean filter, int limit, int offset);

    List<ProductEntity> getPerPage(String ids, int limit, int offset);

    int countByFilter(String ids);

    void updateAvaQty(ProductEntity productEntity);
}
