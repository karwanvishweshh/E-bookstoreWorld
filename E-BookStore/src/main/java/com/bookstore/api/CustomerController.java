package com.bookstore.api;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.entities.Customer;
import com.bookstore.model.ResponseMessage;
import com.bookstore.repositories.CustomerRepository;
import com.bookstore.service.CustomerService;


@RestController
@RequestMapping("/rest")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/createUpdateCust")
    public ResponseEntity<ResponseMessage> createOrUpdateCustomer(@RequestBody Customer customer) {
        logger.info("Creating or updating customer: {}", customer);

        Customer savedCustomer = customerService.addCustomerOrUpdateCustomer(customer);

        String message = (customer.getId() == null) ? "Customer created successfully" : "Customer updated successfully";
        logger.info("Customer {} successfully", (customer.getId() == null) ? "created" : "updated");

        return ResponseEntity.ok(new ResponseMessage(HttpStatus.OK.value(), "success", message, savedCustomer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getCustomerById(@PathVariable Long id) {
        logger.info("Fetching customer by ID: {}", id);

        Customer customer = customerService.getByCustomerId(id);
        if (customer == null) {
            logger.warn("No customer found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage(HttpStatus.NOT_FOUND.value(), "error", "Customer not found", null));
        }

        logger.info("Customer found: {}", customer);
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.OK.value(), "success", "Customer found", customer));
    }

    @GetMapping("/AllCustomer")
    public ResponseEntity<ResponseMessage> getAllCustomers() {
        logger.info("Fetching all customers");

        List<Customer> customers = customerRepository.findAll();
        logger.info("Fetched {} customers", customers.size());

        return ResponseEntity.ok(new ResponseMessage(HttpStatus.OK.value(), "success", "All customers fetched successfully", null, customers));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteCustomer(@PathVariable Long id) {
        logger.info("Deleting customer with ID: {}", id);

        customerRepository.deleteById(id);
        logger.info("Customer deleted with ID: {}", id);

        return ResponseEntity.ok(new ResponseMessage(HttpStatus.OK.value(), "success", "Customer deleted with ID: " + id));
    }
}
