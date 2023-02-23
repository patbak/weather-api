package pl.patbak.weather_api.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class InvalidCoordinatesException extends BaseException {

    public InvalidCoordinatesException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
