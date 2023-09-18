package com.customer.app.repository;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;

import com.customer.app.entity.Customer;

@Repository
public interface CustomerRepository extends R2dbcRepository<Customer, Long> {
	
	@Query("SELECT c FROM Customer c where c.id = ?1")
	Customer findById(String id);
	
	@Query("DELETE FROM Customer c where c.id = ?1")
	void deleteById(String id);

}
