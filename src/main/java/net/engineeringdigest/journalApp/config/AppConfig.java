package net.engineeringdigest.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(WeatherApiProperties weatherApiProperties){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(weatherApiProperties.getTimeOut());
        factory.setReadTimeout(weatherApiProperties.getTimeOut());
        return new RestTemplate(factory);
    }
}
