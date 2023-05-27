package com.cyrus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyrus.models.PublicationPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnnouncementsMappper extends BaseMapper<PublicationPO> {
}
