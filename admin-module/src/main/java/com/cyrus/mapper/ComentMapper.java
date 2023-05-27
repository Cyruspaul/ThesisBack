package com.cyrus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyrus.models.Comments;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ComentMapper extends BaseMapper<Comments> {
}
