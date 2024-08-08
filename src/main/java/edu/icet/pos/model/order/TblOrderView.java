package edu.icet.pos.model.order;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TblOrderView {
    private String id;
    private String customerName;
    private String employeeName;
    private String paymentType;
    private String netTotal;
    private String registerAt;
    private String returnStatus;
}
