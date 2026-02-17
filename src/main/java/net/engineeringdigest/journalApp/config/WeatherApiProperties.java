package net.engineeringdigest.journalApp.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "weather.api")
@Setter
@Getter
public class WeatherApiProperties {
    private String url;
    private String key;
    private int timeOut;
}
