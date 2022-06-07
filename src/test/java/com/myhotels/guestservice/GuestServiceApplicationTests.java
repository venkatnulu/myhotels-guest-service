package com.myhotels.guestservice;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.myhotels.guestservice.repository.TestH2Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class GuestServiceApplicationTests {

	private static String baseUrl = "";
	@LocalServerPort
	private Integer port;
	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private TestH2Repository testH2Repository;

	@BeforeEach
	public void init() {
		baseUrl = "http://localhost:".concat(String.valueOf(port)).concat("/api/v1/guestservice");
	}

	@Test
	void contextLoads() {
	}

}
