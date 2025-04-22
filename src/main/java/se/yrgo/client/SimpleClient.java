package se.yrgo.client;

import se.yrgo.services.customers.CustomerManagementService;
import se.yrgo.services.calls.CallHandlingService;
import se.yrgo.domain.Customer;
import se.yrgo.domain.Call;
import se.yrgo.domain.Action;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.time.LocalDateTime;
import java.util.List;

public class SimpleClient {
    public static void main(String[] args) {
        // Create Spring container
        ApplicationContext container =
                new ClassPathXmlApplicationContext("application.xml");

        // Get the service beans
        CustomerManagementService customerService =
                container.getBean("customerManagementService", CustomerManagementService.class);

        CallHandlingService callService =
                container.getBean("callHandlingService", CallHandlingService.class);

        // Test customer service
        customerService.newCustomer(new Customer("123", "HB", "Jim", "jim@hb.com", "123456789"));

        // Test call handling
        Call call = new Call("Jim called about the new version", LocalDateTime.now());
        Action action = new Action("Contact Jim to confirm new version is ready",
                LocalDateTime.now().plusDays(30));

        callService.recordCall("123", call, action);

        // Print results
        System.out.println("All Customers:");
        List<Customer> customers = customerService.getAllCustomers();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }
}