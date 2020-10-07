package com.vjava.micro.msscbrewery.service;

import com.vjava.micro.msscbrewery.model.BeerDto;
import com.vjava.micro.msscbrewery.model.CustomerDto;

import java.util.UUID;

public interface CustomerService {
    CustomerDto getCustomerById(UUID customerId);

    CustomerDto saveCustomer(CustomerDto customerDto);

    CustomerDto updateCustomer(UUID customerId, CustomerDto customerDto);

    void deleteCustomerById(UUID customerId);
}
