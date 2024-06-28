package edu.icet.pos.model.product;

import edu.icet.pos.entity.SubCategoryEntity;
import edu.icet.pos.entity.SupplierEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Integer id;
    private SubCategoryEntity subCategory;
    private SupplierEntity supplier;
    private String description;
    private String size;
    private Double price;
    private Integer availableQuantity;
    private Blob image;
    private Date registerAt;
    private Date modifyAt;
    private Boolean isActive;
}
