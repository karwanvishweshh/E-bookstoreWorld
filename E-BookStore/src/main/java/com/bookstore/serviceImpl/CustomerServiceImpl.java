package com.bookstore.serviceImpl;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.entities.Customer;
import com.bookstore.exceptions.CustomerIdNotFoundException;
import com.bookstore.repositories.CustomerRepository;
import com.bookstore.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Customer addCustomerOrUpdateCustomer(Customer customer) {

		if (customer.getId() == null) {

			customerRepository.save(customer);
		} else {

			Optional<Customer> getById = customerRepository.findById(customer.getId());

			if (getById.isPresent()) {
				Customer existCustmer = getById.get();
				existCustmer.setName(customer.getName());
				existCustmer.setEmail(customer.getEmail());
				return customerRepository.save(existCustmer);
			} else {

				throw new RuntimeException("Custmer Id not found");
			}
		}
		return customer;
	}

	@Override
	public Customer getByCustomerId(Long id) {

		Optional<Customer> byId = customerRepository.findById(id);

		if (!byId.isPresent()) {

			throw new CustomerIdNotFoundException("Custmer Id not found");
		}
		return byId.get();
	}

}

