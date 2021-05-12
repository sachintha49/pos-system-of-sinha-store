package lk.ijse.pos.dao;

import lk.ijse.pos.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.pos.dao.custom.impl.ItemDAOImpl;
import lk.ijse.pos.dao.custom.impl.PlaceOrderDAOImpl;

public class DaoFactory { //Compose design pattern
    // Singleton
    // Factory method pattern
    private static DaoFactory daoFactory;

    private DaoFactory(){

    }

    public enum DaoType{ // enum also like a java class we can keep constant (Final variable)
        CUSTOMER,ORDER,ITEM,ORDER_DETAIL
    }

    public static DaoFactory getInstance(){
        return daoFactory == null ? (daoFactory = new DaoFactory()) : (daoFactory);
    }

    public <T> T getDao(DaoType type){
        switch (type){
            case CUSTOMER:
                return (T) new CustomerDAOImpl();
            case ITEM:
                return (T) new ItemDAOImpl();
            case ORDER:
                return (T) new PlaceOrderDAOImpl();
            case ORDER_DETAIL:
                return null;
            default:
                return null;
        }
    }
}
