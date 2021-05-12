package lk.ijse.pos.business.custom.impl;

import lk.ijse.pos.business.custom.ItemBO;
import lk.ijse.pos.dao.DaoFactory;
import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.entity.Item;

import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {

    ItemDAO itemDAO = DaoFactory.getInstance().getDao(DaoFactory.DaoType.ITEM);
    @Override
    public boolean save(ItemDTO dto) throws Exception {
        return itemDAO.save(new Item(
                dto.getCode(),
                dto.getDescription(),
                dto.getUnitPrice(),
                dto.getQtyOnHand()
        ));
    }

    @Override
    public boolean delete(String id) throws Exception {
        return itemDAO.delete(id);
    }

    @Override
    public boolean update(ItemDTO dto) throws Exception {
        Item itmUpdate = new Item(dto.getCode(),dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand());
        return itemDAO.update(itmUpdate);
    }

    @Override
    public ArrayList<ItemDTO> getAll() throws Exception {
        ArrayList<Item> itemList = itemDAO.getAll();
        ArrayList<ItemDTO> itemDtoList = new ArrayList<>();
        for (Item itmTemp : itemList){
            itemDtoList.add(new ItemDTO(
                    itmTemp.getCode(),
                    itmTemp.getDescription(),
                    itmTemp.getUnitPrice(),
                    itmTemp.getQtyOnHand()
            ));
        }
        return itemDtoList;
    }
}
