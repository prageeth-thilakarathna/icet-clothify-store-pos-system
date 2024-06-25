package edu.icet.pos.model.user;

import edu.icet.pos.entity.UserRoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String eMail;
    private String password;
    private Date registerAt;
    private Date modifyAt;
    private Boolean isActive;
    private UserRoleEntity userRole;
}
