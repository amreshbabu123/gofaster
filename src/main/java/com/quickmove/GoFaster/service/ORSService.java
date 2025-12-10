package com.quickmove.GoFaster.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.quickmove.GoFaster.dto.ORSDistanceResponse;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class ORSService {

    @Value("${ors.api.key}")
    private String orsApiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public ORSDistanceResponse getDistance(double sLat, double sLon, double dLat, double dLon) {
        try {
            String url = "https://api.openrouteservice.org/v2/directions/driving-car";

            String body = "{ \"coordinates\": [ [" + sLon + "," + sLat + "], [" + dLon + "," + dLat + "] ] }";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", orsApiKey);   // No Bearer
            headers.set("Content-Type", "application/json");

            HttpEntity<String> entity = new HttpEntity<>(body, headers);

            String response = restTemplate.postForObject(url, entity, String.class);

            JsonNode json = mapper.readTree(response);

            double meters = json.get("routes").get(0).get("summary").get("distance").asDouble();
            double seconds = json.get("routes").get(0).get("summary").get("duration").asDouble();

            return new ORSDistanceResponse(
                    meters / 1000,   // km
                    seconds / 3600   // hrs
            );

        } catch (Exception e) {
            System.err.println("ORS API Error: " + e.getMessage());
            return new ORSDistanceResponse(0, 0);
        }
    }
}
