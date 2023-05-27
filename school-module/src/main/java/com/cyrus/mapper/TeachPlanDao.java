package com.cyrus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyrus.models.TeachPlan;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TeachPlanDao extends BaseMapper<TeachPlan> {

    //    List<TeachPlanVO> selectAll(QueryVO queryVO);
    @MapKey(value = "getData")
    List<Map<String, Object>> getData(@Param("id") String id);

    @MapKey(value = "getDataMap")
    List<Map<String, Object>> getDataMap(@Param("id") String id, @Param("level") String level);

}