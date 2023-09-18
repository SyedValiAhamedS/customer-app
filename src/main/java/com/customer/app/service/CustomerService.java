package com.customer.app.service;

import com.customer.app.dto.CustomerDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CustomerService {

	Mono<CustomerDTO> save(CustomerDTO productDTO);

	Flux<CustomerDTO> fetchAll();

	Mono<CustomerDTO> getByCustomerId(String id);

	Mono<CustomerDTO> update(String id, CustomerDTO customerDTO);

	Mono<String> deleteByCustomerId(String id);

}
