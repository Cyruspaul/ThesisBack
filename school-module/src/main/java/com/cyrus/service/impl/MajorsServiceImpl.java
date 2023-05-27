package com.cyrus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyrus.mapper.MajorsMapper;
import com.cyrus.models.Majors;
import com.cyrus.service.MajorsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 专业表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-05-04
 */
@Service
public class MajorsServiceImpl extends ServiceImpl<MajorsMapper, Majors> implements MajorsService {

}
