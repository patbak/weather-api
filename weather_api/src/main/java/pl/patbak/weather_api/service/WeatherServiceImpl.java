package pl.patbak.weather_api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.http.HttpStatus;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import pl.patbak.weather_api.controller.dto.*;
import pl.patbak.weather_api.exception.ErrorCode;
import pl.patbak.weather_api.exception.InvalidCoordinatesException;
import pl.patbak.weather_api.exception.UnsupportedResponseException;
import pl.patbak.weather_api.model.DailyWeatherResponse;
import pl.patbak.weather_api.model.dao.Request;
import pl.patbak.weather_api.repository.RequestRepository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static pl.patbak.weather_api.StringUtils.formatMessage;


@Service
@AllArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final HttpClient httpClient;
    private final URIBuilder uriBuilder;
    private final ObjectMapper objectMapper;
    private final ConversionService conversionService;
    private final RequestRepository requestRepository;

    @Override
    public DailyPrecipitationSunriseSunsetDto getWeather(CoordinatesDto coordinatesDto) {
        try {
            validateParams(coordinatesDto);
            saveRequest(coordinatesDto);
            HttpResponse<String> response = getData(coordinatesDto);
            if (response.statusCode() == HttpStatus.SC_OK) {
                var dailyWeatherApiResponse = objectMapper.readValue(response.body(), DailyWeatherResponse.class);
                return conversionService.convert(dailyWeatherApiResponse, DailyPrecipitationSunriseSunsetDto.class);
            } else {
                throw new UnsupportedResponseException("Weather server returned unsupported response", ErrorCode.UNSUPPORTED_RESPONSE);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveRequest(CoordinatesDto coordinatesDto) {
        Request request = Request.builder()
                .latitude(coordinatesDto.getLatitude())
                .longitude(coordinatesDto.getLongitude())
                .dateTime(LocalDateTime.now())
                .build();
        requestRepository.saveAndFlush(request);
    }

    private HttpResponse<String> getData(CoordinatesDto coordinatesDto) throws URISyntaxException, IOException, InterruptedException {
        LocalDate date = LocalDate.now();
        var uri = uriBuilder
                .setScheme("https")
                .setHost("api.open-meteo.com/v1")
                .setPath("/forecast")
                .setParameter("latitude", String.valueOf(coordinatesDto.getLatitude()))
                .setParameter("longitude", String.valueOf(coordinatesDto.getLongitude()))
                .addParameter("daily", "precipitation_sum")
                .addParameter("daily", "sunrise")
                .addParameter("daily", "sunset")
                .setParameter("timezone", "GMT")
                .setParameter("start_date", date.minusDays(7l).toString())
                .setParameter("end_date", date.toString())
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public void validateParams(CoordinatesDto coordinatesDto) {
        if (coordinatesDto.getLongitude() > 180 || coordinatesDto.getLongitude() < -180 || coordinatesDto.getLatitude() > 90 || coordinatesDto.getLatitude() < -90)
            throw new InvalidCoordinatesException(
                    formatMessage("Latitude must be in range of -90 to 90°. Given: {}; Longitude must be in range of -180 to 180°. Given: {}", coordinatesDto.getLatitude(), coordinatesDto.getLongitude()),
                    ErrorCode.INVALID_COORDINATES);
    }

}
