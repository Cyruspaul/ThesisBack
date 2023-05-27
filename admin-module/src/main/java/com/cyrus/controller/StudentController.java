package com.cyrus.controller;

import com.cyrus.config.R;
import com.cyrus.models.StudentInfo;
import com.cyrus.service.StudentInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/student")
@RequiredArgsConstructor
public class StudentController {

    @Autowired
    private StudentInfoService studentInfoService;

    @ApiOperation(value = "所有学生列表")
    @GetMapping("findAll")
    public R findAll() {
        return studentInfoService.findAll();

    }

    @PostMapping("search")
    public R search(@RequestBody Map<String, Object> data) {
        return studentInfoService.search(data);
    }


    //逻辑删除(利用swagger测试)
    @ApiOperation(value = "逻辑删除学生")
    @DeleteMapping("logicDelete/{id}")
    public R logicDeleteTest(@PathVariable String id) {
        return studentInfoService.delete(id);
    }

    //查询学生
    @PostMapping("getById/{id}")
    @ApiOperation("根据id查询学生")
    public R getStudentById(@PathVariable String id) {
        return studentInfoService.getbyId(id);
    }

    //添加学生的方法
    @PostMapping("addStudent")
    @ApiOperation("添加学生")
    public R addStudent(@RequestBody StudentInfo studentInfo) {
        return studentInfoService.add(studentInfo);
    }

    //修改学生
    @PostMapping("updateStudent")
    @ApiOperation("更新学生")
    public R updateStudent(@RequestBody StudentInfo studentInfo) {
        return studentInfoService.update(studentInfo);
    }

}
