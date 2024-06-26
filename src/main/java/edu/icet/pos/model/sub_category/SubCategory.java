package edu.icet.pos.model.sub_category;

import edu.icet.pos.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubCategory {
    private Integer id;
    private String name;
    private Date registerAt;
    private Date modifyAt;
    private Boolean isActive;
    private CategoryEntity category;
}
