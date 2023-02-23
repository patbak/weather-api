package pl.patbak.weather_api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.patbak.weather_api.converter.DailyResponseToDailyPrecipitationSunriseSunsetDtoConverter;

import java.net.http.HttpClient;

@Configuration
public class Config implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DailyResponseToDailyPrecipitationSunriseSunsetDtoConverter());
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }

    @Bean
    @Scope("prototype")
    public URIBuilder uriBuilder(){
        return new URIBuilder();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }
}
