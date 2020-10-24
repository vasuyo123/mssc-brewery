package com.vjava.micro.msscbrewery.service;

import com.vjava.micro.msscbrewery.model.BeerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class BeerServiceImpl implements BeerService {
    @Override
    public BeerDto getBeerById(UUID beerId) {
        return BeerDto.builder()
                .id(beerId)
                .beerName("Kingfisher")
                .beerStyle("Lager")
                .upc(200020L)
                .build();
    }

    @Override
    public BeerDto saveBeer(BeerDto beerDto) {
        return BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName(beerDto.getBeerName())
                .build();
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {

        return getBeerById(beerId);
    }

    @Override
    public void deleteBeerById(UUID beerId) {
        log.debug("Deleting a beer...");
    }
}
