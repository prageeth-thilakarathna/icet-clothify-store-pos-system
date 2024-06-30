package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.SuperDao;
import edu.icet.pos.entity.InventoryEntity;

public interface InventoryDao extends SuperDao {
    void save(InventoryEntity inventoryEntity);

    void saveQtyOnHand(InventoryEntity inventoryEntity);
}
