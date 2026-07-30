package com.assignment.cards.serviceimpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.cards.dto.EmployeeRequest;
import com.assignment.cards.dto.EmployeeResponse;
import com.assignment.cards.entity.Employee;
import com.assignment.cards.exception.ResourceNotFoundException;
import com.assignment.cards.repository.EmployeeRepository;
import com.assignment.cards.service.EmployeeService;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.assignment.cards.dto.EmployeePostsResponse;
import com.assignment.cards.external.JsonPlaceholderPost;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;

	private final RestTemplate restTemplate;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository, RestTemplate restTemplate) {

		this.employeeRepository = employeeRepository;
		this.restTemplate = restTemplate;
	}

	@Override
	@Transactional(readOnly = true)
	public EmployeePostsResponse getEmployeePosts(Long id) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id : " + id));

		EmployeeResponse employeeResponse = convertToResponse(employee);

		String url = "https://jsonplaceholder.typicode.com/posts?userId=" + id;

		JsonPlaceholderPost[] response = restTemplate.getForObject(url, JsonPlaceholderPost[].class);

		List<JsonPlaceholderPost> posts = Arrays.asList(response);

		EmployeePostsResponse result = new EmployeePostsResponse();
		result.setEmployee(employeeResponse);
		result.setPosts(posts);

		return result;
	}

	@Override
	public EmployeeResponse createEmployee(EmployeeRequest request) {

		Employee employee = new Employee();

		employee.setFirstName(request.getFirstName());
		employee.setLastName(request.getLastName());
		employee.setEmail(request.getEmail());
		employee.setDepartment(request.getDepartment());
		employee.setSalary(request.getSalary());

		Employee savedEmployee = employeeRepository.save(employee);

		return convertToResponse(savedEmployee);
	}

	@Override
	public EmployeeResponse getEmployeeById(Long id) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id : " + id));

		return convertToResponse(employee);
	}

	@Override
	public Page<EmployeeResponse> getAllEmployees(int page, int size, String sortBy, String direction) {

		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(page, size, sort);

		return employeeRepository.findAll(pageable).map(this::convertToResponse);
	}

	@Override
	public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id : " + id));

		employee.setFirstName(request.getFirstName());
		employee.setLastName(request.getLastName());
		employee.setEmail(request.getEmail());
		employee.setDepartment(request.getDepartment());
		employee.setSalary(request.getSalary());

		Employee updatedEmployee = employeeRepository.save(employee);

		return convertToResponse(updatedEmployee);
	}

	@Override
	public void deleteEmployee(Long id) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id : " + id));

		employeeRepository.delete(employee);
	}

	private EmployeeResponse convertToResponse(Employee employee) {

		EmployeeResponse response = new EmployeeResponse();

		response.setId(employee.getId());
		response.setFirstName(employee.getFirstName());
		response.setLastName(employee.getLastName());
		response.setEmail(employee.getEmail());
		response.setDepartment(employee.getDepartment());
		response.setSalary(employee.getSalary());

		return response;
	}

}