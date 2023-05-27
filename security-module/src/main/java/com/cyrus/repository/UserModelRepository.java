package com.cyrus.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyrus.models.PO.UserModelPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserModelRepository extends BaseMapper<UserModelPO> {
//    Optional<UserModelPO> findByUsername(String username);
}
