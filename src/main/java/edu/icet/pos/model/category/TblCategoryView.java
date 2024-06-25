package edu.icet.pos.model.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TblCategoryView {
    private String id;
    private String name;
    private String registerAt;
    private String modifyAt;
    private String status;
}
