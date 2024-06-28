package edu.icet.pos.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "subCategoryId", foreignKey = @ForeignKey(name = "fk_sub_category_product"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubCategoryEntity subCategory;

    @ManyToOne
    @JoinColumn(name = "supplierId", foreignKey = @ForeignKey(name = "fk_supplier_product"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SupplierEntity supplier;

    private String description;
    private String size;
    private Double price;
    private Integer availableQuantity;
    private Blob image;
    private Date registerAt;
    private Date modifyAt;
    private Boolean isActive;

    @OneToMany(mappedBy = "product")
    private List<InventoryEntity> inventory;
}
