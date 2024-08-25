package edu.icet.pos.model.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InventoryReport {
    private String id;
    private String stock;
    private String date;
}
