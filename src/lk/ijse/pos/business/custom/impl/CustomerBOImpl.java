package lk.ijse.pos.business.custom.impl;

import lk.ijse.pos.business.custom.CustomerBO;
import lk.ijse.pos.dao.DaoFactory;
import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.entity.Customer;

import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO dao = DaoFactory.getInstance().getDao(DaoFactory.DaoType.CUSTOMER);
    @Override
    public boolean save(CustomerDTO dto) throws Exception {
        return dao.save(new Customer(
                dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getSalary()
        ));
    }

    @Override
    public boolean delete(String id) throws Exception {
        return dao.delete(id);
    }

    @Override
    public boolean update(CustomerDTO dto) throws Exception {
        return dao.update(new Customer(
                dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getSalary()
        ));
    }

    @Override
    public ArrayList<CustomerDTO> getAll() throws Exception {
        ArrayList<Customer> allCustomer = dao.getAll();
        ArrayList<CustomerDTO> dtoList = new ArrayList<>();
        for (Customer temp : allCustomer){
            dtoList.add(new CustomerDTO(
                    temp.getId(),
                    temp.getName(),
                    temp.getAddress(),
                    temp.getSalary()
            ));
        }
        return dtoList;
    }
}
