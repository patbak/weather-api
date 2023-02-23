package pl.patbak.weather_api.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Builder
@Data
public class DailyDto {

    protected Double precipitation_avg;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    protected LocalDateTime sunrise;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    protected LocalDateTime sunset;

}
