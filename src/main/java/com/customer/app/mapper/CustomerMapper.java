package com.customer.app.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.customer.app.dto.CustomerDTO;
import com.customer.app.entity.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

	Customer mapToModel(CustomerDTO customerDTO);
	
	CustomerDTO mapToViewModel(Customer customer);
	
	List<CustomerDTO> mapListToViewModel(List<Customer> findAll);
	
}
