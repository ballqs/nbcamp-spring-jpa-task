package com.sparta.nbcampspringjpatask.schedule.repository;

import com.sparta.nbcampspringjpatask.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.NoSuchElementException;

public interface ScheduleRepository extends JpaRepository<Schedule , Long> {
    default Schedule findByIdOrElseThrow(long id) {
        return findById(id).orElseThrow(() -> new NoSuchElementException("선택한 일정은 존재하지 않습니다."));
    }
}
