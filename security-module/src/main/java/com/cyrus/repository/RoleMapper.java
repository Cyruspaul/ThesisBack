package com.cyrus.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyrus.models.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
//    Map<String, Object> getUserRole(String account);
}
