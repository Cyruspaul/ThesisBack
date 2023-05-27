package com.cyrus.config;

import org.springframework.util.StringUtils;

public class PageUtils {
    public static boolean checkCurrentAndSize(PageQueryVo queryVo) {
        return !StringUtils.isEmpty(queryVo.getPageParam().get("current")) && !StringUtils.isEmpty(queryVo.getPageParam().get("size"));
    }
}
