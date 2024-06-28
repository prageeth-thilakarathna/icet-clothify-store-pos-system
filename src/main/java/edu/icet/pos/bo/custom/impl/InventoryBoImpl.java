package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.InventoryBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.InventoryDao;
import edu.icet.pos.entity.InventoryEntity;
import edu.icet.pos.model.inventory.Inventory;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;

public class InventoryBoImpl implements InventoryBo {
    private final InventoryDao inventoryDao = DaoFactory.getDao(DaoType.INVENTORY);

    @Override
    public void inventoryRegister(Inventory inventory) {
        assert inventoryDao != null;
        inventoryDao.save(new ModelMapper().map(inventory, InventoryEntity.class));
    }
}
