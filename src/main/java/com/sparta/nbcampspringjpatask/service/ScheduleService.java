package com.sparta.nbcampspringjpatask.service;

import com.sparta.nbcampspringjpatask.dto.*;
import com.sparta.nbcampspringjpatask.entity.Schedule;
import com.sparta.nbcampspringjpatask.entity.ScheduleMapping;
import com.sparta.nbcampspringjpatask.entity.User;
import com.sparta.nbcampspringjpatask.repository.ScheduleMappingRepositry;
import com.sparta.nbcampspringjpatask.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ScheduleService {

    private final UserService userService;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMappingRepositry scheduleMappingRepositry;
    private final RestTemplate restTemplate;

    public ScheduleService(UserService userService, ScheduleRepository scheduleRepository, ScheduleMappingRepositry scheduleMappingRepositry, RestTemplateBuilder builder) {
        this.userService = userService;
        this.scheduleRepository = scheduleRepository;
        this.scheduleMappingRepositry = scheduleMappingRepositry;
        this.restTemplate = builder.build();
    }

    public ScheduleSelectDto createSchedule(ScheduleInsertDto scheduleInsertDto) {
        // 오늘 날짜
        String formatedNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd"));

        // 날씨 정보 가져오기
        WeatherResponseDto[] weatherResponseDto = restTemplate.getForObject("https://f-api.github.io/f-api/weather.json", WeatherResponseDto[].class);

        String weather = Arrays.stream(weatherResponseDto)
                            .filter(it -> it.getDate().equals(formatedNow))
                            .findFirst()
                            .map(WeatherResponseDto::getWeather)
                            .orElseThrow(() -> new NullPointerException("날짜 정보가 없습니다."));

        User authorUser = userService.findById(scheduleInsertDto.getUserId());
        Schedule schedule = new Schedule(scheduleInsertDto , authorUser , weather);

        Schedule saveSchedule = scheduleRepository.save(schedule);

        List<Long> list = new ArrayList<>();
        for (Long l : scheduleInsertDto.getUserList()) {
            if (list.contains(l)) {
                throw new DuplicateKeyException("이미 등록된 유저입니다.");
            } else {
                list.add(l);
            }
            ScheduleMapping scheduleMapping = new ScheduleMapping();
            User user = userService.findById(l);
            scheduleMapping.update(schedule , user);
            saveSchedule.addScheduleMappingList(scheduleMapping);
            scheduleMappingRepositry.save(scheduleMapping);
        }

        return new ScheduleSelectDto(saveSchedule);
    }

    public ScheduleSelectDto selectSchedule(Long id) {
        return new ScheduleSelectDto(findById(id));
    }

    @Transactional
    public ScheduleSelectDto updateSchedule(Long id , ScheduleUpdateDto scheduleUpdateDto) {
        Schedule schedule = findById(id);

        scheduleMappingRepositry.deleteByScheduleId(id);

        schedule.getScheduleMappingList().clear();

        List<Long> list = new ArrayList<>();
        for (Long l : scheduleUpdateDto.getUserList()) {
            if (list.contains(l)) {
                throw new DuplicateKeyException("이미 등록된 유저입니다.");
            } else {
                list.add(l);
            }
            ScheduleMapping scheduleMapping = new ScheduleMapping();
            User user = userService.findById(l);
            scheduleMapping.update(schedule , user);
            schedule.addScheduleMappingList(scheduleMapping);
            scheduleMappingRepositry.save(scheduleMapping);
        }

        schedule.update(scheduleUpdateDto);
        return new ScheduleSelectDto(schedule);
    }

    public Schedule findById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new NullPointerException("선택한 일정은 존재하지 않습니다."));
    }

    public Page<ScheduleSelectAllPagingDto> selectAllPagingSchedule(int page , int size) {
        PageRequest pageRequest = PageRequest.of(page - 1 , size , Sort.by("modifiedAt").descending());
        return scheduleRepository.findAll(pageRequest).map(ScheduleSelectAllPagingDto::new);
    }

    @Transactional
    public void deleteSchedule(Long id) {
        Schedule schedule = findById(id);
        scheduleRepository.delete(schedule);
    }


}
