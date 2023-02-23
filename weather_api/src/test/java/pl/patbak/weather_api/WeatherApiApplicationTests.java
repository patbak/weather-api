package pl.patbak.weather_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.patbak.weather_api.model.DailyWeatherResponse;


import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@Log
@SpringBootTest
class WeatherApiApplicationTests {

    @Autowired
    HttpClient httpClient;
    @Autowired
    URIBuilder uriBuilder;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void contextLoads() {
    }


    @Test
    void testConnection() throws URISyntaxException, IOException, InterruptedException {
        uriBuilder
                .setScheme("https")
                .setHost("api.open-meteo.com/v1")
                .setPath("/forecast")
                .setParameter("hourly", "temperature_2m")
                .setParameter("latitude", "52.52")
                .setParameter("longitude", "13.41")
                .setParameter("start_date", "2023-02-15")
                .setParameter("end_date", "2023-02-22");
        log.info(uriBuilder.build().toString());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uriBuilder.build())
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        log.info("Response :: " + response.body());
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.body(), notNullValue());
    }

    @Test
    void testObjectMapping() throws URISyntaxException, IOException, InterruptedException {
        uriBuilder
                .setScheme("https")
                .setHost("api.open-meteo.com/v1")
                .setPath("/forecast")
                .addParameter("daily", "precipitation_sum")
                .addParameter("daily", "sunrise")
                .addParameter("daily", "sunset")
                .setParameter("timezone", "GMT")
                .setParameter("latitude", "52.52")
                .setParameter("longitude", "13.41")
                .setParameter("start_date", "2023-02-16")
                .setParameter("end_date", "2023-02-22");
        log.info("URI :: " + uriBuilder.build());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uriBuilder.build())
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        var xd = objectMapper.readValue(response.body(), DailyWeatherResponse.class);
        log.info("Api response :: " + xd);

        assertThat(xd.getLatitude(), is(equalTo(52.52)));
        assertThat(xd.getLongitude(), is(closeTo(13.41, 0.01)));
        assertThat(xd.getDaily().getPrecipitation_sum(), is(iterableWithSize(7)));
        assertThat(xd.getDaily().getSunset(), is(iterableWithSize(7)));
        assertThat(xd.getDaily().getSunrise(), is(iterableWithSize(7)));
        assertThat(xd.getDaily().getTime(), is(iterableWithSize(7)));
    }


}
