package com.customer.app.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.customer.app.dto.CustomerDTO;
import com.customer.app.entity.Customer;
import com.customer.app.mapper.CustomerMapper;
import com.customer.app.repository.CustomerRepository;
import com.customer.app.service.CustomerService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerMapper customerMapper;

	@Override
	public Mono<CustomerDTO> save(CustomerDTO customerDTO) {
		return customerRepository.save(customerMapper.mapToModel(customerDTO))
				.map(c -> customerMapper.mapToViewModel(c));
	}

	@Override
	public Flux<CustomerDTO> fetchAll() {
		return customerRepository.findAll().map(c -> customerMapper.mapToViewModel(c));
	}

	@Override
	public Mono<CustomerDTO> getByCustomerId(String id) {
		return Mono.just(customerMapper.mapToViewModel(customerRepository.findById(id)));
	}

	@Transactional
	@Override
	public Mono<CustomerDTO> update(String id, CustomerDTO customerDTO) {
		if (null != customerRepository.findById(id)) {
			Customer customer = customerMapper.mapToModel(customerDTO);
			customer.setId(id);
			return customerRepository.save(customer).map(c -> customerMapper.mapToViewModel(c));
		}
		return null;
	}

	@Transactional
	@Override
	public Mono<String> deleteByCustomerId(String id) {
		Customer customer = Optional.ofNullable(customerRepository.findById(id)).orElse(null);
		if (null != customer) {
			customerRepository.deleteById(id);
			return Mono.just("SUCCESS");
		}
		return null;
	}

}
