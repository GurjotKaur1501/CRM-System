package se.yrgo.client;

import se.yrgo.services.*;
import se.yrgo.domain.Customer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.yrgo.services.customers.CustomerManagementService;

import java.util.*;

public class SimpleClient {

    public static void main(String[] args) {
        // Create Spring container
        ApplicationContext container =
                new ClassPathXmlApplicationContext("application.xml");

        // Get the service bean
        CustomerManagementService service =
                container.getBean("customerManagementService", CustomerManagementService.class);

        // Test the service
        service.newCustomer(new Customer("123", "HB", "Jim", "jim@hb.com", "123456789"));

        // Get and print all customers
        List<Customer> customers = service.getAllCustomers();
        System.out.println("All Customers:");
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }
}