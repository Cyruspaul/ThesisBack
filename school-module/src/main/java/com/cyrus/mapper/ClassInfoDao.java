package com.cyrus.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyrus.models.ClassInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ClassInfoDao extends BaseMapper<ClassInfo> {
    int selectStudentNumber(@Param("classNo") String classNo);
}