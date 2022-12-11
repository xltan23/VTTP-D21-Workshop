package sg.edu.nus.iss.D21.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.D21.models.Customer;
import sg.edu.nus.iss.D21.models.CustomerRowMapper;
import sg.edu.nus.iss.D21.models.Order;

import static sg.edu.nus.iss.D21.repositories.Queries.*;

@Repository
public class CustomerRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Retrieving list of customers
    public List<Customer> getAllCustomers(Integer limit, Integer offset) {
        List<Customer> customerList = new LinkedList<>();
        if (limit == null) {
            limit = 5;
        }
        if (offset == null) {
            offset = 0;
        }
        final SqlRowSet srs = jdbcTemplate.queryForRowSet(SQL_SELECT_ALL_CUSTOMERS, limit, offset);
        while(srs.next()) {
            customerList.add(Customer.create(srs));
        }
        return customerList;
    }

    // Retrieving specific customer
    public Customer getCustomerById(Integer id) {
        List<Customer> customerList = jdbcTemplate.query(SQL_SELECT_CUSTOMER_BY_ID, new CustomerRowMapper(), new Object[] {id});
        return customerList.get(0);
    }

    // Retrieving specific customer orders
    public List<Order> getCustomerOrder(Integer id) {
        final List<Order> orderList = new LinkedList<>();
        final SqlRowSet srs = jdbcTemplate.queryForRowSet(SQL_SELECT_CUSTOMER_ORDERS_BY_ID, id);
        while(srs.next()) {
            orderList.add(Order.create(srs));
        }
        return orderList;
    }
}
