package com.parks.parks.repository;

import com.parks.parks.entity.ParkEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ParkRepository extends CrudRepository<ParkEntity,UUID>{

    @Query("select p from ParkEntity p where p.parkCode=:parkCode")
    ParkEntity findByParkCode(@Param("parkCode") String parkCode);
}