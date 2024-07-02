package edu.icet.pos.model.employee;

import edu.icet.pos.entity.JobRoleEntity;
import edu.icet.pos.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Integer id;
    private UserEntity user;
    private JobRoleEntity jobRole;
    private String title;
    private String firstName;
    private String lastName;
    private String contact;
    private String address;
    private Date registerAt;
    private Date modifyAt;
    private Boolean isActive;
}
