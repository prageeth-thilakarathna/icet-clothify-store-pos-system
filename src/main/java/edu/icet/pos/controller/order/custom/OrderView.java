package edu.icet.pos.controller.order.custom;

import edu.icet.pos.controller.custom.SuperController;

public interface OrderView extends SuperController {
    void loadTable();

    void updatePanel(String name);
}
