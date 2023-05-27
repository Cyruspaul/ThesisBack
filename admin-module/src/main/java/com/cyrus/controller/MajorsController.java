package com.cyrus.controller;


import com.cyrus.config.R;
import com.cyrus.mapper.CollegeInfoMapper;
import com.cyrus.models.Majors;
import com.cyrus.service.MajorsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("专业管理")
@RestController
@RequestMapping("/api/admin/majors")
@RequiredArgsConstructor

public class MajorsController {
    @Autowired
    private final MajorsService majorsService;
    private final CollegeInfoMapper collegeInfoMapper;


    @ApiOperation(value = "所有专业列表")
    @GetMapping("findAll")
    public R findAll() {
        return majorsService.findAll();
    }

    @DeleteMapping("delete/{id}")
    public R logicDeleteTest(@PathVariable String id) {
        return majorsService.delete(id);
    }


    //查询专业
    @PostMapping("getById/{id}")
    @ApiOperation("根据id查询专业")
    public R getMajorById(@PathVariable String id) {
        return majorsService.getById(id);
    }

    //添加专业的方法
    @PostMapping("addMajor")
    @ApiOperation("添加专业")
    public R addMajor(@RequestBody Majors majors) {
        return majorsService.save(majors);
    }

    //修改专业
    @PostMapping("updateMajor")
    @ApiOperation("更新专业")
    public R updateMajor(@RequestBody Majors majors) {
        return majorsService.update(majors);
    }

}

