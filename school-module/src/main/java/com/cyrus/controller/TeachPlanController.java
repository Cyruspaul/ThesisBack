package com.cyrus.controller;


import com.cyrus.config.R;
import com.cyrus.mapper.CourseInfoMapper;
import com.cyrus.mapper.TeachPlanDao;
import com.cyrus.mapper.TeacherInfoMapper;
import com.cyrus.models.TeachPlan;
import com.cyrus.models.Vo.TeachPlanDTO;
import com.cyrus.service.TeachPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/school/teachplan")
@RequiredArgsConstructor
public class TeachPlanController {

    private final TeachPlanDao teachPlanDao;

    private final TeachPlanService teachPlanService;
    private final TeacherInfoMapper teacherInfoMapper;
    private final CourseInfoMapper courseInfoMapper;

    @PostMapping("getById")
    public R getTeachplanById(@RequestBody Map<String, Object> data) {
//        List<TeachPlan> byId = teachPlanDao.selectList(new QueryWrapper<TeachPlan>().eq("majorNo", data.get("majorNo")));

//        List<Map<String, Object>> majorNo = new ArrayList<>();
//
//        byId.forEach(teachPlan -> {
//            TeacherInfo teacherNo = teacherInfoMapper.selectOne(new QueryWrapper<TeacherInfo>().eq("teacherNo", teachPlan.getTeacher()).select("teacherName"));
//            CourseInfo courseInfo = courseInfoMapper.selectOne(new QueryWrapper<CourseInfo>().eq("courseNo", teachPlan.getCourseNo()).select("courseName"));
//
//        });
        System.out.println(data);
        List<Map<String, Object>> byId = teachPlanDao.getDataMap(data.get("id").toString(), data.get("studyLevel").toString());
        return R.ok().data("items", byId);
    }
//    @ApiOperation(value = "所有讲师列表")
//    @GetMapping("findAll")
//    public R findAll(){
//        List<TeacherInfo> list = TeachPlanDao.list(null);
//        return R.ok().data("items",list);
//
//    }

//    //逻辑删除(利用swagger测试)
//    @ApiOperation(value = "逻辑删除讲师")
//    @DeleteMapping("logicDelete/{id}")
//    public R logicDeleteTest(@PathVariable String id ){
//        boolean b = teacherInfoService.removeById(id);
//        if (b){
//            return R.ok();
//        }else {
//            return R.error();
//        }
//    }
    //分页查询
//    @ApiOperation("分页查询")
//    @PostMapping("pageTeacher")
//    public R pageTest(@RequestBody TeacherQueryVo queryVo){
//        if(PageUtils.checkCurrentAndSize(queryVo)) {
//            Page<TeacherInfo> page = new Page<TeacherInfo>(queryVo.getPageParam().get("current"),queryVo.getPageParam().get("size"));
//            teacherInfoService.page(page,null);
//            //返回结果封装在page中 ，
//            long total = page.getTotal();
//            List<TeacherInfo> records = page.getRecords();
//            return R.ok().data("total",total).data("records",records);
//        }else {
//            return R.error().message("page参数为空");
//        }
//
//    }

    //条件查询带分页
//    @ApiOperation("条件查询带分页")
//    @PostMapping("pageTeacherCondition")
//    public R pageTeacherCondition(@RequestBody TeacherQueryVo queryVo){
//        if(PageUtils.checkCurrentAndSize(queryVo)) {
//            //创建page
//            Page<TeacherInfo> page = new Page<TeacherInfo>(queryVo.getPageParam().get("current"),queryVo.getPageParam().get("size"));
//
//            //条件构造器
//            QueryWrapper queryWrapper = new QueryWrapper();
//
//            //动态sql拼接(可以写utIl处理)
//            String teaNumber = queryVo.getTeaNumber();
//            String title = queryVo.getTitle();
//            String colleageNo = queryVo.getColleageNo();
//            String teaName = queryVo.getTeaName();
//            if (!StringUtils.isEmpty(teaNumber)){
//                queryWrapper.ge("teacherNo",teaNumber);
//            }
//            if (!StringUtils.isEmpty(teaName)){
//                queryWrapper.le("teacherName",teaName);
//            }
//            if (!StringUtils.isEmpty(colleageNo)){
//                queryWrapper.eq("collegeNo",colleageNo);
//            }
//            if (!StringUtils.isEmpty(title)){
//                queryWrapper.like("title",title);
//            }
//            //返回结果封装在page中
//            teacherInfoService.page(page,queryWrapper);
//            long total = page.getTotal();
//            List<TeacherInfo> records = page.getRecords();
//            return R.ok().data("total",total).data("records",records);
//        }else {
//            return R.error().message("page参数为空");
//        }
//
//    }
    //查询讲师


    //添加讲师的方法
//    @PostMapping("addTeacher")
//    @ApiOperation("添加讲师")
//    public R addTeacher(@RequestBody TeacherInfo teacherInfo){
//        boolean save = teacherInfoService.save(teacherInfo);
//        if (save){
//            return R.ok();
//        }else {
//            return R.error();
//        }
//    }

    //修改讲师
//    @PostMapping("updateTeacher")
//    @ApiOperation("更新讲师")
//    public R updateTeacher(@RequestBody TeacherInfo teacherInfo){
//        QueryWrapper<TeacherInfo> teacherInfoQueryWrapper = new QueryWrapper<>();
//        teacherInfoQueryWrapper.eq("id",teacherInfo.getId());
//        boolean update = teacherInfoService.update(teacherInfo,teacherInfoQueryWrapper);
//        if (update){
//            return R.ok();
//        }else {
//            return R.error();
//        }
//    }
    //无条件查询讲师
//    @GetMapping("getTeacherList")
//    public R getTeacherList(){
//        List<TeacherInfo> list = teacherInfoService.list(null);
//        return R.ok().data("items",list);
//    }
//

    @PostMapping("createTeachPlan")
    public R getTeacherList(@RequestBody TeachPlanDTO data) {
//        System.out.println(data);

        List<TeachPlan> teachPlanList = new ArrayList<>();

        data.getPosts().forEach(posts -> {
            TeachPlan teachPlan = new TeachPlan();
            teachPlan.setCourseAttr((String.valueOf(posts.get("courseAttr"))));
            teachPlan.setTeacher((String.valueOf(posts.get("teacher"))));
            teachPlan.setClassHour(Integer.parseInt((String.valueOf(posts.get("classHour")))));
            teachPlan.setCredit(Double.parseDouble((String.valueOf(posts.get("credit")))));
            teachPlan.setCourseNo((String.valueOf(posts.get("course"))));
            teachPlan.setMajorNo(data.getMajorNo());
            teachPlan.setStudyLevel(data.getStudyLevel());
            teachPlan.setSemester((String.valueOf(posts.get("semester"))));
            teachPlan.setIsDeleted(0);
            teachPlan.setGmtCreate(Date.valueOf(LocalDateTime.now().toLocalDate()));
            teachPlan.setGmtModified(Date.valueOf(LocalDateTime.now().toLocalDate()));
            teachPlanList.add(teachPlan);
        });

        boolean saveBatch = teachPlanService.saveBatch(teachPlanList);

        return R.ok().data("result", saveBatch).message("okay");
    }
}
