package pl.patbak.weather_api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.patbak.weather_api.controller.dto.CoordinatesDto;
import pl.patbak.weather_api.exception.InvalidCoordinatesException;
import pl.patbak.weather_api.repository.RequestRepository;
import pl.patbak.weather_api.service.WeatherService;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThrows;

@SpringBootTest
@Testcontainers
public class WeatherApiIntegrationTest {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private RequestRepository requestRepository;

    @Test
    public void shouldSaveRequestAndReturnResponse() {
        CoordinatesDto coordinatesDto = new CoordinatesDto(1.52, 22);
        var response = weatherService.getWeather(coordinatesDto);
        assertThat(response, hasProperty("dailyInfo"));
        assertThat(response.getDailyInfo().size(), is(7));
        var requests = requestRepository.findAll();
        assertThat(requests.size(), greaterThanOrEqualTo(1));
        assertThat(requests.get(0).getLatitude(), equalTo(coordinatesDto.getLatitude()));
        assertThat(requests.get(0).getLongitude(), equalTo(coordinatesDto.getLongitude()));
    }

    private static Stream<Arguments> provideCoordinates() {
        return Stream.of(
                Arguments.of(new CoordinatesDto(1.52, 200)),
                Arguments.of(new CoordinatesDto(1.52, -200)),
                Arguments.of(new CoordinatesDto(150, 100)),
                Arguments.of(new CoordinatesDto(-150, 100))
        );
    }

    @ParameterizedTest
    @MethodSource("provideCoordinates")
    public void validateParamsTest(CoordinatesDto coordinatesDto) {
        assertThrows(InvalidCoordinatesException.class, () -> weatherService.validateParams(coordinatesDto));
    }
}
