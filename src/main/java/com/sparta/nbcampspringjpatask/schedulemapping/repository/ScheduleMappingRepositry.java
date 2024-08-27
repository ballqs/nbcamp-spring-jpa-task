package com.sparta.nbcampspringjpatask.schedulemapping.repository;

import com.sparta.nbcampspringjpatask.schedulemapping.entity.ScheduleMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleMappingRepositry extends JpaRepository<ScheduleMapping, Long> {
}
