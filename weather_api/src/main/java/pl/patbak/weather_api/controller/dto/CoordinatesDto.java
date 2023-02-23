package pl.patbak.weather_api.controller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@RequiredArgsConstructor
@Getter
@Setter
public class CoordinatesDto {
    private double latitude;
    private double longitude;

}
