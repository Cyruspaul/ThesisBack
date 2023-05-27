package com.cyrus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyrus.models.StudentInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 学生信息表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2023-05-02
 */
@Mapper
public interface StudentInfoMapper extends BaseMapper<StudentInfo> {

}
