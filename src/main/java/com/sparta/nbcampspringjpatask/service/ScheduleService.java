package com.sparta.nbcampspringjpatask.service;

import com.sparta.nbcampspringjpatask.dto.ScheduleInsertDto;
import com.sparta.nbcampspringjpatask.dto.ScheduleSelectAllPagingDto;
import com.sparta.nbcampspringjpatask.dto.ScheduleSelectDto;
import com.sparta.nbcampspringjpatask.dto.ScheduleUpdateDto;
import com.sparta.nbcampspringjpatask.entity.Schedule;
import com.sparta.nbcampspringjpatask.entity.ScheduleMapping;
import com.sparta.nbcampspringjpatask.entity.User;
import com.sparta.nbcampspringjpatask.repository.ScheduleMappingRepositry;
import com.sparta.nbcampspringjpatask.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserService userService;
    private final ScheduleMappingRepositry scheduleMappingRepositry;

    public ScheduleSelectDto createSchedule(ScheduleInsertDto scheduleInsertDto) {
        Schedule schedule = new Schedule(scheduleInsertDto);
        Schedule saveSchedule = scheduleRepository.save(schedule);

        for (Long l : scheduleInsertDto.getUserList()) {
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

        for (Long l : scheduleUpdateDto.getUserList()) {
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
