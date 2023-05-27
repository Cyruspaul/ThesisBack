package com.cyrus.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyrus.config.R;
import com.cyrus.mapper.ClassInfoDao;
import com.cyrus.mapper.ScoreApprovalMapper;
import com.cyrus.mapper.TeachPlanDao;
import com.cyrus.models.ClassInfo;
import com.cyrus.models.ScoreApproval;
import com.cyrus.models.TeachPlan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ClassService {
    private final ClassInfoDao classInfoDao;
    //    private final RedisOperation<?> redisOperation;
    private final ScoreApprovalMapper scoreApprovalMapper;
    private final TeachPlanDao teachPlanDao;

    public R create(ClassInfo classInfo) {
        classInfo.setClassno(String.valueOf(new Date().getTime()));

        //CHECK IF THE PEIYANG FANGAN EXISTS
        List<TeachPlan> teachPlanList = teachPlanDao.selectList(new QueryWrapper<TeachPlan>()
                .eq("majorNo", classInfo.getMajorid())
                .eq("studyLevel", classInfo.getStudylevel())
        );
        if (teachPlanList.isEmpty()) {
            return R.error().message("先构建培养方案 ！！");
        }

        //EXIST SO CONTINUE TO ADD THE CLASS
        int insert = classInfoDao.insert(classInfo);
        if (insert == 0)
            return R.error().message("创建班级失败 !!");

        //GET THE CLASS INFOS AND CREATE SCORE APPROVAL
        //WE ARE USING THE CLASSNO for reference in the SCore Approval CLASSID column
        teachPlanList.forEach(teachPlan -> {
            ScoreApproval scoreApproval = ScoreApproval.builder()
                    .classid(classInfo.getClassno())
                    .course(teachPlan.getCourseNo())
                    .teacher(teachPlan.getTeacher())
                    .courseattr(teachPlan.getCourseAttr())
                    .semester(teachPlan.getSemester())

                    .status(0)
                    .remark("")
                    .build();

            scoreApprovalMapper.insert(scoreApproval);
        });

        return R.ok().message("创建班级 OKAY");
    }
}
