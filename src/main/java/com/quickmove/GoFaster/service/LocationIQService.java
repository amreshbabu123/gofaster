package com.quickmove.GoFaster.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class LocationIQService {

    @Value("${locationiq.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    // ------------------------ DISTANCE CALCULATION ------------------------
    public double getDistance(String source, String destination) {
        try {
            String src = URLEncoder.encode(source, StandardCharsets.UTF_8);
            String dest = URLEncoder.encode(destination, StandardCharsets.UTF_8);

            // SOURCE COORDINATES
            String sUrl = "https://us1.locationiq.com/v1/search?key=" + apiKey +
                    "&q=" + src + "&format=json";

            JsonNode sNode = mapper.readTree(restTemplate.getForObject(sUrl, String.class)).get(0);
            double sLat = sNode.get("lat").asDouble();
            double sLon = sNode.get("lon").asDouble();

            // DESTINATION COORDINATES
            String dUrl = "https://us1.locationiq.com/v1/search?key=" + apiKey +
                    "&q=" + dest + "&format=json";

            JsonNode dNode = mapper.readTree(restTemplate.getForObject(dUrl, String.class)).get(0);
            double dLat = dNode.get("lat").asDouble();
            double dLon = dNode.get("lon").asDouble();

            // DRIVING DISTANCE
            String disUrl = "https://us1.locationiq.com/v1/directions/driving/" +
                    sLon + "," + sLat + ";" + dLon + "," + dLat +
                    "?key=" + apiKey;

            JsonNode result = mapper.readTree(restTemplate.getForObject(disUrl, String.class));

            double meters = result.get("routes").get(0).get("distance").asDouble();
            return meters / 1000; // KM

        } catch (Exception e) {
            System.err.println("Distance API Error: " + e.getMessage());
            return 0;
        }
    }

    // ------------------------ REVERSE GEOCODING ------------------------
    public String getAddressFromCoordinates(double lat, double lon) {
        try {
            String url = "https://us1.locationiq.com/v1/reverse?key=" + apiKey +
                    "&lat=" + lat + "&lon=" + lon + "&format=json";

            JsonNode response = mapper.readTree(restTemplate.getForObject(url, String.class));
            return response.get("display_name").asText();

        } catch (Exception e) {
            System.err.println("Reverse Geo Error: " + e.getMessage());
            return "Unknown Location";
        }
    }
}
