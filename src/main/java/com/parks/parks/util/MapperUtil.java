package com.parks.parks.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.parks.parks.dto.Activity;
import com.parks.parks.dto.Park;

import static com.parks.parks.util.Const.*;

public class MapperUtil {

    public static Park convertToParkDTO(Map parametersMap){
        String id = (String) parametersMap.get(ID);
        String url = (String) parametersMap.get(URL);
        String fullName = (String) parametersMap.get(FULL_NAME);
        String parkCode = (String) parametersMap.get(PARK_CODE);
        List<Map> activitiesMap = (List) parametersMap.get(ACTIVITIES);
        Set<Activity> activityList = activitiesMap.stream().map(m->convertToActivityDTO((Map) m)).collect(Collectors.toSet());

        return new Park(id,url,fullName,parkCode, activityList);

    }

    public static Activity convertToActivityDTO(Map parametersMap){
        String id = (String) parametersMap.get(ID);
        String name = (String) parametersMap.get(NAME);
        return new Activity(id,name);
    }

}
