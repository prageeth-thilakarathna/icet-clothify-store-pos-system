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
@Table(name = "sub_category")
public class SubCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Date registerAt;
    private Date modifyAt;
    private Boolean isActive;

    @ManyToOne()
    @JoinColumn(name = "categoryId", foreignKey = @ForeignKey(name = "fk_category_sub_category"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CategoryEntity category;
}
