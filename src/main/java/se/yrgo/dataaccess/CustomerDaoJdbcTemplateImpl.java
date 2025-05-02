package se.yrgo.dataaccess;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import se.yrgo.domain.Call;
import se.yrgo.domain.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerDaoJdbcTemplateImpl implements CustomerDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void createTables() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS customer (id INT PRIMARY KEY, companyName VARCHAR(100), email VARCHAR(100))");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS call_record (id INT PRIMARY KEY, customer_id INT, notes VARCHAR(255))");
    }

    @Override
    public void create(Customer customer) {
        String sql = "INSERT INTO customer (id, companyName, email) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, customer.getId(), customer.getCompanyName(), customer.getEmail());
    }

    @Override
    public Customer getById(String customerId) throws RecordNotFoundException {
        try {
            String sql = "SELECT * FROM customer WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, customerRowMapper, Integer.parseInt(customerId));
        } catch (Exception e) {
            throw new RecordNotFoundException("Customer not found with ID: " + customerId);
        }
    }

    @Override
    public List<Customer> getByName(String name) {
        String sql = "SELECT * FROM customer WHERE companyName LIKE ?";
        return jdbcTemplate.query(sql, customerRowMapper, "%" + name + "%");
    }

    @Override
    public void update(Customer customerToUpdate) throws RecordNotFoundException {
        String sql = "UPDATE customer SET companyName = ?, email = ? WHERE id = ?";
        int updated = jdbcTemplate.update(sql, customerToUpdate.getCompanyName(), customerToUpdate.getEmail(), customerToUpdate.getId());
        if (updated == 0) {
            throw new RecordNotFoundException("No customer to update with ID: " + customerToUpdate.getId());
        }
    }

    @Override
    public void delete(Customer oldCustomer) throws RecordNotFoundException {
        String sql = "DELETE FROM customer WHERE id = ?";
        int deleted = jdbcTemplate.update(sql, oldCustomer.getId());
        if (deleted == 0) {
            throw new RecordNotFoundException("No customer to delete with ID: " + oldCustomer.getId());
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM customer";
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws RecordNotFoundException {
        Customer customer = getById(customerId);
        List<Call> calls = getCallsForCustomer(customerId);
        customer.setCalls(calls);
        return customer;
    }

    @Override
    public void addCall(Call newCall, String customerId) throws RecordNotFoundException {
        getById(customerId); // Ensure customer exists
        String sql = "INSERT INTO call_record (id, customer_id, notes) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, newCall.getTimeAndDate(), Integer.parseInt(customerId), newCall.getNotes());
    }

    @Override
    public Customer getCustomerById(int id) throws RecordNotFoundException {
        try {
            String sql = "SELECT * FROM customer WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, customerRowMapper, id);
        } catch (Exception e) {
            throw new RecordNotFoundException("Customer not found with ID: " + id);
        }
    }

    @Override
    public Customer findCustomerById(String customerId) throws RecordNotFoundException {
        return getById(customerId);
    }

    @Override
    public List<Customer> findCustomersByName(String name) {
        return getByName(name);
    }

    @Override
    public List<Call> getCallsForCustomer(String customerId) {
        String sql = "SELECT * FROM call_record WHERE customer_id = ?";
        return jdbcTemplate.query(sql, new RowMapper<Call>() {
            @Override
            public Call mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Call(rs.getInt("id"), rs.getString("notes"));
            }
        }, Integer.parseInt(customerId));
    }

    private final RowMapper<Customer> customerRowMapper = new RowMapper<>() {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Customer(rs.getInt("id"), rs.getString("companyName"), rs.getString("email"));
        }
    };
}
