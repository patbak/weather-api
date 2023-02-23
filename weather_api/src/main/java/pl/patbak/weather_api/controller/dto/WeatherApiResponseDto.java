package pl.patbak.weather_api.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class WeatherApiResponseDto {

    protected double latitude;

    protected double longitude;
}
