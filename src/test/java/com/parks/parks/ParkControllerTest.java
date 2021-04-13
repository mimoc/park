package com.parks.parks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parks.parks.controller.ParkController;
import com.parks.parks.dto.Park;
import com.parks.parks.exception.NotFoundException;
import com.parks.parks.exception.handler.CustomizedResponseEntityExceptionHandler;
import com.parks.parks.repository.ParkRepository;
import com.parks.parks.service.ParkService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class ParkControllerTest {

    @Mock
    private ParkService mockParkService;

    @InjectMocks
    private static ParkController mockParkController;

    private JacksonTester<Park> jsonConverter;
    private static MockMvc mvc;
    private static Park park ;


    @BeforeEach
    void setUp(){
        mvc = MockMvcBuilders.standaloneSetup(mockParkController)
                .setControllerAdvice(new CustomizedResponseEntityExceptionHandler())
                .build();
        park =  new Park(UUID.randomUUID().toString(),"url","fullName","parkCode",new HashSet<>());
        JacksonTester.initFields(this, new ObjectMapper());
    }


    @Test
    void testWhenParkIsCreatedReturnUriOfRepresentationInHeaderLocation() throws Exception {
        String parkCode = "parkCode";
        Mockito.when(mockParkService.save(Mockito.any(Park.class))).thenReturn(parkCode);

        ResponseEntity responseEntity = mockParkController.createPark(new Park());

        URI userRepresentationUri = responseEntity.getHeaders().getLocation();
        assertThat(userRepresentationUri, is(equalTo(new URI("/parks/"+parkCode))));
    }

    @Test
    void testWhenPostIsSuccessfulCreatedStatusIsReturned() throws Exception {
        String parkCode = "parkCode";

        String json = String.valueOf(jsonConverter.write(park).getJson());
        Mockito.when(mockParkService.save(Mockito.any(Park.class))).thenReturn(parkCode);
        MockHttpServletResponse response = mvc.perform(
                post("/parks/")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus(),is(equalTo(HttpStatus.CREATED.value())));
    }

    @Test
    void testWhenPutDoesNotFindResourceNotFoundIsReturned() throws Exception {
        String parkCode = "parkCode";
        String json = String.valueOf(jsonConverter.write(park).getJson());
        Mockito.when(mockParkService.update(Mockito.any(String.class),Mockito.any(Park.class))).thenThrow(new NotFoundException("Not found"));
        MockHttpServletResponse response = mvc.perform(
                put("/parks/"+parkCode)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus(),is(equalTo(HttpStatus.NOT_FOUND.value())));
    }

    @Test
    void testWhenFindByCodeThenOkStatusIsReturned() throws Exception {
        String parkCode = "parkCode";
        String json = String.valueOf(jsonConverter.write(park).getJson());
        Mockito.when(mockParkService.findByCode(Mockito.any(String.class))).thenReturn(park);
        MockHttpServletResponse response = mvc.perform(
                get("/parks/"+parkCode)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus(),is(equalTo(HttpStatus.OK.value())));
    }

    @Test
    void testWhenFindAllThenOKStatusIsReturned() throws Exception {
        ArrayList arrayList = new ArrayList();
        arrayList.add(park);
        String json = String.valueOf(jsonConverter.write(park).getJson());
        Mockito.when(mockParkService.findAll(Mockito.any(Map.class))).thenReturn(arrayList);
        MockHttpServletResponse response = mvc.perform(
                get("/parks/")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus(),is(equalTo(HttpStatus.OK.value())));
    }

    @Test
    void testWhenPostHasTooShortParkCodeThenBadRequestIsReturned() throws Exception {
        park.setParkCode("ss");
        String json = String.valueOf(jsonConverter.write(park).getJson());
        MockHttpServletResponse response = mvc.perform(
                post("/parks/")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus(),is(equalTo(HttpStatus.BAD_REQUEST.value())));
    }

    @Test
    void testWhenPostHasInvalidFullNameThenBadRequestIsReturned() throws Exception {
        park.setFullName(null);
        String json = String.valueOf(jsonConverter.write(park).getJson());
        MockHttpServletResponse response = mvc.perform(
                post("/parks/")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus(),is(equalTo(HttpStatus.BAD_REQUEST.value())));
    }

    @Test
    void testWhenPutHasInvalidFullNameThenBadRequestIsReturned() throws Exception {
        String parkCode = "parkCode";
        park.setFullName(null);
        String json = String.valueOf(jsonConverter.write(park).getJson());
        MockHttpServletResponse response = mvc.perform(
                put("/parks/"+parkCode)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus(),is(equalTo(HttpStatus.BAD_REQUEST.value())));
    }

}
