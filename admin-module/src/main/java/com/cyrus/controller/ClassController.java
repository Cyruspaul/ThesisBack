package com.cyrus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyrus.config.R;
import com.cyrus.mapper.ClassInfoDao;
import com.cyrus.models.ClassInfo;
import com.cyrus.service.ClassService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/admin/class")
@RequiredArgsConstructor
public class ClassController {

    private final ClassInfoDao classInfoDao;
    private final ClassService classService;


    @PostMapping("create")
    public R create(@RequestBody ClassInfo classInfo) {
        return classService.create(classInfo);
    }


    @ApiOperation(value = "所有列表")
    @GetMapping("findAll")
    public R findAll() {
        List<ClassInfo> list = classInfoDao.selectList(new QueryWrapper<>());
//        list.forEach(ClassInfo -> ClassInfo.setPassword());
        return R.ok().data("items", list);
    }


    @ApiOperation(value = "所有列表")
    @GetMapping("find")
    public R find() {
        List<ClassInfo> list = classInfoDao.selectList(new QueryWrapper<ClassInfo>().isNotNull("majorId"));
//        list.forEach(ClassInfo -> ClassInfo.setPassword());
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "逻辑删除")
    @DeleteMapping("logicDelete/{id}")
    public R logicDelete(@PathVariable String id) {
        int b = classInfoDao.deleteById(id);
        if (b == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //查询学院
    @RequestMapping("search")
    @ApiOperation("根据id查询学院")
    public R search(@RequestBody Map<String, Object> search) {
        List<ClassInfo> list = new ArrayList<>();
        System.out.println(search.get("majorId").toString().equals("0"));
        if (search.get("majorId").toString().equals("0")) {
            list = classInfoDao.selectList(new QueryWrapper<ClassInfo>().isNotNull("majorId"));
//        list.forEach(ClassInfo -> ClassInfo.setPassword());
            return R.ok().data("items", list);
        } else {
            list = classInfoDao.selectByMap(search);
        }
        return R.ok().data("items", list);
    }

    @PostMapping("update")
    public R updateClass(@RequestBody ClassInfo ClassInfo) {
        int update = classInfoDao.updateById(ClassInfo);
        if (update == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @GetMapping("getById/{id}")
    public R getById(@PathVariable String id) {
        ClassInfo update = classInfoDao.selectById(id);
        if (update == null) {
            return R.error().message("班级不存在 ！！");
        } else {
            return R.ok().data("class", update);
        }
    }

}
