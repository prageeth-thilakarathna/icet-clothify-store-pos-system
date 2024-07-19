package edu.icet.pos.controller.dashboard.custom;

import edu.icet.pos.controller.custom.SuperController;

public interface DashboardNavPanel extends SuperController {
    void loadCategory();

    void loadInitializer();

    void loadOrder();
}
