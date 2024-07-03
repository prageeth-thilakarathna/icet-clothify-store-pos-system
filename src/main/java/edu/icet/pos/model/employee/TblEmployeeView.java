package edu.icet.pos.model.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TblEmployeeView {
    private String id;
    private String fullName;
    private String userEMail;
    private String jobRole;
    private String contact;
    private String address;
    private String registerAt;
    private String modifyAt;
    private String status;
}
