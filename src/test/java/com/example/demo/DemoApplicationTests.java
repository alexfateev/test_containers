package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest
class DemoApplicationTests {

	private static final GenericContainer<?> myApp1 = new GenericContainer<>("devapp:latest")
			.withExposedPorts(8080);
	private static final GenericContainer<?> myApp2 = new GenericContainer<>("prodapp:latest")
			.withExposedPorts(8081);

	@Autowired
	TestRestTemplate restTemplate;

	@BeforeAll
	public static void setUp() {
		myApp1.start();
		myApp2.start();
	}

	@Test
	void contextLoads() {
		ResponseEntity<String> forEntityApp1 = restTemplate.getForEntity("http://localhost:" + myApp1.getMappedPort(8080), String.class);
		ResponseEntity<String> forEntityApp2 = restTemplate.getForEntity("http://localhost:" + myApp2.getMappedPort(8081), String.class);
		Assertions.assertEquals("Hello from container", forEntityApp1.getBody());
		Assertions.assertEquals("Hello from container", forEntityApp2.getBody());
	}

}
