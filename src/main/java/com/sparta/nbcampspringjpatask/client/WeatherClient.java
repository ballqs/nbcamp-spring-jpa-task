package com.sparta.nbcampspringjpatask.client;

import com.sparta.nbcampspringjpatask.weather.dto.WeatherResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WeatherClient {

//    private final RestTemplate restTemplate;
    private final WeatherClientInterface weatherClientInterface;

//    public WeatherClient(RestTemplateBuilder builder) {
//        this.restTemplate = builder.build();
//    }

    @Cacheable(value = "weatherCache") // Cacheable는 스프링의 프록시 메커니즘에 의존
    public WeatherResponseDto[] getWeatherData() {
        return weatherClientInterface.getWeatherData();
//        return restTemplate.getForObject("https://f-api.github.io/f-api/weather.json", WeatherResponseDto[].class);
    }
}
