package edu.icet.pos.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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

    @OneToMany(mappedBy = "employee")
    private List<OrderEntity> orders;
}
