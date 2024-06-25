package edu.icet.pos.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TblUserView {
    private String id;
    private String eMail;
    private String userRole;
    private String registerAt;
    private String modifyAt;
    private String status;
}
