package com.assignment.cards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.assignment.cards.dto.EmployeePostsResponse;

import com.assignment.cards.dto.EmployeeRequest;
import com.assignment.cards.dto.EmployeeResponse;
import com.assignment.cards.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee API", description = "CRUD Operations for Employee")
public class EmployeeController {
	
	
	private static final Logger LOGGER =
	        LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Create Employee")
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(
            @Valid @RequestBody EmployeeRequest request) {
    	
    	LOGGER.info("Request: createEmployee");

        EmployeeResponse response = employeeService.createEmployee(request);
        
        LOGGER.info("Response: Employee created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Employee By ID")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
    	
    	LOGGER.info("Request: getEmployeeById..");

        EmployeeResponse response = employeeService.getEmployeeById(id);
        LOGGER.info("Response: Employee id details returned successfully");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get All Employees with Pagination and Sorting")
    @GetMapping
    public ResponseEntity<Page<EmployeeResponse>> getAllEmployees(
    		
    		

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {
    	
    	LOGGER.info("Request: getAllEmployees..");

        Page<EmployeeResponse> response =
                employeeService.getAllEmployees(page, size, sortBy, direction);
        
        LOGGER.info("Response: getAllEmployees returned successfully");

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update Employee")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {
    	
    	
    	LOGGER.info("Request: updateEmployee..");

        EmployeeResponse response =
                employeeService.updateEmployee(id, request);
        
        LOGGER.info("Response: updateEmployee by id  successfully");

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete Employee")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
    	
    	LOGGER.info("Request: deleteEmployee..");

        employeeService.deleteEmployee(id);
        
        LOGGER.info("Response: deleteEmployee by id  successfully");

        return ResponseEntity.ok("Employee deleted successfully.");
    }
    
    
    
    @Operation(summary = "Get Employee with Third Party Posts")
    @GetMapping("/{id}/posts")
    public ResponseEntity<EmployeePostsResponse> getEmployeePosts(@PathVariable Long id) {
    	
    	LOGGER.info("getEmployeePosts..");

        return ResponseEntity.ok(employeeService.getEmployeePosts(id));
    }
    
    

}