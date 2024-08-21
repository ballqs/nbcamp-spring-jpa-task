package com.sparta.nbcampspringjpatask.service;

import com.sparta.nbcampspringjpatask.dto.ScheduleInsertDto;
import com.sparta.nbcampspringjpatask.dto.ScheduleSelectDto;
import com.sparta.nbcampspringjpatask.dto.ScheduleUpdateDto;
import com.sparta.nbcampspringjpatask.entity.Schedule;
import com.sparta.nbcampspringjpatask.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleSelectDto createSchedule(ScheduleInsertDto scheduleInsertDto) {
        Schedule schedule = new Schedule(scheduleInsertDto);
        return new ScheduleSelectDto(scheduleRepository.save(schedule));
    }

    public ScheduleSelectDto selectSchedule(Long id) {
        return new ScheduleSelectDto(findById(id));
    }

    @Transactional
    public ScheduleSelectDto updateSchedule(Long id , ScheduleUpdateDto scheduleUpdateDto) {
        Schedule schedule = findById(id);
        schedule.update(scheduleUpdateDto);
        return new ScheduleSelectDto(schedule);
    }

    private Schedule findById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new NullPointerException("선택한 일정은 존재하지 않습니다."));
    }
}
