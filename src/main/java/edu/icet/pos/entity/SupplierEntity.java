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
@Table(name = "supplier")
public class SupplierEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String firstName;
    private String lastName;
    private String contact;
    private String address;
    private Date registerAt;
    private Date modifyAt;
    private Boolean isActive;

    @OneToMany(mappedBy = "supplier")
    private List<ProductEntity> product;
}
