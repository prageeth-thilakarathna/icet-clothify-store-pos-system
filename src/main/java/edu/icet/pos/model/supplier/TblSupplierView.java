package edu.icet.pos.model.supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TblSupplierView {
    private String id;
    private String fullName;
    private String contact;
    private String address;
    private String registerAt;
    private String modifyAt;
    private String status;
}
