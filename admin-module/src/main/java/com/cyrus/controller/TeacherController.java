package com.cyrus.controller;

import com.cyrus.config.R;
import com.cyrus.models.TeacherInfo;
import com.cyrus.service.TeacherInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherInfoService teacherInfoService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAll() {
        return teacherInfoService.findAll();
    }

    @PostMapping("search")
    public R search(@RequestBody Map<String, Object> data) {
        return teacherInfoService.search(data);
    }


    @DeleteMapping("logicDelete/{id}")
    public R logicDeleteTest(@PathVariable String id) {
        return teacherInfoService.delete(id);
    }

    //查询讲师
    @PostMapping("getById/{id}")
    @ApiOperation("根据id查询讲师")
    public R getTeacherById(@PathVariable String id) {
        return teacherInfoService.getById(id);
    }

    //添加讲师的方法
    @PostMapping("addTeacher")
    @ApiOperation("添加讲师")
    public R addTeacher(@RequestBody TeacherInfo teacherInfo) {
        return teacherInfoService.addTeacher(teacherInfo);
    }

    //修改讲师
    @PostMapping("updateTeacher")
    @ApiOperation("更新讲师")
    public R updateTeacher(@RequestBody TeacherInfo teacherInfo) {
        return teacherInfoService.update(teacherInfo);
    }

}
