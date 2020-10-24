package com.vjava.micro.msscbrewery.web.controller.v2;

import com.vjava.micro.msscbrewery.model.v2.BeerDtoV2;
import com.vjava.micro.msscbrewery.service.v2.BeerServiceV2;
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
@RequestMapping("/api/v2/beer")
public class BeerControllerV2 {

    private final BeerServiceV2 beerService;

    public BeerControllerV2(BeerServiceV2 beerService) {
        this.beerService = beerService;
    }

    @GetMapping({"/{beerId}"})
    public ResponseEntity<BeerDtoV2> getBeer(@PathVariable("beerId") UUID beerId) {
        return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BeerDtoV2> handlePost(@Valid @RequestBody BeerDtoV2 beerDto){
        BeerDtoV2 savedBeerDto = beerService.saveBeer(beerDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "http://localhost:8080/api/v1/beer/"+savedBeerDto.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<BeerDtoV2> handleUpdate(@PathVariable("beerId") UUID beerId, @Valid @RequestBody BeerDtoV2 beerDto){
        BeerDtoV2 savedBeerDto = beerService.updateBeer(beerId, beerDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable("beerId") UUID beerId){
        beerService.deleteBeerById(beerId);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> validationErrorHandler(ConstraintViolationException constraintViolationException){
        List<String> errors = new ArrayList<>(constraintViolationException.getConstraintViolations().size());
        constraintViolationException.getConstraintViolations().
                forEach(constraintViolation -> errors.add(constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
