package com.cyrus.models.Vo;


import com.cyrus.config.PageQueryVo;
import lombok.Data;

import java.util.Map;

@Data
public class StudentQueryVo extends PageQueryVo {
    private Map<String, String> param;
    private String stu_number;//教师编号
    private String stu_name;
    private String department;
    private String major;
    private String class_id;
}
