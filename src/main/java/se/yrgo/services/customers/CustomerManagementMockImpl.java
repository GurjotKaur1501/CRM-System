package se.yrgo.services.customers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import se.yrgo.domain.Call;
import se.yrgo.domain.Customer;
import se.yrgo.domain.Action;

public class CustomerManagementMockImpl implements CustomerManagementService {
	private Map<String,Customer> customer = new HashMap<>();
	private Map<String, List<Call>> calls = new HashMap<>();


	@Override
	public void newCustomer(Customer customer) {
		customer.put(customer.getCompanyName(), customer);
		calls.put(customer.getCompanyName(), new ArrayList<>());
	}

	@Override
	public void updateCustomer(Customer customer) {
		customer.put(customer.getCompanyName(), customer);
	}

	@Override
	public void deleteCustomer(Customer Customer) {
		customer.remove(customer.getClass());
		calls.remove(customer.getClass());
	}

	@Override
	public Customer findCustomerById(String customerId) throws CustomerNotFoundException {
		return customer.get(customerId);
	}

	@Override
	public List<Customer> findCustomersByName(String name) {
		List<Customer> results = new ArrayList<>();
		for (Customer customer : customer.values()) {
			if (customer.getCompanyName().contains(name)) {
				results.add(customer);
			}
		}
		return results;
	}

	@Override
	public List<Customer> getAllCustomers() {
		return new ArrayList<>(customer.values());
	}

	@Override
	public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {
		return customer.get(customerId);
	}

	@Override
	public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
		List<Call> customerCalls = calls.get(customerId);
		if (customerCalls != null) {
			customerCalls.add(callDetails);
		}
	}
	}
	
