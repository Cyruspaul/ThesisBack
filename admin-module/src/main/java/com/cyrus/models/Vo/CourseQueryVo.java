package com.cyrus.models.Vo;


import com.cyrus.config.PageQueryVo;
import lombok.Data;

import java.util.Map;

@Data
public class CourseQueryVo extends PageQueryVo {
    private Map<String, String> param;
    private String courseNo;
    private String courseName;
    private String courseAttr;
    private String courseCollege;

}
