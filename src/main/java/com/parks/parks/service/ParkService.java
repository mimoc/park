package com.parks.parks.service;

import com.parks.parks.dto.Park;

import java.util.List;
import java.util.Map;

public interface ParkService {
    List<Park> findAll(Map valuesMap);
    Park findByCode(String parkCode);
    Park update(String parkCode, Park park);
    String save(Park park);
}
