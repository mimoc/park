package com.parks.parks;

import com.parks.parks.client.ParkClient;
import com.parks.parks.dto.Activity;
import com.parks.parks.dto.Park;
import com.parks.parks.entity.ParkEntity;
import com.parks.parks.exception.NotFoundException;
import com.parks.parks.repository.ParkRepository;
import com.parks.parks.service.ParkService;
import com.parks.parks.service.ParkServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.parks.parks.util.Const.*;


import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ParkServiceTest {
    @Mock
    private ParkRepository mockParkRepository;
    @Mock
    private ParkClient parkClient;

    @InjectMocks
    private  ParkService parkService = new ParkServiceImpl();

    private static ParkEntity parkEntity;
    private static Park park ;

    @BeforeAll
    public static void Init(){
        parkEntity = new ParkEntity(UUID.randomUUID(),"url","fullName","parkCode",new HashSet<>());
        park = new Park(UUID.randomUUID().toString(),"url","fullName","parkCode",new HashSet<>());
    }

    @Test
    public void testSaveReturnsParkCode(){
        String parkCode = "parkCode";
        Mockito.when(mockParkRepository.save(Mockito.any(ParkEntity.class))).thenReturn(parkEntity);
        park.setParkCode(parkCode);
        assertThat(parkCode,is(equalTo(parkService.save(park))));
    }

    @Test
    public void testWhenUpdateThenThrowsNotFoundException(){
        String parkCode = "parkCode";
        Mockito.when(mockParkRepository.findByParkCode(Mockito.any(String.class))).thenReturn(null);
        park.setParkCode(parkCode);
        assertThrows(NotFoundException.class,()->parkService.update(parkCode,park));
    }

    @Test
    public void testWhenUpdateThenModifiesTheFoundParkObject(){
        String fullName = "p1";
        String url = "url";
        String parkCode = "parkCode";
        park.setFullName(fullName);
        park.setUrl(url);
        ArgumentCaptor<ParkEntity> argumentCaptor = ArgumentCaptor.forClass(ParkEntity.class);
        Mockito.when(mockParkRepository.findByParkCode(Mockito.any(String.class))).thenReturn(parkEntity);
        Mockito.when(mockParkRepository.save(Mockito.any(ParkEntity.class))).thenReturn(parkEntity);
        parkService.update(parkCode,park);
        Mockito.verify(mockParkRepository).save(argumentCaptor.capture());

        assertThat(park.getFullName(), is(equalTo(argumentCaptor.getValue().getFullName())));
        assertThat(park.getUrl(), is(equalTo(argumentCaptor.getValue().getUrl())));

    }
    @Test
    public void testWhenFindAllParksThenMapsDataToPark(){
        String parkCode1 = "parkCode1";
        String url1 = "url1";
        String fullName1 = "fullName1";
        String uuid1 = UUID.randomUUID().toString();

        String parkCode2 = "parkCode2";
        String url2 = "url2";
        String fullName2 = "fullName2";
        String uuid2 = UUID.randomUUID().toString();

        String aUuid1 = UUID.randomUUID().toString();
        String aName1 = "activity1";

        String aUuid2 = UUID.randomUUID().toString();
        String aName2 = "activity2";


        List<Map> mapList = new ArrayList<>();
        Map<String,List<Map>> stringListMap = new HashMap<>();
        List<Map> mapList1 = new ArrayList<>();

        Map act1 = new HashMap();
        act1.put(ID,aUuid1);
        act1.put(NAME,aName1);

        Map park1 = new HashMap();
        park1.put(PARK_CODE,parkCode1);
        park1.put(FULL_NAME,fullName1);
        park1.put(URL,url1);
        park1.put(ID,uuid1);
        park1.put(ACTIVITIES,Arrays.asList(act1));

        Map act2 = new HashMap();
        act2.put(ID,aUuid2);
        act2.put(NAME,aName2);

        Map park2 = new HashMap();
        park2.put(PARK_CODE,parkCode2);
        park2.put(FULL_NAME,fullName2);
        park2.put(URL,url2);
        park2.put(ID,uuid2);
        park2.put(ACTIVITIES,Arrays.asList(act2));


        Activity activity1 = new Activity(aUuid1,aName1);
        Activity activity2 = new Activity(aUuid2,aName2);
        Set set1 = new HashSet();
        set1.add(activity1);
        Set set2 = new HashSet();
        set2.add(activity2);

        Park parkObj1 = new Park(uuid1,url1,fullName1,parkCode1, set1);
        Park parkObj2 = new Park(uuid2,url2,fullName2,parkCode2, set2);

        mapList1.add(0,park1);
        mapList1.add(1,park2);
        stringListMap.put("data",mapList1);
        mapList.add(0,stringListMap);

        Mockito.when(parkClient.callExternalURI(Mockito.any(Map.class))).thenReturn(mapList);
        List<Park> parkList = parkService.findAll(new HashMap());

        Optional optional1 = parkList.stream().filter(o->o.equals(parkObj1)).findAny();
        Optional optional2 = parkList.stream().filter(o->o.equals(parkObj2)).findAny();

        assertThat("true",optional1.isPresent());
        assertThat("true",optional2.isPresent());

    }

    @Test
    public void testWhenFindByCodeThenParkMapsToPark(){
        String parkCode1 = "parkCode1";
        String url1 = "url1";
        String fullName1 = "fullName1";
        String uuid1 = UUID.randomUUID().toString();

        String aUuid1 = UUID.randomUUID().toString();
        String aName1 = "activity1";


        List<Map> mapList = new ArrayList<>();
        Map<String,List<Map>> stringListMap = new HashMap<>();
        List<Map> mapList1 = new ArrayList<>();

        Map act1 = new HashMap();
        act1.put(ID,aUuid1);
        act1.put(NAME,aName1);

        Map park1 = new HashMap();
        park1.put(PARK_CODE,parkCode1);
        park1.put(FULL_NAME,fullName1);
        park1.put(URL,url1);
        park1.put(ID,uuid1);
        park1.put(ACTIVITIES,Arrays.asList(act1));


        Activity activity1 = new Activity(aUuid1,aName1);

        Set set1 = new HashSet();
        set1.add(activity1);

        Park parkObj1 = new Park(uuid1,url1,fullName1,parkCode1, set1);

        mapList1.add(0,park1);
        stringListMap.put("data",mapList1);
        mapList.add(0,stringListMap);

        Mockito.when(parkClient.callExternalURI(Mockito.any(Map.class))).thenReturn(mapList);
        List<Park> parkList = parkService.findAll(new HashMap());

        assertThat("true",parkList.get(0).equals(parkObj1));
    }
}
