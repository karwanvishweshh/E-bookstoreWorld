package com.bookstore.api;


import java.util.List;

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

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    
    @PostMapping("/createUpdateCust")
    public ResponseEntity<ResponseMessage> createOrUpdateCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.addCustomerOrUpdateCustomer(customer);
        String message = (customer.getId() == null) ? "Customer created successfully" : "Customer updated successfully";
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.OK.value(),"success",message,savedCustomer));
    }

   
    @GetMapping("/{CustomerByid}")
    public ResponseEntity<ResponseMessage> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getByCustomerId(id);
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.OK.value(),"success","Customer found",customer
        ));
    }

    
    @GetMapping("/AllCustomer")
    public ResponseEntity<ResponseMessage> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.OK.value(),"success","All customers fetched successfully",null,customers
        ));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.OK.value(),"success","Customer deleted with ID: " + id
        ));
    }
}