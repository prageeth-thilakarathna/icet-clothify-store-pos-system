package edu.icet.pos.bo;

import edu.icet.pos.bo.custom.impl.*;
import edu.icet.pos.util.BoType;

public class BoFactory {
    private BoFactory(){}

    public static <T extends SuperBo> T getBo(BoType boType){
        switch (boType){
            case USER_ROLE: return (T) new UserRoleBoImpl();
            case USER: return (T) new UserBoImpl();
            case CATEGORY: return (T) new CategoryBoImpl();
            case SUB_CATEGORY: return (T) new SubCategoryBoImpl();
            case SUPPLIER: return (T) new SupplierBoImpl();
            case PRODUCT: return (T) new ProductBoImpl();
            case INVENTORY: return (T) new InventoryBoImpl();
        }
        return null;
    }
}
