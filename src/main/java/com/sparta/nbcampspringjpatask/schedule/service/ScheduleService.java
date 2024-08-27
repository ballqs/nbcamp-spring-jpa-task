package com.sparta.nbcampspringjpatask.schedule.service;

import com.sparta.nbcampspringjpatask.schedule.dto.ScheduleInsertDto;
import com.sparta.nbcampspringjpatask.schedule.dto.ScheduleSelectAllPagingDto;
import com.sparta.nbcampspringjpatask.schedule.dto.ScheduleSelectDto;
import com.sparta.nbcampspringjpatask.schedule.dto.ScheduleUpdateDto;
import com.sparta.nbcampspringjpatask.schedule.entity.Schedule;
import com.sparta.nbcampspringjpatask.schedulemapping.entity.ScheduleMapping;
import com.sparta.nbcampspringjpatask.user.entity.User;
import com.sparta.nbcampspringjpatask.schedulemapping.repository.ScheduleMappingRepositry;
import com.sparta.nbcampspringjpatask.schedule.repository.ScheduleRepository;
import com.sparta.nbcampspringjpatask.user.service.UserService;
import com.sparta.nbcampspringjpatask.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final UserService userService;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMappingRepositry scheduleMappingRepositry;
    private final WeatherService weatherService;

    public ScheduleSelectDto createSchedule(ScheduleInsertDto scheduleInsertDto) {
        // 오늘 날짜
        String formatedNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd"));
        String weather = weatherService.getWeather(formatedNow);

        User authorUser = userService.getUserEntity(scheduleInsertDto.getUserId());
        Schedule schedule = new Schedule(scheduleInsertDto.getTitle() , scheduleInsertDto.getContent() , authorUser , weather);

        scheduleRepository.save(schedule);

        List<ScheduleMapping> scheduleMappingList = new ArrayList<>();
        for (Long userId : scheduleInsertDto.getUserList()) {
            ScheduleMapping scheduleMapping = new ScheduleMapping();
            User user = userService.getUserEntity(userId);
            scheduleMapping.update(schedule , user);
            schedule.addScheduleMappingList(scheduleMapping);
            scheduleMappingList.add(scheduleMapping);
        }
        scheduleMappingRepositry.saveAll(scheduleMappingList);

        return new ScheduleSelectDto(schedule);
    }

    public ScheduleSelectDto getSchedule(Long id) {
        return new ScheduleSelectDto(scheduleRepository.findByIdOrElseThrow(id));
    }

    public ScheduleSelectDto updateSchedule(Long id , ScheduleUpdateDto scheduleUpdateDto) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);

        schedule.getScheduleMappingList().clear();

        List<ScheduleMapping> scheduleMappingList = new ArrayList<>();
        for (Long userId : scheduleUpdateDto.getUserList()) {
            ScheduleMapping scheduleMapping = new ScheduleMapping();
            User user = userService.getUserEntity(userId);
            scheduleMapping.update(schedule , user);
            schedule.addScheduleMappingList(scheduleMapping);
            scheduleMappingList.add(scheduleMapping);
        }
        scheduleMappingRepositry.saveAll(scheduleMappingList);

        schedule.update(scheduleUpdateDto.getTitle() , scheduleUpdateDto.getContent());
        return new ScheduleSelectDto(schedule);
    }

    @Transactional(readOnly = true)
    public Schedule getSchduleEntity(Long id) {
        return scheduleRepository.findByIdOrElseThrow(id);
    }

    @Transactional(readOnly = true)
    public Page<ScheduleSelectAllPagingDto> search(int page , int size) {
        PageRequest pageRequest = PageRequest.of(page - 1 , size , Sort.by("modifiedAt").descending());
        return scheduleRepository.findAll(pageRequest).map(ScheduleSelectAllPagingDto::new);
    }

    public void removeSchedule(Long id) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);
        scheduleRepository.delete(schedule);
    }
}
