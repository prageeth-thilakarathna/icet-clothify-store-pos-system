package edu.icet.pos.model.supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
    private Integer id;
    private String title;
    private String firstName;
    private String lastName;
    private String contact;
    private String address;
    private Date registerAt;
    private Date modifyAt;
    private Boolean isActive;
}
