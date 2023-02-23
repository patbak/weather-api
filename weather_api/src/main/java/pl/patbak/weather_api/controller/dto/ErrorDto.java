package pl.patbak.weather_api.controller.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {

    private String message;

    private String errorCode;

}
