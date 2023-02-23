package pl.patbak.weather_api.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public abstract class WeatherApiResponse {

    protected double latitude;

    protected double longitude;

    protected double generationtime_ms;

    protected int utc_offset_seconds;

    protected String timezone;

    protected String timezone_abbreviation;

    protected double elevation;


}
