package com.cyrus.controller;


import com.cyrus.config.R;
import com.cyrus.models.CourseInfo;
import com.cyrus.service.CourseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-05-02
 */
@Api("课程管理")
@RestController
@RequestMapping("/api/admin/course")
@RequiredArgsConstructor
public class CourseInfoController {
    @Autowired
    private CourseInfoService courseInfoService;


    @ApiOperation(value = "所有课程列表")
    @GetMapping("findAll")
    public R findAll() {
        return courseInfoService.findAll();
    }


    @ApiOperation(value = "逻辑删除课程")
    @PostMapping("logicDelete")
    public R logicDeleteTest(@RequestBody Map<String, Object> data) {
        return courseInfoService.delete(data);
    }

    //查询课程
    @PostMapping("getById/{id}")
    @ApiOperation("根据id查询课程")
    public R getCourseById(@PathVariable String id) {
        return courseInfoService.getCourseById(id);
    }

    //查询课程
    @PostMapping("search")
    @ApiOperation("根据查询课程")
    public R search(@RequestBody Map<String, Object> data) {
        return courseInfoService.search(data);
    }

    //添加课程的方法
    @PostMapping("addCourse")
    @ApiOperation("添加课程")
    public R addCourse(@RequestBody CourseInfo courseInfo) {
        return courseInfoService.addCourse(courseInfo);
    }

    //修改课程
    @PostMapping("updateCourse")
    @ApiOperation("更新课程")
    public R updateCourse(@RequestBody CourseInfo courseInfo) {
        return courseInfoService.update(courseInfo);
    }

}

