package se.yrgo.services.customers;

import se.yrgo.dataaccess.CustomerDao;
import se.yrgo.domain.Customer;
import se.yrgo.dataaccess.RecordNotFoundException;

public abstract class CustomerManagementServiceProductionImpl implements CustomerManagementService {

    private CustomerDao customerDao;

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public Customer getCustomerById(int id) throws CustomerNotFoundException {
        return customerDao.getCustomerById(id);
    }
}
