package com.cyrus.models.Vo;


import com.cyrus.config.PageQueryVo;
import lombok.Data;

import java.util.Map;

@Data
public class CollegeQueryVo extends PageQueryVo {
    private Map<String, String> param;
    private String collegeNo;
    private String collegeName;
}
