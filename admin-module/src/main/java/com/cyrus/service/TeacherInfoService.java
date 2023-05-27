package com.cyrus.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyrus.config.R;
import com.cyrus.mapper.CollegeInfoMapper;
import com.cyrus.mapper.TeacherInfoMapper;
import com.cyrus.models.TeacherInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TeacherInfoService {

    private final TeacherInfoMapper teacherInfoMapper;
    private final CollegeInfoMapper collegeInfoMapper;


    public R findAll() {
        List<TeacherInfo> list = teacherInfoMapper.selectList(null);
        return R.ok().data("items", list);
    }

    public R search(Map<String, Object> data) {
        List<TeacherInfo> list = new ArrayList<>();
        list = teacherInfoMapper.selectList(new QueryWrapper<TeacherInfo>()
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

    public R delete(String id) {
        int b = teacherInfoMapper.deleteById(id);
        if (b == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    public R getById(String id) {
        TeacherInfo byId = teacherInfoMapper.selectById(id);
        return R.ok().data("teacher", byId);
    }

    public R addTeacher(TeacherInfo teacherInfo) {
        int save = teacherInfoMapper.insert(teacherInfo);
        if (save == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    public R update(TeacherInfo teacherInfo) {
        QueryWrapper<TeacherInfo> teacherInfoQueryWrapper = new QueryWrapper<>();
        teacherInfoQueryWrapper.eq("id", teacherInfo.getId());
        int update = teacherInfoMapper.update(teacherInfo, teacherInfoQueryWrapper);
        if (update == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}
