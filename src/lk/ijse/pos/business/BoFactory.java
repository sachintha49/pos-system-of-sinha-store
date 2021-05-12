package lk.ijse.pos.business;

import lk.ijse.pos.business.custom.PlaceOrderBO;
import lk.ijse.pos.business.custom.impl.CustomerBOImpl;
import lk.ijse.pos.business.custom.impl.ItemBOImpl;
import lk.ijse.pos.business.custom.impl.PlaceOrderBOImpl;
import lk.ijse.pos.dao.custom.impl.PlaceOrderDAOImpl;

public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory(){}

    public enum BoType{
        CUSTOMER,ITEM,ORDER,ORDER_DETAIL
    }
    public static BoFactory getInstance(){
        return (boFactory == null) ? (boFactory = new BoFactory()) : (boFactory);
    }

    public <T> T getBo(BoType type){
        switch (type){
            case CUSTOMER:
                return (T) new CustomerBOImpl();
            case ITEM:
                return (T) new ItemBOImpl();
            case ORDER_DETAIL:
                return null;
            case ORDER:
                return (T) new PlaceOrderBOImpl();
                default:
                    return null;
        }
    }
}
