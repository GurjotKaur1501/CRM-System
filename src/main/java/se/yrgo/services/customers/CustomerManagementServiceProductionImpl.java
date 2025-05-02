package se.yrgo.services.customers;

import se.yrgo.dataaccess.CustomerDao;
import se.yrgo.domain.Call;
import se.yrgo.domain.Customer;
import se.yrgo.services.customers.CustomerNotFoundException;
import se.yrgo.dataaccess.RecordNotFoundException;

import java.util.List;

public class CustomerManagementServiceProductionImpl implements CustomerManagementService {

    private CustomerDao customerDao;

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public Customer getCustomerById(int id) throws CustomerNotFoundException, RecordNotFoundException {
        return customerDao.getCustomerById(id);
    }

    @Override
    public void newCustomer(Customer newCustomer) {
        customerDao.create(newCustomer);
    }

    @Override
    public void updateCustomer(Customer changedCustomer) throws RecordNotFoundException {
        customerDao.update(changedCustomer);
    }

    @Override
    public void deleteCustomer(Customer oldCustomer) throws RecordNotFoundException {
        customerDao.delete(oldCustomer);
    }

    @Override
    public Customer findCustomerById(String customerId) throws CustomerNotFoundException, RecordNotFoundException {
        return customerDao.findCustomerById(customerId);
    }

    @Override
    public List<Customer> findCustomersByName(String name) {
        return customerDao.findCustomersByName(name);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException, RecordNotFoundException {
        Customer customer = customerDao.findCustomerById(customerId);
        List<Call> calls = customerDao.getCallsForCustomer(customerId);
        customer.setCalls(calls);
        return customer;
    }

    @Override
    public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
        try {
            customerDao.addCall(callDetails, customerId);
        } catch (RecordNotFoundException e) {
            throw new CustomerNotFoundException("Cannot record call: Customer not found with ID: " + customerId, e);
        }
    }
}
