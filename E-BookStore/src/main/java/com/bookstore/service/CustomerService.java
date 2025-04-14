package com.bookstore.service;

import com.bookstore.entities.Customer;

public interface CustomerService {
	
	public Customer addCustomerOrUpdateCustomer(Customer customer);

	public Customer getByCustomerId(Long id);


}
