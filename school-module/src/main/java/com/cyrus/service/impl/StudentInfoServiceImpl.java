package com.cyrus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyrus.mapper.StudentInfoMapper;
import com.cyrus.models.StudentInfo;
import com.cyrus.service.StudentInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生信息表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-05-02
 */
@Service
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoMapper, StudentInfo> implements StudentInfoService {

}
