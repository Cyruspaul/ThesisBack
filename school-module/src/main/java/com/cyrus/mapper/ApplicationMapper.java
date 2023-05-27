package com.cyrus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyrus.models.Application;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplicationMapper extends BaseMapper<Application> {
}
