package com.cyrus.models.Vo;


import com.cyrus.config.PageQueryVo;
import lombok.Data;

import java.util.Map;

@Data
public class TeacherQueryVo extends PageQueryVo {
    private Map<String, String> param;
    private String teaNumber;//教师编号
    private String teaName;
    private String colleageNo;
    private String title;
}
