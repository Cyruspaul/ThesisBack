package com.cyrus.controller;


import com.cyrus.config.R;
import com.cyrus.models.CollegeInfo;
import com.cyrus.service.CollegeInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/admin/college")
@RequiredArgsConstructor
public class CollegeInfoController {

    @Autowired
    private final CollegeInfoService collegeInfoService;


    @ApiOperation(value = "所有学院列表")
    @GetMapping("findAll")
    public R findAll() {
        return collegeInfoService.findAll();
    }

    //删除(利用swagger测试)
    @ApiOperation(value = "逻辑删除学院")
    @DeleteMapping("delete/{id}")
    public R logicDeleteTest(@PathVariable String id) {
        return collegeInfoService.remove(id);
    }

    //查询学院
    @RequestMapping("getById/{id}")
    @ApiOperation("根据id查询学院")
    public R getCollegeById(@PathVariable String id) {
        return collegeInfoService.getById(id);
    }

    @PostMapping("search")
    public R search(@RequestBody Map<String, Object> data) {
        return collegeInfoService.search(data);
    }

    //添加学院的方法
    @PostMapping("addCollege")
    @ApiOperation("添加学院")
    public R addCollege(@RequestBody CollegeInfo collegeInfo) {
        return collegeInfoService.addCollege(collegeInfo);
    }

    //修改学院
    @PostMapping("updateCollege")
    @ApiOperation("更新学院")
    public R updateCollege(@RequestBody CollegeInfo collegeInfo) {
        return collegeInfoService.update(collegeInfo);
    }

}

