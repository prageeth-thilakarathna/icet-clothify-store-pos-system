package edu.icet.pos.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "fk_user_employee"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "jobRoleId", foreignKey = @ForeignKey(name = "fk_job_role_employee"))
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
