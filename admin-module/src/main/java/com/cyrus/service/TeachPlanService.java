package com.cyrus.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyrus.config.FaSystemException;
import com.cyrus.config.MinUnit_Constants;
import com.cyrus.config.R;
import com.cyrus.mapper.TeachPlanDao;
import com.cyrus.models.TeachPlan;
import com.cyrus.models.Vo.TeachPlanDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TeachPlanService {

    private final TeachPlanDao teachPlanDao;


    public R getPlan(Map<String, Object> data) {
        System.out.println(data);
        List<TeachPlan> teachPlans = teachPlanDao.selectList(new QueryWrapper<TeachPlan>().eq("majorNo", data.get("id").toString()));
        return R.ok().data("items", teachPlans);
    }

    public R getPlanById(Map<String, Object> data) {
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

    public R createPlan(TeachPlanDTO data) {
        //        System.out.println(data);

        List<TeachPlan> teachPlanList = new ArrayList<>();

        try {
            data.getPosts()
                    .forEach(posts -> {
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

                                teachPlanDao.insert(teachPlan);
                            }
                    );
        } catch (Exception e) {
            throw new FaSystemException(MinUnit_Constants.WARNING_CODE, "");
        }

        return R.ok().data("result", 1).message("okay");
    }
}
