package edu.icet.pos.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String eMail;
    private String password;
    private Date registerAt;
    private Date modifyAt;
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "userRoleId", foreignKey = @ForeignKey(name = "fk_user_role_user"))
    private UserRoleEntity userRole;
}
