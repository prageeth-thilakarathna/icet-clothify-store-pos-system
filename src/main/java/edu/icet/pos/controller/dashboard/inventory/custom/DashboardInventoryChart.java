package edu.icet.pos.controller.dashboard.inventory.custom;

import edu.icet.pos.model.inventory.Inventory;
import javafx.fxml.Initializable;

import java.util.List;

public interface DashboardInventoryChart {
    void loadChart(List<Inventory> inventoryList);

    void clearChart();

    List<String> getMonth();
}
