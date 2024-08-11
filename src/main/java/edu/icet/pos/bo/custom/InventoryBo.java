package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.model.inventory.Inventory;

import java.util.List;

public interface InventoryBo extends SuperBo {
    void inventoryRegister(Inventory inventory);

    void qtyOnHandRegister(Inventory inventory);

    Inventory getInventory(Integer id);

    int getInventoryCount();

    List<Inventory> getInventoryPerPage(int offset);
}
