package com.cyrus.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyrus.config.R;
import com.cyrus.mapper.CollegeInfoMapper;
import com.cyrus.mapper.MajorsMapper;
import com.cyrus.models.CollegeInfo;
import com.cyrus.models.Majors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CollegeInfoService {

    private final MajorsMapper majorsMapper;
    private final CollegeInfoMapper collegeInfoMapper;


    public R findAll() {
        List<CollegeInfo> list = collegeInfoMapper.selectList(null);
        List<CollegeInfo> responseList = new ArrayList<>();
        list.forEach(collegeInfo -> {
            List<Majors> majorsList = new ArrayList<>();

            majorsList = majorsMapper.selectList(new QueryWrapper<Majors>()
                    .eq("college_id", collegeInfo.getCollegeNo())
                    .select("id", "major_name")
            );
            if (!majorsList.isEmpty()) {
                collegeInfo.setMajorsList(majorsList);
                responseList.add(collegeInfo);
            }

        });
        return R.ok().data("items", responseList);

    }

    public R remove(String id) {
        int b = collegeInfoMapper.deleteById(id);
        if (b == 1) {
            return R.ok().message("删除成功");
        } else {
            return R.ok().message("删除失败");
        }
    }

    public R getById(String id) {
        CollegeInfo byId = collegeInfoMapper.selectById(id);
        if (byId != null) {
            return R.ok().data("college", byId);
        }
        return R.ok().message("无");
    }

    public R search(Map<String, Object> data) {
        List<CollegeInfo> list = new ArrayList<>();
        list = collegeInfoMapper.selectList(new QueryWrapper<CollegeInfo>().like("collegeName", data.get("collegeName")));
        list.forEach(collegeInfo -> {
            List<Majors> majorsList = new ArrayList<>();

            majorsList = majorsMapper.selectList(new QueryWrapper<Majors>()
                    .eq("college_id", collegeInfo.getCollegeNo())
                    .select("id", "major_name")
            );
            collegeInfo.setMajorsList(majorsList);
        });
        return R.ok().data("items", list);
    }

    public R addCollege(CollegeInfo collegeInfo) {
        int save = collegeInfoMapper.insert(collegeInfo);
        if (save == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    public R update(CollegeInfo collegeInfo) {
        QueryWrapper<CollegeInfo> collegeInfoQueryWrapper = new QueryWrapper<>();
        collegeInfoQueryWrapper.eq("id", collegeInfo.getId());
        int update = collegeInfoMapper.update(collegeInfo, collegeInfoQueryWrapper);
        if (update == 1) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}
