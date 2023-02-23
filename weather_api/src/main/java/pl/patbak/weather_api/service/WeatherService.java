package pl.patbak.weather_api.service;

import pl.patbak.weather_api.controller.dto.CoordinatesDto;
import pl.patbak.weather_api.controller.dto.DailyPrecipitationSunriseSunsetDto;

public interface WeatherService {

    DailyPrecipitationSunriseSunsetDto getWeather(CoordinatesDto coordinatesDto);


}
