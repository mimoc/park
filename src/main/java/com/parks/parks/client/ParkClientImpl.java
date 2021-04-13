package com.parks.parks.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class ParkClientImpl implements ParkClient{

    @Autowired
    WebClient webClient;

    @Override
    public List<Map> callExternalURI(Map multiValueMap){
        return webClient.get().uri(uriBuilder ->
                uriBuilder.path("/parks/")
                        .queryParams((MultiValueMap<String, String>) multiValueMap)
                        .queryParam("api_key","HhUfqXjpmNIPihnQ5vU2jt8UWpyfAiLYfFrqsE2G")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToFlux(Map.class).collectList().block();
    }
}
