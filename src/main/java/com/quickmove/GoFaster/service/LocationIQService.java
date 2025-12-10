package com.quickmove.GoFaster.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class LocationIQService {

    @Value("${locationiq.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    // Get coordinates for a given city
    public double[] getCoordinates(String city) {
        try {
            String q = URLEncoder.encode(city, StandardCharsets.UTF_8);

            String url = "https://us1.locationiq.com/v1/search?key=" + apiKey +
                    "&q=" + q + "&format=json&limit=1";

            JsonNode node = mapper.readTree(restTemplate.getForObject(url, String.class)).get(0);

            double lat = node.get("lat").asDouble();
            double lon = node.get("lon").asDouble();

            return new double[]{lat, lon};

        } catch (Exception e) {
            System.err.println("Coordinates Error: " + e.getMessage());
            return new double[]{0, 0};
        }
    }

    // Reverse geocoding
    public String getAddressFromCoordinates(double lat, double lon) {
        try {
            String url =
                    "https://us1.locationiq.com/v1/reverse?key=" + apiKey +
                    "&lat=" + lat + "&lon=" + lon + "&format=json";

            JsonNode response = mapper.readTree(restTemplate.getForObject(url, String.class));

            return response.get("display_name").asText();

        } catch (Exception e) {
            System.err.println("Reverse Geo Error: " + e.getMessage());
            return "Unknown Location";
        }
    }
    
}
