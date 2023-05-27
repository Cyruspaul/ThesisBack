package com.cyrus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyrus.models.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
}
