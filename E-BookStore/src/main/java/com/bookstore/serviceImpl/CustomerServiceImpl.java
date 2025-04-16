package com.bookstore.serviceImpl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.entities.Customer;
import com.bookstore.exceptions.CustomerIdNotFoundException;
import com.bookstore.repositories.CustomerRepository;
import com.bookstore.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer addCustomerOrUpdateCustomer(Customer customer) {
        if (customer.getId() == null) {
            logger.info("Adding new customer: {}", customer.getName());
            Customer savedCustomer = customerRepository.save(customer);
            logger.info("Customer added with ID: {}", savedCustomer.getId());
            return savedCustomer;
        } else {
            logger.info("Updating customer with ID: {}", customer.getId());
            Optional<Customer> getById = customerRepository.findById(customer.getId());

            if (getById.isPresent()) {
                Customer existCustomer = getById.get();
                existCustomer.setName(customer.getName());
                existCustomer.setEmail(customer.getEmail());
                Customer updatedCustomer = customerRepository.save(existCustomer);
                logger.info("Customer updated successfully with ID: {}", updatedCustomer.getId());
                return updatedCustomer;
            } else {
                logger.warn("Customer ID not found: {}", customer.getId());
                throw new RuntimeException("Customer ID not found");
            }
        }
    }

    @Override
    public Customer getByCustomerId(Long id) {
        logger.info("Fetching customer by ID: {}", id);
        Optional<Customer> byId = customerRepository.findById(id);

        if (!byId.isPresent()) {
            logger.error("Customer ID not found: {}", id);
            throw new CustomerIdNotFoundException("Customer ID not found");
        }

        logger.info("Customer found: {}", byId.get().getName());
        return byId.get();
    }
}
