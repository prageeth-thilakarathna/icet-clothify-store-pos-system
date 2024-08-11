package edu.icet.pos.model.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TblInventoryView {
    private String id;
    private String stock;
    private String registerAt;
    private String productId;
}
