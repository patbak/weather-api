package pl.patbak.weather_api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyPrecipitationSunriseSunsetDto extends WeatherApiResponseDto {

    private Map<LocalDate, DailyDto> dailyInfo;

    private Map<String, String> daily_units;


}
