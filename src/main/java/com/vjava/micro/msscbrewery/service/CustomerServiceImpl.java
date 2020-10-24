package com.vjava.micro.msscbrewery.service;

import com.vjava.micro.msscbrewery.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerDto getCustomerById(UUID customerId) {
        return CustomerDto.builder()
                .id(customerId)
                .name("Robert")
                .build();
    }

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        return CustomerDto.builder()
                .id(UUID.randomUUID())
                .name(customerDto.getName())
                .build();
    }

    @Override
    public CustomerDto updateCustomer(UUID customerId, CustomerDto customerDto) {
        return getCustomerById(customerId);
    }

    @Override
    public void deleteCustomerById(UUID customerId) {
        log.debug("Deleting a beer...");
    }
}
