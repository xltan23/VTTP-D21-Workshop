package sg.edu.nus.iss.D21.repositories;

public class Queries {

    // SQL Query for retrieving customers with specified range
    public final static String SQL_SELECT_ALL_CUSTOMERS = "SELECT id, last_name, first_name, company from customers limit ? offset ?";

    // SQL Query for retrieving specific customer by ID
    public final static String SQL_SELECT_CUSTOMER_BY_ID = "SELECT id, last_name, first_name, company from customers where id = ?";

    // SQL Query for retrieving customer's orders by customer's ID
    // c.id as customer_id creates customer_id field
    // DATE_FORMAT converts SQL DateTime format to dd/MM/yyyy style (To be reconverted back to SQL DateTime format in JsonObject)
    public final static String SQL_SELECT_CUSTOMER_ORDERS_BY_ID = "SELECT c.id as customer_id, c.last_name, c.first_name, c.company, o.id as order_id, DATE_FORMAT(o.order_date, \"%d/%m/%y\") as order_date, DATE_FORMAT(o.shipped_date, \"%d/%m/%y\") as shipped_date, o.ship_name from customers c, orders o where c.id = o.customer_id and o.customer_id = ?";
}
