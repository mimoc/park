package com.parks.parks.controller;

import com.parks.parks.dto.Park;
import com.parks.parks.service.ParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static com.parks.parks.util.Const.*;

@RestController
@RequestMapping(value = "/parks")
@Validated
public class ParkController {

    @Autowired
    ParkService parkService;

    @GetMapping("/{parkCode}")
    public ResponseEntity<Park> getParkByCode(@PathVariable(name="parkCode")
                                                  @NotBlank
                                                  @Size(min=4, max=10,message="Park code must have between 4 and 10 characters") String parkCode) {

        return ResponseEntity.ok(parkService.findByCode(parkCode));
    }

    @GetMapping
    public ResponseEntity<List<Park>> getAllParks(@RequestParam(name = STATE_CODE, required = false)  String stateCode,
                                                    @RequestParam(name = LIMIT, required = false,defaultValue = "10")  Integer limit,
                                                    @RequestParam(name = START,required = false,defaultValue = "1")  Integer start) {

        MultiValueMap queryParams = new LinkedMultiValueMap();
        queryParams.put(STATE_CODE, Collections.singletonList(stateCode));
        queryParams.put(LIMIT, Collections.singletonList(String.valueOf(limit)));
        queryParams.put(START, Collections.singletonList(String.valueOf(start)));

        return ResponseEntity.ok(parkService.findAll(queryParams));
    }

    @PostMapping(consumes = { "application/json" },produces = { "application/json" })
    public ResponseEntity createPark(@RequestBody @Valid final Park park) throws URISyntaxException {
         String parkCode = parkService.save(park);

        return ResponseEntity.created(new URI("/parks/"+parkCode)).build();
    }

    @PutMapping(value = "/{parkCode}",consumes = { "application/json" },produces = { "application/json" })
    public ResponseEntity updatePark(@RequestBody @Valid final Park park,
                                     @PathVariable(name="parkCode") @NotBlank
                                     @Size(min=4, max=10,message="Park code must have between 4 and 10 characters") String parkCode) {

        return ResponseEntity.ok(parkService.update(parkCode,park));
    }

}

