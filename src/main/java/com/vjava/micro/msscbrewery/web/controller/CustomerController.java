package com.vjava.micro.msscbrewery.web.controller;

import com.vjava.micro.msscbrewery.model.CustomerDto;
import com.vjava.micro.msscbrewery.service.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping({"/{customerId}"})
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerId") UUID customerId) {
        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDto> handlePost(@Valid @RequestBody CustomerDto customerDto){
        CustomerDto savedCustomerDto = customerService.saveCustomer(customerDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "http://localhost:8080/api/v1/customer/"+savedCustomerDto.getId().toString());
        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(savedCustomerDto);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDto> handleUpdate(@PathVariable("customerId") UUID customerId, @Valid @RequestBody CustomerDto customerDto){
        CustomerDto savedCustomerDto = customerService.updateCustomer(customerId, customerDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("customerId") UUID customerId){
        customerService.deleteCustomerById(customerId);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> validationErrorHandler(ConstraintViolationException constraintViolationException){
        List<String> errors = new ArrayList<>(constraintViolationException.getConstraintViolations().size());
        constraintViolationException.getConstraintViolations().
                forEach(constraintViolation -> errors.add(constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
