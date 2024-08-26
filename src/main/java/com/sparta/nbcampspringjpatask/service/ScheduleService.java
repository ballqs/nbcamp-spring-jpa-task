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
import org.springframework.cache.annotation.Cacheable;
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
import java.util.*;

@Transactional
@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final UserService userService;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMappingRepositry scheduleMappingRepositry;
    private final RestTemplateService restTemplateService;

    public ScheduleSelectDto createSchedule(ScheduleInsertDto scheduleInsertDto) {
        // 오늘 날짜
        String formatedNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd"));

        // 날씨 정보 가져오기(같은 클래스 내에 캐싱중인 메서드를 호출하면 캐시가 동작안할수도 있어서 restTemplateService로 뺌)
        WeatherResponseDto[] weatherResponseDto = restTemplateService.getWeatherData();

        String weather = Arrays.stream(weatherResponseDto)
                            .filter(it -> it.getDate().equals(formatedNow))
                            .findFirst()
                            .map(WeatherResponseDto::getWeather)
                            .orElseThrow(() -> new NullPointerException("날짜 정보가 없습니다."));

        User authorUser = userService.findById(scheduleInsertDto.getUserId());
        Schedule schedule = new Schedule(scheduleInsertDto , authorUser , weather);

        Schedule saveSchedule = scheduleRepository.save(schedule);

        Set<Long> userList = new HashSet<>(scheduleInsertDto.getUserList());

        List<ScheduleMapping> scheduleMappingList = new ArrayList<>();
        for (Long userId : userList) {
            ScheduleMapping scheduleMapping = new ScheduleMapping();
            User user = userService.findById(userId);
            scheduleMapping.update(schedule , user);
            saveSchedule.addScheduleMappingList(scheduleMapping);
            scheduleMappingList.add(scheduleMapping);
        }
        scheduleMappingRepositry.saveAll(scheduleMappingList);

        return new ScheduleSelectDto(saveSchedule);
    }

    public ScheduleSelectDto selectSchedule(Long id) {
        return new ScheduleSelectDto(findById(id));
    }

    public ScheduleSelectDto updateSchedule(Long id , ScheduleUpdateDto scheduleUpdateDto) {
        Schedule schedule = findById(id);

        schedule.getScheduleMappingList().clear();

        Set<Long> userList = new HashSet<>(scheduleUpdateDto.getUserList());
        List<ScheduleMapping> scheduleMappingList = new ArrayList<>();
        for (Long userId : userList) {
            ScheduleMapping scheduleMapping = new ScheduleMapping();
            User user = userService.findById(userId);
            scheduleMapping.update(schedule , user);
            schedule.addScheduleMappingList(scheduleMapping);
            scheduleMappingList.add(scheduleMapping);
        }
        scheduleMappingRepositry.saveAll(scheduleMappingList);

        schedule.update(scheduleUpdateDto);
        return new ScheduleSelectDto(schedule);
    }

    @Transactional(readOnly = true)
    public Schedule findById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new NullPointerException("선택한 일정은 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public Page<ScheduleSelectAllPagingDto> selectAllPagingSchedule(int page , int size) {
        PageRequest pageRequest = PageRequest.of(page - 1 , size , Sort.by("modifiedAt").descending());
        return scheduleRepository.findAll(pageRequest).map(ScheduleSelectAllPagingDto::new);
    }

    public void deleteSchedule(Long id) {
        Schedule schedule = findById(id);
        scheduleRepository.delete(schedule);
    }
}
