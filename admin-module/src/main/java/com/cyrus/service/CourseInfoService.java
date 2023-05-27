package com.cyrus.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyrus.config.R;
import com.cyrus.mapper.CollegeInfoMapper;
import com.cyrus.mapper.CourseInfoMapper;
import com.cyrus.mapper.CourseTypeMapper;
import com.cyrus.mapper.MajorsMapper;
import com.cyrus.models.CollegeInfo;
import com.cyrus.models.CourseInfo;
import com.cyrus.models.CourseType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CourseInfoService {

    private final CourseTypeMapper courseTypeMapper;
    private final MajorsMapper majorsMapper;
    private final CollegeInfoMapper collegeInfoMapper;
    private final CourseInfoMapper courseInfoMapper;

    public R findAll() {
        List<CourseInfo> list = courseInfoMapper.selectList(null);
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

    public R delete(Map<String, Object> data) {
        System.out.println(data);
        int b = courseInfoMapper.deleteById(data.get("id").toString());
        if (b == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    public R getCourseById(String id) {
        CourseInfo byId = courseInfoMapper.selectById(id);
        return R.ok().data("course", byId);
    }

    public R search(Map<String, Object> data) {
        List<CourseInfo> list = courseInfoMapper.selectByMap(data);
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

    public R addCourse(CourseInfo courseInfo) {
        int save = courseInfoMapper.insert(courseInfo);
        if (save == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    public R update(CourseInfo courseInfo) {
        QueryWrapper<CourseInfo> courseInfoQueryWrapper = new QueryWrapper<>();
        courseInfoQueryWrapper.eq("id", courseInfo.getId());
        int update = courseInfoMapper.update(courseInfo, courseInfoQueryWrapper);
        if (update == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}
