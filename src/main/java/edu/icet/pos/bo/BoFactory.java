package edu.icet.pos.bo;

import edu.icet.pos.bo.custom.impl.UserBoImpl;
import edu.icet.pos.bo.custom.impl.UserRoleBoImpl;
import edu.icet.pos.util.BoType;

public class BoFactory {
    private BoFactory(){}

    public static <T extends SuperBo> T getBo(BoType boType){
        switch (boType){
            case USER_ROLE: return (T) new UserRoleBoImpl();
            case USER: return (T) new UserBoImpl();
        }
        return null;
    }
}
