package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.SuperDao;
import edu.icet.pos.entity.InventoryEntity;

import java.util.List;

public interface InventoryDao extends SuperDao {
    void save(InventoryEntity inventoryEntity);

    void saveQtyOnHand(InventoryEntity inventoryEntity);

    InventoryEntity get(Integer id);

    int count();

    List<InventoryEntity> getPerPage(int offset);
}
