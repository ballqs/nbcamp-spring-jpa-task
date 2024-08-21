package com.sparta.nbcampspringjpatask.controller;

import com.sparta.nbcampspringjpatask.dto.ResponseDto;
import com.sparta.nbcampspringjpatask.dto.ScheduleInsertDto;
import com.sparta.nbcampspringjpatask.dto.ScheduleSelectDto;
import com.sparta.nbcampspringjpatask.dto.ScheduleUpdateDto;
import com.sparta.nbcampspringjpatask.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 저장
    @PostMapping("/schedules")
    public ResponseEntity<ResponseDto<ScheduleSelectDto>> createSchedule(@RequestBody ScheduleInsertDto scheduleInsertDto) {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), scheduleService.createSchedule(scheduleInsertDto) , "성공적으로 등록완료했습니다."));
    }

    // 일정 단건조회
    @GetMapping("/schedules/{id}")
    public ResponseEntity<ResponseDto<ScheduleSelectDto>> selectSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), scheduleService.selectSchedule(id) , "성공적으로 조회완료했습니다."));
    }

    // 일정 수정
    @PatchMapping("/schedules/{id}")
    public ResponseEntity<ResponseDto<ScheduleSelectDto>> updateSchedule(@PathVariable Long id , @RequestBody ScheduleUpdateDto scheduleUpdateDto) {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), scheduleService.updateSchedule(id , scheduleUpdateDto) , "성공적으로 수정완료했습니다."));
    }


}
