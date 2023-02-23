package pl.patbak.weather_api.model;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
public class DailyWeatherResponse extends WeatherApiResponse{

    protected DailyPrecipitationSumSunriseSunset daily;

    protected Map<String, String> daily_units;

}
