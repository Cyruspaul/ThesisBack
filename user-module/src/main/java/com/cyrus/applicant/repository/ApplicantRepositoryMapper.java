package com.cyrus.applicant.repository;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyrus.applicant.models.ApplicationInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplicantRepositoryMapper extends BaseMapper<ApplicationInfo> {
}