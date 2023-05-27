package com.cyrus.models.Vo;


import com.cyrus.config.PageQueryVo;
import lombok.Data;

import java.util.Map;

@Data
public class TeachBuildQueryVo extends PageQueryVo {
    private Map<String, String> param;
    private String teachBuildNo;
    private String teachBuildName;
}
