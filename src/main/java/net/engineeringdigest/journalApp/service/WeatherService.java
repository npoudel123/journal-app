package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.config.WeatherApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@Service
@Slf4j
public class WeatherService {
//    private static final String apiKey = "d736b058c9b57b323ae7b856860a4566";
//    private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    private RestTemplate restTemplate;
    private WeatherApiProperties weatherApiProperties;
    private AppCache appCache;
    @Autowired
    private RedisService redisService;

    public WeatherService(RestTemplate restTemplate, WeatherApiProperties weatherApiProperties,
                          AppCache appCache){
        this.restTemplate = restTemplate;
        this.weatherApiProperties = weatherApiProperties;
        this.appCache = appCache;
    }

    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse = redisService.get(city, WeatherResponse.class);
        if(weatherResponse != null){
            return weatherResponse;
        }
        UriComponentsBuilder url = UriComponentsBuilder
//                .fromHttpUrl(weatherApiProperties.getUrl()) //Now getting from database
//                .queryParam("access_key", weatherApiProperties.getKey())//Now getting from database
                .fromHttpUrl(appCache.APP_CACHE.get("weather_api"))
                .queryParam("access_key",appCache.APP_CACHE.get("access_key"))
                .queryParam("query", city);

        //Add Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Client-App", "JournalApp");//Custom Header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<WeatherResponse> responseEntity = restTemplate.exchange(url.toUriString(), HttpMethod.GET, entity, WeatherResponse.class);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                log.error("Weather API returned status: {}", responseEntity.getStatusCode());
                throw new RuntimeException("Weather API call failed");
            }
            WeatherResponse body = responseEntity.getBody();
            if(body != null){
                redisService.set(city, body, 300L);
            }
            return body;
        } catch (RestClientException ex) {
            log.error("Error calling Weather API", ex);
            throw new RuntimeException("External Weather Service unavailable");
        }
    }
}
