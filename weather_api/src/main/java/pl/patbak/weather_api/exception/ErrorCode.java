package pl.patbak.weather_api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_COORDINATES("Invalid coordinates"),
    UNSUPPORTED_RESPONSE("Unsupported response"),
    URI_SYNTAX_INCORRECT("URI syntax incorrect");


    String value;
}
