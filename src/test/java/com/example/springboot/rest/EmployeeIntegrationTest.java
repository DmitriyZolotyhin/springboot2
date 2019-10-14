package com.example.springboot.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.example.springboot.rest.Application;
import com.example.springboot.rest.model.Employee;

	@TestPropertySource("/application.properties")
	@Sql(value = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@RunWith(SpringRunner.class)
	@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
	public class EmployeeIntegrationTest {
		@Autowired
		private TestRestTemplate restTemplate;

		@LocalServerPort
		private int port;

		private String getRootUrl() {
			return "http://localhost:" + port;
		}


		@Test
		public void testGetAllEmployees() {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);

			ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/app/employees",
					HttpMethod.GET, entity, String.class);

			assertNotNull(response.getBody());
			System.out.println(response.getBody());
		}

		@Test
		public void testGetEmployeeById() {
			Employee employee = restTemplate.getForObject(getRootUrl() + "app/employees/101", Employee.class);
			System.out.println(employee.getFirstName());
			assertNotNull(employee);
			System.out.println(employee);
		}

		@Test
		public void testCreateEmployee() {
			Employee employee = new Employee();
			employee.setEmailId("admin@gmail.com");
			employee.setFirstName("adminjj");
			employee.setLastName("admin");

			ResponseEntity<Employee> postResponse = restTemplate.postForEntity(getRootUrl() + "app/employees", employee, Employee.class);
			assertNotNull(postResponse);
			assertNotNull(postResponse.getBody());
			System.out.println(postResponse.getBody());
		}

		@Test
		public void testUpdateEmployee() {
			int id = 101;
			Employee employee = restTemplate.getForObject(getRootUrl() + "app/employees/" + id, Employee.class);
			employee.setFirstName("admin1");
			employee.setLastName("admin2");

			restTemplate.put(getRootUrl() + "app/employees/" + id, employee);

			Employee updatedEmployee = restTemplate.getForObject(getRootUrl() + "app/employees/" + id, Employee.class);
			assertNotNull(updatedEmployee);
			System.out.println(updatedEmployee);
		}

		@Test
		public void testDeleteEmployee() {
			int id = 103;
			Employee employee = restTemplate.getForObject(getRootUrl() + "app/employees/" + id, Employee.class);
			assertNotNull(employee);

			restTemplate.delete(getRootUrl() + "app/employees/" + id);


			try {
				employee = restTemplate.getForObject(getRootUrl() + "app/employees/" + id, Employee.class);
			} catch (final HttpClientErrorException e) {
				assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
			}
		}
	}
