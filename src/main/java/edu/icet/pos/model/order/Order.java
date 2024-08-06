package edu.icet.pos.model.order;

import edu.icet.pos.entity.EmployeeEntity;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Order {
    private Integer id;
    private EmployeeEntity employee;
    private String employeeName;
    private String customerName;
    private String customerEMail;
    private String paymentType;
    private Date registerAt;
    private Date returnAt;
}
