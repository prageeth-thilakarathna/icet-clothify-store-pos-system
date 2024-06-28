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
@Table(name = "inventory")
public class InventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "productId", foreignKey = @ForeignKey(name = "fk_product_inventory"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductEntity product;

    private Integer stock;
    private Date registerAt;
    private Date modifyAt;
    private Boolean isActive;
}
