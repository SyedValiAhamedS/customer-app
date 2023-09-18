package com.customer.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.app.dto.CustomerDTO;
import com.customer.app.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Save customer details", description = "Returns Saved customer details")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Successfully saved customer detail"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
			@ApiResponse(responseCode = "400", description = "Invalid request parameters"),
			@ApiResponse(responseCode = "403", description = "Forbidden due to access privilege"),
			@ApiResponse(responseCode = "500", description = "Error occurred.") })
	@PostMapping(value = "/customers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<CustomerDTO>> saveProducts(@RequestBody CustomerDTO productDTO) {
		return customerService.save(productDTO).map(dto -> new ResponseEntity<>(dto, HttpStatus.CREATED))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
	}

	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@Operation(summary = "Retrieve all customer details", description = "Returns customer details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved customer details"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
			@ApiResponse(responseCode = "403", description = "Forbidden due to access privilege"),
			@ApiResponse(responseCode = "404", description = "Customer details not found") })
	@GetMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<ResponseEntity<CustomerDTO>> fetchAll() {
		return customerService.fetchAll().map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@Operation(summary = "Get a customer by id", description = "Returns a customer as per the id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
			@ApiResponse(responseCode = "403", description = "Forbidden due to access privilege"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
			@ApiResponse(responseCode = "404", description = "Not found - The customer was not found") })
	@GetMapping(value = "/customers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<CustomerDTO>> getByCustomerId(@PathVariable String id) {

		return customerService.getByCustomerId(id).map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Update customer details using customer id", description = "Returns Updated customer details using customer id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully updated customer details"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
			@ApiResponse(responseCode = "403", description = "Forbidden due to access privilege"),
			@ApiResponse(responseCode = "404", description = "Customer details not found") })
	@PutMapping(value = "/customers/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<CustomerDTO>> update(@PathVariable String id, @RequestBody CustomerDTO customerDTO) {

		return customerService.update(id, customerDTO).map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Delete an employee by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Deleted an employee", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "401", description = "Unauthorized user", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden due to access privilege"),
			@ApiResponse(responseCode = "404", description = "Employee not found", content = @Content),
			@ApiResponse(responseCode = "400", description = "Invalid employee id", content = @Content) })
	@DeleteMapping(value = "/customers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<String>> deleteByCustomerId(@PathVariable String id) {

		return customerService.deleteByCustomerId(id).map(msg -> new ResponseEntity<>(msg, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NO_CONTENT));
	}

}
