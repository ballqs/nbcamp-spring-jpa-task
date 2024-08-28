package com.sparta.nbcampspringjpatask.client;

import com.sparta.nbcampspringjpatask.weather.dto.WeatherResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "WeatherClientInterface" , url = "https://f-api.github.io")
public interface WeatherClientInterface {
    @GetMapping("/f-api/weather.json")
    WeatherResponseDto[] getWeatherData();
}
