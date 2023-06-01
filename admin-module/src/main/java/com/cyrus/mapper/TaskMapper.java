package com.cyrus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyrus.models.TaskPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskMapper extends BaseMapper<TaskPO> {
}
