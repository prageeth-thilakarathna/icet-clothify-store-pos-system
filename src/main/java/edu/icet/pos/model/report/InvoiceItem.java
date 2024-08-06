package edu.icet.pos.model.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InvoiceItem {
    private String description;
    private String quantity;
    private String unitPrice;
    private String total;
}
