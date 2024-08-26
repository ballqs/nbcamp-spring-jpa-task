package com.sparta.nbcampspringjpatask.client;

import com.sparta.nbcampspringjpatask.dto.WeatherResponseDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherClient {

    private final RestTemplate restTemplate;

    public WeatherClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Cacheable(value = "weatherCache") // Cacheable는 스프링의 프록시 메커니즘에 의존
    public WeatherResponseDto[] getWeatherData() {
        return restTemplate.getForObject("https://f-api.github.io/f-api/weather.json", WeatherResponseDto[].class);
    }
}
