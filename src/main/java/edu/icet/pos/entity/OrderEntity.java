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
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employeeId", foreignKey = @ForeignKey(name = "fk_employee_orders"))
    private EmployeeEntity employee;

    private String employeeName;
    private String customerName;
    private String customerEMail;
    private String paymentType;
    private Date registerAt;
    private Date returnAt;

    @OneToMany(mappedBy = "orders")
    private List<OrderDetailEntity> orderDetail;
}
