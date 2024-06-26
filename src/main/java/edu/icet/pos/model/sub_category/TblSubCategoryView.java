package edu.icet.pos.model.sub_category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TblSubCategoryView {
    private String id;
    private String categoryName;
    private String subCategoryName;
    private String registerAt;
    private String modifyAt;
    private String status;
}
