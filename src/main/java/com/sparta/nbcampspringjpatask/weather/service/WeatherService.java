package com.sparta.nbcampspringjpatask.weather.service;

import com.sparta.nbcampspringjpatask.client.WeatherClient;
import com.sparta.nbcampspringjpatask.weather.dto.WeatherResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class WeatherService {
    private final WeatherClient weatherClient;

    public String getWeather(String date) {
        WeatherResponseDto[] weatherResponseDto = weatherClient.getWeatherData();

        // 날씨 정보 가져오기(같은 클래스 내에 캐싱중인 메서드를 호출하면 캐시가 동작안할수도 있어서 weatherClient 뺌)
        return Arrays.stream(weatherResponseDto)
                .filter(it -> it.getDate().equals(date))
                .findFirst()
                .map(WeatherResponseDto::getWeather)
                .orElseThrow(() -> new IllegalStateException("날짜 정보를 가져올수 없는 상태입니다."));
    }
}
