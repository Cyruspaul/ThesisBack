package com.cyrus.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyrus.config.R;
import com.cyrus.mapper.ClassInfoDao;
import com.cyrus.mapper.StudentInfoMapper;
import com.cyrus.models.ClassInfo;
import com.cyrus.models.StudentInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentInfoService {

    private final StudentInfoMapper studentInfoMapper;
    private final ClassInfoDao classInfoDao;

    public R findAll() {
        List<StudentInfo> list = studentInfoMapper.selectList(null);

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


    public R search(Map<String, Object> data) {
        System.out.println(data);
        List<StudentInfo> list = studentInfoMapper.selectByMap(data);
//        if (data.get("stuNumber") != null && data.get("stuName") != null) {
//            list = studentInfoMapper.selectList(new QueryWrapper<StudentInfo>()
//                    .like("stu_name", data.get("stuName"))
//                    .like("stu_number", data.get("stuNumber"))
//            );
//        } else if (data.get("stuNumber") == null) {
//            list = studentInfoMapper.selectList(new QueryWrapper<StudentInfo>()
//                    .like("stuName", data.get("stu_name"))
//            );
//        } else if (data.get("stuName") == null) {
//            list = studentInfoMapper.selectList(new QueryWrapper<StudentInfo>()
//                    .like("stu_number", data.get("stuNumber"))
//            );
//        }


        return R.ok().data("items", list);
//        list.forEach(teacherInfo -> {
//            List<Majors> majorsList= new ArrayList<>();
//
//            majorsList = teacherInfoMapper.selectList(new QueryWrapper<Majors>()
//                    .eq("college_id",teacherInfo.getCollegeNo())
//                    .select("id", "major_name")
//            );
//            collegeInfo.setMajorsList(majorsList);
//        });
    }


    public R delete(String id) {
        int b = studentInfoMapper.deleteById(id);
        if (b == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    public R update(StudentInfo studentInfo) {
        studentInfo.setGmtModified(Date.valueOf(LocalDate.now()));
        QueryWrapper<StudentInfo> studentInfoQueryWrapper = new QueryWrapper<>();
        studentInfoQueryWrapper.eq("uid", studentInfo.getUid());
        int update = studentInfoMapper.update(studentInfo, studentInfoQueryWrapper);
        if (update == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    public R add(StudentInfo studentInfo) {
        int save = studentInfoMapper.insert(studentInfo);
        if (save == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    public R getbyId(String id) {
        StudentInfo byId = studentInfoMapper.selectById(id);

        return R.ok().data("student", byId);
    }
}
