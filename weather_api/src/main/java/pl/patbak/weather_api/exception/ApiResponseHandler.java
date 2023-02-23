package pl.patbak.weather_api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.patbak.weather_api.controller.dto.ErrorDto;

import java.net.URISyntaxException;
import java.util.List;


@Slf4j
@RestControllerAdvice
public class ApiResponseHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidCoordinatesException.class)
    public ResponseEntity<List<ErrorDto>> handleInvalidCoordinates(BaseException ex) {
        log.error("InvalidCoordinates :: {}", ex);
        return createErrorResponseEntity(ex.getErrorCode().getValue(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(URISyntaxException.class)
    public ResponseEntity<List<ErrorDto>> handleURISyntaxException(URISyntaxException ex) {
        log.error("URISyntaxException :: {}", ex);
        return createErrorResponseEntity(ErrorCode.URI_SYNTAX_INCORRECT.getValue(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnsupportedResponseException.class)
    public ResponseEntity<List<ErrorDto>> handleUnsupportedResponseException(BaseException ex) {
        log.error("UnsupportedResponseException :: {}", ex);
        return createErrorResponseEntity(ex.getErrorCode().getValue(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<List<ErrorDto>> createErrorResponseEntity(String code, String message, HttpStatus status) {
        ErrorDto errorDto = ErrorDto.builder()
                .errorCode(code)
                .message(message)
                .build();
        return ResponseEntity.status(status).body(List.of(errorDto));
    }
}
