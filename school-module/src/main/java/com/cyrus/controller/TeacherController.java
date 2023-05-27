package com.cyrus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyrus.config.PageUtils;
import com.cyrus.config.R;
import com.cyrus.mapper.CollegeInfoMapper;
import com.cyrus.models.TeacherInfo;
import com.cyrus.models.Vo.TeacherQueryVo;
import com.cyrus.service.TeacherInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/school/teacher")
@RequiredArgsConstructor
public class TeacherController {


    private final CollegeInfoMapper teacherInfoMapper;
    @Autowired
    private TeacherInfoService teacherInfoService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAll() {
        List<TeacherInfo> list = teacherInfoService.list(null);
        return R.ok().data("items", list);
    }

    @PostMapping("search")
    public R search(@RequestBody Map<String, Object> data) {
        List<TeacherInfo> list = new ArrayList<>();
        list = teacherInfoService.list(new QueryWrapper<TeacherInfo>()
                .like("teacherName", data.get("teacherName") != null ? data.get("teacherName") : "")
                .eq("title", data.get("title"))
        );
//        list.forEach(teacherInfo -> {
//            List<Majors> majorsList= new ArrayList<>();
//
//            majorsList = teacherInfoMapper.selectList(new QueryWrapper<Majors>()
//                    .eq("college_id",teacherInfo.getCollegeNo())
//                    .select("id", "major_name")
//            );
//            collegeInfo.setMajorsList(majorsList);
//        });
        return R.ok().data("items", list);
    }

    //逻辑删除(利用swagger测试)
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("logicDelete/{id}")
    public R logicDeleteTest(@PathVariable String id) {
        boolean b = teacherInfoService.removeById(id);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //分页查询
    @ApiOperation("分页查询")
    @PostMapping("pageTeacher")
    public R pageTest(@RequestBody TeacherQueryVo queryVo) {
        if (PageUtils.checkCurrentAndSize(queryVo)) {
            Page<TeacherInfo> page = new Page<TeacherInfo>(queryVo.getPageParam().get("current"), queryVo.getPageParam().get("size"));
            teacherInfoService.page(page, null);
            //返回结果封装在page中 ，
            long total = page.getTotal();
            List<TeacherInfo> records = page.getRecords();
            return R.ok().data("total", total).data("records", records);
        } else {
            return R.error().message("page参数为空");
        }

    }

    //条件查询带分页
    @ApiOperation("条件查询带分页")
    @PostMapping("pageTeacherCondition")
    public R pageTeacherCondition(@RequestBody TeacherQueryVo queryVo) {
        if (PageUtils.checkCurrentAndSize(queryVo)) {
            //创建page
            Page<TeacherInfo> page = new Page<TeacherInfo>(queryVo.getPageParam().get("current"), queryVo.getPageParam().get("size"));

            //条件构造器
            QueryWrapper queryWrapper = new QueryWrapper();

            //动态sql拼接(可以写utIl处理)
            String teaNumber = queryVo.getTeaNumber();
            String title = queryVo.getTitle();
            String colleageNo = queryVo.getColleageNo();
            String teaName = queryVo.getTeaName();
            if (!StringUtils.isEmpty(teaNumber)) {
                queryWrapper.ge("teacherNo", teaNumber);
            }
            if (!StringUtils.isEmpty(teaName)) {
                queryWrapper.le("teacherName", teaName);
            }
            if (!StringUtils.isEmpty(colleageNo)) {
                queryWrapper.eq("collegeNo", colleageNo);
            }
            if (!StringUtils.isEmpty(title)) {
                queryWrapper.like("title", title);
            }
            //返回结果封装在page中
            teacherInfoService.page(page, queryWrapper);
            long total = page.getTotal();
            List<TeacherInfo> records = page.getRecords();
            return R.ok().data("total", total).data("records", records);
        } else {
            return R.error().message("page参数为空");
        }

    }

    //查询讲师
    @PostMapping("getById/{id}")
    @ApiOperation("根据id查询讲师")
    public R getTeacherById(@PathVariable String id) {
        TeacherInfo byId = teacherInfoService.getById(id);
        return R.ok().data("teacher", byId);
    }

    //添加讲师的方法
    @PostMapping("addTeacher")
    @ApiOperation("添加讲师")
    public R addTeacher(@RequestBody TeacherInfo teacherInfo) {
        boolean save = teacherInfoService.save(teacherInfo);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //修改讲师
    @PostMapping("updateTeacher")
    @ApiOperation("更新讲师")
    public R updateTeacher(@RequestBody TeacherInfo teacherInfo) {
        QueryWrapper<TeacherInfo> teacherInfoQueryWrapper = new QueryWrapper<>();
        teacherInfoQueryWrapper.eq("id", teacherInfo.getId());
        boolean update = teacherInfoService.update(teacherInfo, teacherInfoQueryWrapper);
        if (update) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //无条件查询讲师
    @GetMapping("getTeacherList")
    public R getTeacherList() {
        List<TeacherInfo> list = teacherInfoService.list(null);
        return R.ok().data("items", list);
    }
}
