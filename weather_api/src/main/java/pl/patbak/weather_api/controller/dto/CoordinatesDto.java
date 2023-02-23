package pl.patbak.weather_api.controller.dto;

import lombok.*;


@AllArgsConstructor
@Getter
@Setter
public class CoordinatesDto {
    private double latitude;
    private double longitude;

}
