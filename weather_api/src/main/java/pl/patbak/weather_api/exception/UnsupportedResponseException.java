package pl.patbak.weather_api.exception;

public class UnsupportedResponseException extends BaseException{

    public UnsupportedResponseException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
