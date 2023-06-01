package com.cyrus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyrus.config.PageUtils;
import com.cyrus.config.R;
import com.cyrus.mapper.CollegeInfoMapper;
import com.cyrus.mapper.CourseTypeMapper;
import com.cyrus.models.CollegeInfo;
import com.cyrus.models.CourseInfo;
import com.cyrus.models.CourseType;
import com.cyrus.models.Vo.CourseQueryVo;
import com.cyrus.service.CourseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Api("课程管理")
@RestController
@RequestMapping("/api/school/course")
@RequiredArgsConstructor
public class CourseInfoController {
    private final CollegeInfoMapper collegeInfoMapper;
    private final CourseTypeMapper courseTypeMapper;
    @Autowired
    private CourseInfoService courseInfoService;

    @ApiOperation(value = "所有课程列表")
    @GetMapping("findAll")
    public R findAll() {
        List<CourseInfo> list = courseInfoService.list(null);
        list.forEach(courseInfo -> {
            CollegeInfo collegeInfo = collegeInfoMapper.selectOne(new QueryWrapper<CollegeInfo>()
                    .eq("collegeNo", courseInfo.getCourseCollege())
                    .select("collegeName")
            );
            CourseType courseType = courseTypeMapper.selectById(courseInfo.getCourseAttr());
            courseInfo.setCourseCollege(collegeInfo.getCollegeName());
            courseInfo.setCourseAttr(courseType.getTypename());
        });
        return R.ok().data("items", list);

    }

    //删除(利用swagger测试)
    @ApiOperation(value = "逻辑删除课程")
    @PostMapping("logicDelete")
    public R logicDeleteTest(@RequestBody Map<String, Object> data) {
        System.out.println(data);
        boolean b = courseInfoService.removeById(data.get("id").toString());
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //分页查询
    @ApiOperation("分页查询")
    @PostMapping("pageCourse")
    public R pageTest(@RequestBody CourseQueryVo queryVo) {

        if (PageUtils.checkCurrentAndSize(queryVo)) {
            Page<CourseInfo> page = new Page<CourseInfo>(queryVo.getPageParam().get("current"), queryVo.getPageParam().get("size"));
            courseInfoService.page(page, null);
            //返回结果封装在page中 ，
            long total = page.getTotal();
            List<CourseInfo> records = page.getRecords();
            return R.ok().data("total", total).data("records", records);
        } else {
            return R.error().message("page参数为空");
        }
    }

    //条件查询带分页
    @ApiOperation("条件查询带分页")
    @PostMapping("pageCourseCondition")
    public R pageCourseCondition(@RequestBody CourseQueryVo queryVo) {
        if (PageUtils.checkCurrentAndSize(queryVo)) {
            //创建page
            Page<CourseInfo> page = new Page<CourseInfo>(queryVo.getPageParam().get("current"), queryVo.getPageParam().get("size"));

            //条件构造器
            QueryWrapper<CourseInfo> queryWrapper = new QueryWrapper();

            //动态sql拼接(可以写utIl处理)
            String courseAttr = queryVo.getCourseAttr();
            String courseCollege = queryVo.getCourseCollege();
            String courseName = queryVo.getCourseName();
            String courseNo = queryVo.getCourseNo();
            if (!StringUtils.isEmpty(courseAttr)) {
                queryWrapper.ge("stu_number", courseAttr);
            }
            if (!StringUtils.isEmpty(courseCollege)) {
                queryWrapper.le("class_id", courseCollege);
            }
            if (!StringUtils.isEmpty(courseName)) {
                queryWrapper.eq("department", courseName);
            }
            if (!StringUtils.isEmpty(courseNo)) {
                queryWrapper.like("major", courseNo);
            }
            //返回结果封装在page中
            courseInfoService.page(page, queryWrapper);
            long total = page.getTotal();
            List<CourseInfo> records = page.getRecords();
            return R.ok().data("total", total).data("records", records);
        } else {
            return R.error().message("page参数为空");
        }

    }

    //查询课程
    @PostMapping("getById/{id}")
    @ApiOperation("根据id查询课程")
    public R getCourseById(@PathVariable String id) {
        CourseInfo byId = courseInfoService.getById(id);
        return R.ok().data("course", byId);
    }

    //查询课程
    @PostMapping("search")
    @ApiOperation("根据查询课程")
    public R search(@RequestBody Map<String, Object> data) {

        List<CourseInfo> list = courseInfoService.getBaseMapper().selectByMap(data);
        list.forEach(courseInfo -> {
            CollegeInfo collegeInfo = collegeInfoMapper.selectOne(new QueryWrapper<CollegeInfo>()
                    .eq("collegeNo", courseInfo.getCourseCollege())
                    .select("collegeName")
            );
            CourseType courseType = courseTypeMapper.selectById(courseInfo.getCourseAttr());
            courseInfo.setCourseCollege(collegeInfo.getCollegeName());
            courseInfo.setCourseAttr(courseType.getTypename());
        });
        return R.ok().data("items", list);
    }

    //添加课程的方法
    @PostMapping("addCourse")
    @ApiOperation("添加课程")
    public R addCourse(@RequestBody CourseInfo courseInfo) {
        boolean save = courseInfoService.save(courseInfo);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //修改课程
    @PostMapping("updateCourse")
    @ApiOperation("更新课程")
    public R updateCourse(@RequestBody CourseInfo courseInfo) {
        QueryWrapper<CourseInfo> courseInfoQueryWrapper = new QueryWrapper<>();
        courseInfoQueryWrapper.eq("id", courseInfo.getId());
        boolean update = courseInfoService.update(courseInfo, courseInfoQueryWrapper);
        if (update) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //无条件查询课程
    @GetMapping("getCourseList")
    public R getCourseList() {
        List<CourseInfo> list = courseInfoService.list(null);
        return R.ok().data("items", list);
    }
}

