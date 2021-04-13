package com.parks.parks.util;

import com.parks.parks.dto.Activity;
import com.parks.parks.dto.Park;
import com.parks.parks.entity.ActivityEntity;
import com.parks.parks.entity.ParkEntity;


public class EntityConvertor {

    public static ParkEntity parkDTOToEntity(Park park){
            ParkEntity parkEntity = new ParkEntity();
            parkEntity.setParkCode(park.getParkCode());
            parkEntity.setFullName(park.getFullName());
            parkEntity.setUrl(park.getUrl());
            park.getActivities().stream().forEach(i-> {
                ActivityEntity activityEntity = activityDTOToEntity(i);
                activityEntity.setPark(parkEntity);
                parkEntity.getActivities().add(activityEntity);

            });
            return parkEntity;
    }
    public static Park entityToParkDTO(ParkEntity parkEntity){
        Park park = new Park();
        park.setParkCode(parkEntity.getParkCode());
        park.setFullName(parkEntity.getFullName());
        park.setId(String.valueOf(parkEntity.getId()));
        park.setUrl(parkEntity.getUrl());
        parkEntity.getActivities().stream().forEach(i-> park.getActivities().add(activityEntityToDTO(i)));
        return park;
    }

    public static Activity activityEntityToDTO(ActivityEntity activityEntity){
        Activity activity = new Activity();
        activity.setId(String.valueOf(activityEntity.getId()));
        activity.setName(activityEntity.getName());
        return activity;
    }

    public static ActivityEntity activityDTOToEntity(Activity activity){
        ActivityEntity activityEntity = new ActivityEntity();
        //activityEntity.setId(UUID.fromString(activityDTO.getId()));
        activityEntity.setName(activity.getName());
        return activityEntity;
    }
}
