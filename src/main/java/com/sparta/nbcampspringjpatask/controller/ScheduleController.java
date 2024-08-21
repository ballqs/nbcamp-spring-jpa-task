package com.sparta.nbcampspringjpatask.controller;

import com.sparta.nbcampspringjpatask.dto.*;
import com.sparta.nbcampspringjpatask.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<ResponseDto<ScheduleSelectDto>> createSchedule(@RequestBody ScheduleInsertDto scheduleInsertDto) {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), scheduleService.createSchedule(scheduleInsertDto) , "성공적으로 등록완료했습니다."));
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity<ResponseDto<ScheduleSelectDto>> selectSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), scheduleService.selectSchedule(id) , "성공적으로 조회완료했습니다."));
    }

    @GetMapping("/schedules")
    public ResponseEntity<ResponseDto<Page<ScheduleSelectAllPagingDto>>> selectAllPagingSchedule(@RequestParam(defaultValue = "1") int page ,
                                                                                                 @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), scheduleService.selectAllPagingSchedule(page , size) , "성공적으로 조회완료했습니다."));
    }

    @PatchMapping("/schedules/{id}")
    public ResponseEntity<ResponseDto<ScheduleSelectDto>> updateSchedule(@PathVariable Long id , @RequestBody ScheduleUpdateDto scheduleUpdateDto) {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), scheduleService.updateSchedule(id , scheduleUpdateDto) , "성공적으로 수정완료했습니다."));
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<ResponseDto<String>> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), "" , "성공적으로 삭제완료했습니다."));
    }
}
