package com.customer.app;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.Base64;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.customer.app.config.security.webflux.JWTUtil;
import com.customer.app.controller.CustomerController;
import com.customer.app.dto.CustomerDTO;
import com.customer.app.model.Role;
import com.customer.app.model.User;
import com.customer.app.service.impl.CustomerServiceImpl;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(CustomerController.class)
public class CustomerControllerTest {

    private final String ADMIN_USER = "admin";

    @Autowired
    private WebTestClient webClient;
    
    @Value("${springbootwebfluxjjwt.jjwt.secret}")
    private String secret;

    @Value("${springbootwebfluxjjwt.jjwt.expiration}")
    private String expirationTime;

    @MockBean
    private CustomerServiceImpl customerService;
    //@Before, @BeforeAll can be added to implement common logic 
    
    @Test
    @WithMockUser(username = ADMIN_USER, password = ADMIN_USER)
    public void authFailure() {
    	// GIVEN
    	CustomerDTO dto = getCustomerDTO();
    	// THEN
        webClient.post().uri("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(dto), CustomerDTO.class)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    @WithMockUser(username = ADMIN_USER, password = ADMIN_USER)
    public void persistCustomer() {
    	// GIVEN
    	CustomerDTO dto = getCustomerDTO();
    	String token = generateToken();
    	
    	// THEN
        webClient.post().uri("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer "+token)
                .body(Mono.just(dto), CustomerDTO.class)
                .exchange()
                .expectStatus().isOk();
    }

	private String generateToken() {
		User user = new User();
    	user.setUsername(ADMIN_USER);
    	user.setPassword(ADMIN_USER);
    	user.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
    	JWTUtil jwtUtil = new JWTUtil();
    	jwtUtil.setSecret(secret);
    	jwtUtil.setExpirationTime(expirationTime);
    	jwtUtil.setEncodedString(Base64.getEncoder().encodeToString(secret.getBytes()));
    	String token = jwtUtil.generateToken(user);
		return token;
	}
	
	private CustomerDTO getCustomerDTO() {
		CustomerDTO dto = new CustomerDTO();
    	dto.setId("CUST1");
    	dto.setName("abc");
    	dto.setEmail("abc@email.com");
    	dto.setPhone("1234567890");
		return dto;
	}

}
