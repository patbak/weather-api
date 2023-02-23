package pl.patbak.weather_api.controller;

import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.patbak.weather_api.controller.dto.CoordinatesDto;
import pl.patbak.weather_api.controller.dto.DailyPrecipitationSunriseSunsetDto;
import pl.patbak.weather_api.service.WeatherService;

@RestController
@Log
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public DailyPrecipitationSunriseSunsetDto getWeather(@RequestBody CoordinatesDto cordinates) {
        return weatherService.getWeather(cordinates);
    }

}
