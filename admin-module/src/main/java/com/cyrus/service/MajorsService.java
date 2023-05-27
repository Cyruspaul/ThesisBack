package com.cyrus.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyrus.config.R;
import com.cyrus.config.RedisOperation;
import com.cyrus.mapper.CollegeInfoMapper;
import com.cyrus.mapper.MajorsMapper;
import com.cyrus.models.CollegeInfo;
import com.cyrus.models.Majors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MajorsService {
    private static final String REDISMAJOR = "majorList";
    private final MajorsMapper majorsMapper;
    private final CollegeInfoMapper collegeInfoMapper;
    private final RedisOperation<?> redisOperation;

    //FIND ALL
    public R findAll() {
        List<Majors> list = (List<Majors>) redisOperation.getRedisList(REDISMAJOR);

        if (list == null) {
            list = majorsMapper.selectList(null);
            list.forEach(majors -> {
                if (!majors.getCollegeId().isEmpty()) {
                    CollegeInfo collegeInfo = collegeInfoMapper.selectOne(new QueryWrapper<CollegeInfo>()
                            .eq("collegeNo", majors.getCollegeId())
                            .select("collegeName"));
                    majors.setCollegeName(collegeInfo.getCollegeName());
                }
            });

            //SAVE REDIS SAMPLE
            redisOperation.saveRedisList(REDISMAJOR, list);
        }

        return R.ok().data("items", list);
    }

    public R delete(String id) {
        int b = majorsMapper.deleteById(id);
        redisOperation.deleteRedisValue(REDISMAJOR);
        if (b == 1) {
            return R.ok().message("删除成功");
        } else {
            return R.ok().message("删除失败");
        }
    }

    public R getById(String id) {
        Majors byId = majorsMapper.selectById(id);
        if (byId != null) {
            return R.ok().data("college", byId);
        }
        return R.ok().message("无");

    }

    public R save(Majors majors) {

        int save = majorsMapper.insert(majors);
        if (save == 1) {
            redisOperation.deleteRedisValue(REDISMAJOR);
            return R.ok();
        } else {
            return R.error();
        }
    }

    public R update(Majors majors) {
        QueryWrapper<Majors> majorsQueryWrapper = new QueryWrapper<>();
        majorsQueryWrapper.eq("id", majors.getId());
        int update = majorsMapper.update(majors, majorsQueryWrapper);
        if (update == 1) {
            redisOperation.deleteRedisValue(REDISMAJOR);
            return R.ok();
        } else {
            return R.error();
        }
    }
}
