package sg.edu.nus.iss.D21.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Customer {

    // Defining customer's members
    private Integer id;
    private String lastName;
    private String firstName;
    private String company;
    private String mobilePhone;

    // Generate getter & setter
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getMobilePhone() {
        return mobilePhone;
    }
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    // Create customer object from Sql row query result
    public static Customer create(SqlRowSet srs) {
        Customer customer = new Customer();
        customer.setId(srs.getInt("id"));
        customer.setLastName(srs.getString("last_name"));
        customer.setFirstName(srs.getString("first_name"));
        customer.setCompany(srs.getString("company"));
        return customer;
    }

    // Convert to JSON from customer object
    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("last_name", lastName)
                .add("first_name", firstName)
                .add("company", company)
                .build();
    }
}
