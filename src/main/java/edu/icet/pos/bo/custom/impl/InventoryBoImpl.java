package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.InventoryBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.InventoryDao;
import edu.icet.pos.entity.InventoryEntity;
import edu.icet.pos.model.inventory.Inventory;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

public class InventoryBoImpl implements InventoryBo {
    private final InventoryDao inventoryDao = DaoFactory.getDao(DaoType.INVENTORY);

    @Override
    public void inventoryRegister(Inventory inventory) {
        assert inventoryDao != null;
        inventoryDao.save(new ModelMapper().map(inventory, InventoryEntity.class));
    }

    @Override
    public void qtyOnHandRegister(Inventory inventory) {
        assert inventoryDao != null;
        inventoryDao.saveQtyOnHand(new ModelMapper().map(inventory, InventoryEntity.class));
    }

    @Override
    public Inventory getInventory(Integer id) {
        assert inventoryDao != null;
        return new ModelMapper().map(inventoryDao.get(id), Inventory.class);
    }

    @Override
    public int getInventoryCount() {
        assert inventoryDao != null;
        return inventoryDao.count();
    }

    @Override
    public List<Inventory> getInventoryPerPage(int offset) {
        assert inventoryDao != null;
        return new ModelMapper().map(inventoryDao.getPerPage(offset), new TypeToken<List<Inventory>>() {
        }.getType());
    }

    @Override
    public List<Inventory> getStockByProductId(Integer productId) {
        assert inventoryDao != null;
        return new ModelMapper().map(inventoryDao.getStock(productId), new TypeToken<List<Inventory>>() {
        }.getType());
    }
}
