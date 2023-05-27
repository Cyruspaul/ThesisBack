package com.cyrus.controller;


import com.cyrus.config.R;
import com.cyrus.models.Vo.TeachPlanDTO;
import com.cyrus.service.TeachPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/teachplan")
@RequiredArgsConstructor
public class TeachPlanController {

    private final TeachPlanService teachPlanService;


    @PostMapping("getPlan")
    public R getTeachplan(@RequestBody Map<String, Object> data) {
        return teachPlanService.getPlan(data);
    }


    @PostMapping("getById")
    public R getTeachplanById(@RequestBody Map<String, Object> data) {
        return teachPlanService.getPlanById(data);
    }


    @PostMapping("createTeachPlan")
    public R getTeacherList(@RequestBody TeachPlanDTO data) {
        return teachPlanService.createPlan(data);
    }
}
