package com.parks.parks.service;

import com.parks.parks.client.ParkClient;
import com.parks.parks.dto.Park;
import com.parks.parks.entity.ActivityEntity;
import com.parks.parks.entity.ParkEntity;
import com.parks.parks.exception.NotFoundException;
import com.parks.parks.repository.ParkRepository;
import com.parks.parks.util.EntityConvertor;
import com.parks.parks.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.parks.parks.util.Const.PARK_CODE;

@Component
public class ParkServiceImpl implements ParkService {

    @Autowired
    ParkRepository parkRepository;

    @Autowired
    ParkClient parkClient;

    @Override
    public List<Park> findAll(final Map multiValueMap) {
        List<Map> mapList = parkClient.callExternalURI(multiValueMap);
        List<Park> parkList =  extractParkData(mapList);
        return parkList;
    }

    @Override
    public Park findByCode(final String parkCode) {
        MultiValueMap pathParams = new LinkedMultiValueMap();
        pathParams.put(PARK_CODE, Collections.singletonList(parkCode));
        List<Map> mapList = parkClient.callExternalURI(pathParams);
        return extractParkData(mapList).get(0);
    }

    @Override
    public Park update(String parkCode,final Park park) {
        ParkEntity parkEntity = parkRepository.findByParkCode(parkCode);
        if (parkEntity == null) throw new NotFoundException("Resource with park code " + park.getParkCode() + " was not found");

        parkEntity.setFullName(park.getFullName());
        parkEntity.setParkCode(park.getParkCode());
        parkEntity.setUrl(park.getUrl());
        Set activities = park.getActivities().stream().map(i->{
            ActivityEntity activityEntity = EntityConvertor.activityDTOToEntity(i);
            activityEntity.setPark(parkEntity);
            return activityEntity;
        }).collect(Collectors.toSet());
        parkEntity.setActivities(activities);

        return EntityConvertor.entityToParkDTO(parkRepository.save(parkEntity));
    }

    public String save(final Park park){
        ParkEntity parkEntity = parkRepository.save(EntityConvertor.parkDTOToEntity(park));
        return parkEntity.getParkCode();
    }

    private List<Park> extractParkData(final List<Map> mapList){
        List data = (List) mapList.get(0).get("data");
        List<Park> parkList = (List<Park>) data.stream().map(m-> MapperUtil.convertToParkDTO((Map) m)).collect(Collectors.toList());
        return parkList;
    }

}
