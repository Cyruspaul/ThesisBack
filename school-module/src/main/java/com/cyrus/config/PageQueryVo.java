package com.cyrus.config;

import lombok.Data;

import java.util.Map;

@Data
public class PageQueryVo {
    public Map<String, Integer> pageParam;//需要current,size 两个参数
}
