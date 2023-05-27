package com.cyrus.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyrus.config.R;
import com.cyrus.mapper.*;
import com.cyrus.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreApprovalService {
    private final ScoreApprovalMapper scoreApprovalMapper;
    private final ClassInfoDao classInfoDao;
    private final TeachPlanDao teachPlanDao;
    private final TeacherInfoMapper teacherInfoMapper;
    private final CourseInfoMapper courseInfoMapper;

    public R createScore(ScoreApproval scoreApproval) {

        return R.ok();
    }

    public R getByClass(String classid) {
        //Verfiy the class informations
        ClassInfo classInfo = classInfoDao.selectById(classid);
        if (classInfo == null) {
            return R.error().message("班级编号不合格");
        }
        //Get the class major
        String classInfoMajorid = classInfo.getMajorid();

        //Get the major Course list through the study plan by the majorId
        List<TeachPlan> teachPlanList = teachPlanDao.selectList(new QueryWrapper<TeachPlan>()
                .eq("majorNo", classInfoMajorid)
                .eq("studyLevel", classInfo.getStudylevel())
        );

        if (teachPlanList.isEmpty()) {
            return R.error().message("先构建培养方案 ！！").data("classname", classInfo.getClassname());
        }

        List<JSONObject> response = new ArrayList<>();
//        response.add(JSONObject.of("classname",classInfo.getClassname()));
        //With the majorId / ClassId / CourseId get the specific scoreapproval

        teachPlanList.forEach(teachPlan -> {
            ScoreApproval scoreApproval = scoreApprovalMapper.selectOne(new QueryWrapper<ScoreApproval>()
                    .eq("class_id", classid)
                    .eq("course_id", teachPlan.getCourseNo())
            );

            CourseInfo courseName = courseInfoMapper.selectOne(new QueryWrapper<CourseInfo>().eq("courseNo", Integer.parseInt(teachPlan.getCourseNo()))
                    .select("courseName"));
            TeacherInfo teacherInfo = teacherInfoMapper.selectOne(new QueryWrapper<TeacherInfo>()
                    .eq("teacherNo", teachPlan.getTeacher())
                    .select("teacherName"));

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("course", courseName.getCourseName());
            jsonObject.put("teacher", teacherInfo.getTeacherName());
            jsonObject.put("credit", teachPlan.getCredit());
            jsonObject.put("courseattr", teachPlan.getCourseAttr());
            jsonObject.put("semester", teachPlan.getSemester());
            jsonObject.put("class", classInfo.getClassname());
            jsonObject.put("score", scoreApproval);

            response.add(jsonObject);
        });


        return R.ok().data("items", response).data("classname", classInfo.getClassname());

    }

    public R getByCourse(String classid) {
        List<ScoreApproval> scoreApprovalList =
                scoreApprovalMapper.selectList(new QueryWrapper<ScoreApproval>().eq("class_id", classid));
        return R.ok().data("items", scoreApprovalList);
    }
}
