package com.cyrus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyrus.config.PageUtils;
import com.cyrus.config.R;
import com.cyrus.mapper.ClassInfoDao;
import com.cyrus.models.ClassInfo;
import com.cyrus.models.StudentInfo;
import com.cyrus.models.Vo.StudentQueryVo;
import com.cyrus.service.StudentInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/school/student")
@RequiredArgsConstructor
public class StudentController {
    private final ClassInfoDao classInfoDao;
    @Autowired
    private StudentInfoService studentInfoService;

    @ApiOperation(value = "所有学生列表")
    @GetMapping("findAll")
    public R findAll() {
        List<StudentInfo> list = studentInfoService.list(null);

        list.forEach(studentInfo -> {
            ClassInfo classInfo = classInfoDao.selectOne(new QueryWrapper<ClassInfo>()
                    .eq("classNo", studentInfo.getClassId())
                    .select("className"));

            if (classInfo != null) {
                studentInfo.setClassId(classInfo.getClassname());
            }
        });
        return R.ok().data("items", list);

    }

    @PostMapping("search")
    public R search(@RequestBody Map<String, Object> data) {
        System.out.println(data);
        List<StudentInfo> list = new ArrayList<>();
        if (data.get("stuNumber") != null && data.get("stuName") != null) {
            list = studentInfoService.list(new QueryWrapper<StudentInfo>()
                    .like("stu_name", data.get("stuName"))
                    .like("stu_number", data.get("stuNumber"))
            );
        } else if (data.get("stuNumber") == null) {
            list = studentInfoService.list(new QueryWrapper<StudentInfo>()
                    .like("stuName", data.get("stu_name"))
            );
        } else if (data.get("stuName") == null) {
            list = studentInfoService.list(new QueryWrapper<StudentInfo>()
                    .like("stu_number", data.get("stuNumber"))
            );
        }

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
    @ApiOperation(value = "逻辑删除学生")
    @DeleteMapping("logicDelete/{id}")
    public R logicDeleteTest(@PathVariable String id) {
        boolean b = studentInfoService.removeById(id);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //分页查询
    @ApiOperation("分页查询")
    @PostMapping("pageStudent")
    public R pageTest(@RequestBody StudentQueryVo queryVo) {
        if (PageUtils.checkCurrentAndSize(queryVo)) {
            Page<StudentInfo> page = new Page<StudentInfo>(queryVo.getPageParam().get("current"), queryVo.getPageParam().get("size"));
            studentInfoService.page(page, null);
            //返回结果封装在page中 ，
            long total = page.getTotal();
            List<StudentInfo> records = page.getRecords();
            return R.ok().data("total", total).data("records", records);
        } else {
            return R.error().message("page参数为空");
        }

    }

    //条件查询带分页
    @ApiOperation("条件查询带分页")
    @PostMapping("pageStudentCondition")
    public R pageStudentCondition(@RequestBody StudentQueryVo queryVo) {
        if (PageUtils.checkCurrentAndSize(queryVo)) {
            //创建page
            Page<StudentInfo> page = new Page<StudentInfo>(queryVo.getPageParam().get("current"), queryVo.getPageParam().get("size"));

            //条件构造器
            QueryWrapper queryWrapper = new QueryWrapper();

            //动态sql拼接(可以写utIl处理)
            String stu_number = queryVo.getStu_number();
            String class_id = queryVo.getClass_id();
            String department = queryVo.getDepartment();
            String major = queryVo.getMajor();
            String stu_name = queryVo.getStu_name();
            if (!StringUtils.isEmpty(stu_number)) {
                queryWrapper.ge("stu_number", stu_number);
            }
            if (!StringUtils.isEmpty(class_id)) {
                queryWrapper.le("class_id", class_id);
            }
            if (!StringUtils.isEmpty(department)) {
                queryWrapper.eq("department", department);
            }
            if (!StringUtils.isEmpty(major)) {
                queryWrapper.like("major", major);
            }
            if (!StringUtils.isEmpty(stu_name)) {
                queryWrapper.like("stu_name", stu_name);
            }
            //返回结果封装在page中
            studentInfoService.page(page, queryWrapper);
            long total = page.getTotal();
            List<StudentInfo> records = page.getRecords();
            return R.ok().data("total", total).data("records", records);
        } else {
            return R.error().message("page参数为空");
        }

    }

    //查询学生
    @PostMapping("getById/{id}")
    @ApiOperation("根据id查询学生")
    public R getStudentById(@PathVariable String id) {
        StudentInfo byId = studentInfoService.getById(id);
        return R.ok().data("student", byId);
    }

    //添加学生的方法
    @PostMapping("addStudent")
    @ApiOperation("添加学生")
    public R addStudent(@RequestBody StudentInfo StudentInfo) {
        boolean save = studentInfoService.save(StudentInfo);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //修改学生
    @PostMapping("updateStudent")
    @ApiOperation("更新学生")
    public R updateStudent(@RequestBody StudentInfo studentInfo) {
        studentInfo.setGmtModified(Date.valueOf(LocalDate.now()));
        QueryWrapper<StudentInfo> studentInfoQueryWrapper = new QueryWrapper<>();
        studentInfoQueryWrapper.eq("uid", studentInfo.getUid());
        boolean update = studentInfoService.update(studentInfo, studentInfoQueryWrapper);
        if (update) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //无条件查询学生
    @GetMapping("getStudentList")
    public R getStudentList() {
        List<StudentInfo> list = studentInfoService.list(null);
        return R.ok().data("items", list);
    }
}
