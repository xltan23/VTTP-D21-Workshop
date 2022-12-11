package sg.edu.nus.iss.D21.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.D21.models.Customer;
import sg.edu.nus.iss.D21.models.Order;
import sg.edu.nus.iss.D21.repositories.CustomerRepository;

@RestController
@RequestMapping(path = "/api/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerRestController {

    @Autowired
    private CustomerRepository customerRepo;

    // localhost:8080/api/customer
    @GetMapping
    public ResponseEntity<String> getAllCustomers(@RequestParam(required = false) Integer limit, Integer offset) {
        List<Customer> customerList = customerRepo.getAllCustomers(limit, offset);
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (Customer customer : customerList) {
            jab.add(customer.toJSON());
        }
        JsonArray ja = jab.build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ja.toString());
    }

    // localhost:8080/api/customer/{customerId}
    @GetMapping(path = "{customerId}")
    public ResponseEntity<String> getCustomerById(@PathVariable Integer customerId) {
        JsonObject jo = null;
        try {
            Customer customer = customerRepo.getCustomerById(customerId);
            jo = Json.createObjectBuilder()
                        .add("customer", customer.toJSON())
                        .build();
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{Error: Record not found}");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jo.toString());
    }

    // localhost:8080/api/customer/{customerId}/orders
    @GetMapping(path = "{customerId}/orders")
    public ResponseEntity<String> getCustomerOrders(@PathVariable Integer customerId) {
        JsonArray ja = null;
        try {
            List<Order> orderList = customerRepo.getCustomerOrder(customerId);
            JsonArrayBuilder jab = Json.createArrayBuilder();
            for (Order order : orderList) {
                jab.add(order.toJSON());
            }
            ja = jab.build();
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Error: Record not found");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ja.toString());

    }
}
