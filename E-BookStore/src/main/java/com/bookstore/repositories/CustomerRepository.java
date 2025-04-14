package com.bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
