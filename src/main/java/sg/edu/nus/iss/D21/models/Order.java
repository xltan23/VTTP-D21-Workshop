package sg.edu.nus.iss.D21.models;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Order {
    
    // Defining customer's order
    private Integer id;
    private Customer customer;
    private DateTime orderDate;
    private DateTime shippedDate;
    private String shipName;

    // Generate getter and setter
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public DateTime getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(DateTime orderDate) {
        this.orderDate = orderDate;
    }
    public DateTime getShippedDate() {
        return shippedDate;
    }
    public void setShippedDate(DateTime shippedDate) {
        this.shippedDate = shippedDate;
    }
    public String getShipName() {
        return shipName;
    }
    public void setShipName(String shipName) {
        this.shipName = shipName;
    }
    
    // Creating Order object from SQL row result
    public static Order create(SqlRowSet srs) {
        Customer customer = new Customer();
        Order order = new Order();
        // Define customer
        customer.setId(srs.getInt("customer_id"));
        customer.setLastName(srs.getString("last_name"));
        customer.setFirstName(srs.getString("first_name"));
        customer.setCompany(srs.getString("company"));
        customer.setMobilePhone(srs.getString("mobile_phone"));
        // Define order
        order.setId(srs.getInt("id"));
        order.setCustomer(customer);
        // Convert SQL DateTime (yyyy-MM-dd 00:00:00) into dd/MM/yyyy
        order.setOrderDate(new DateTime(DateTimeFormat.forPattern("dd/MM/yyyy")
                                                        .parseDateTime(srs.getString("order_date"))));
        
        if (srs.getString("shipped_date") != null) {
            order.setShippedDate(new DateTime(DateTimeFormat.forPattern("dd/MM/yyyy")
                                                            .parseDateTime(srs.getString("shipped_date"))));
        }
        order.setShipName(srs.getString("ship_name"));
        return order;
    }
    
    // Creating Json Object from Order object
    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("customer_id", id)
                .add("order_id", id)
                .add("order_date", orderDate != null ? orderDate.toString() : "")
                .add("shipped_date", shippedDate != null ? shippedDate.toString() : "")
                .add("ship_name", shipName)
                .build();
    }
}
