package pl.patbak.weather_api.converter;

import org.springframework.core.convert.converter.Converter;
import pl.patbak.weather_api.controller.dto.DailyDto;
import pl.patbak.weather_api.controller.dto.DailyPrecipitationSunriseSunsetDto;

import pl.patbak.weather_api.model.DailyWeatherResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DailyWeatherResponseToDailyPrecipitationSunriseSunsetDtoConverter implements Converter<DailyWeatherResponse, DailyPrecipitationSunriseSunsetDto> {

    @Override
    public DailyPrecipitationSunriseSunsetDto convert(DailyWeatherResponse source) {
        DailyPrecipitationSunriseSunsetDto dailyPrecipitationSunriseSunsetDto = new DailyPrecipitationSunriseSunsetDto();
        var sourceDaily = source.getDaily();
        var averagePrecipitation = countAveragePrecipitation(sourceDaily.getPrecipitation_sum());
        var size = sourceDaily.getTime().size();
        Map<LocalDate, DailyDto> dailyInfo = new HashMap<>();
        for (int i = 0; i < size; i++) {
            var daily = DailyDto.builder()
                    .sunrise(sourceDaily.getSunrise().get(i))
                    .sunset(sourceDaily.getSunset().get(i))
                    .precipitation_avg(averagePrecipitation.get(i))
                    .build();
            dailyInfo.put(sourceDaily.getTime().get(i), daily);
        }
        dailyPrecipitationSunriseSunsetDto.setDailyInfo(dailyInfo);
        dailyPrecipitationSunriseSunsetDto.setLatitude(source.getLatitude());
        dailyPrecipitationSunriseSunsetDto.setLongitude(source.getLongitude());
        dailyPrecipitationSunriseSunsetDto.setDaily_units(source.getDaily_units());
        return dailyPrecipitationSunriseSunsetDto;
    }

    private List<Double> countAveragePrecipitation(List<Double> precipitationSumList) {
        return precipitationSumList.stream()
                .map(aDouble -> aDouble / 24)
                .map(aDouble -> {
                    BigDecimal bd = new BigDecimal(Double.toString(aDouble));
                    bd = bd.setScale(5, RoundingMode.HALF_UP);
                    return bd.doubleValue();
                })
                .collect(Collectors.toUnmodifiableList());
    }
}
