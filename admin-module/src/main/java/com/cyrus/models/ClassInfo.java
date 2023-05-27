package com.cyrus.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "class_info")
public class ClassInfo {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField("classNo")
    private String classno;

    @TableField("className")
    private String classname;

    @TableField("studentNumber")
    private int stunum;

    @TableField("studyLevel")
    private int studylevel;

    @TableField("timetable")
    private String timetable;

    @TableField("majorId")
    private String majorid;

    @TableField("nianji")
    private String nianji;

}