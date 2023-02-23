package pl.patbak.weather_api.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
public class DailyPrecipitationSumSunriseSunset {

    protected List<LocalDate> time;

    protected List<Double> precipitation_sum;

    protected List<LocalDateTime> sunrise;

    protected List<LocalDateTime> sunset;

}
