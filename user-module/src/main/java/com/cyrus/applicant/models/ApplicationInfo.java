package com.cyrus.applicant.models;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("student_register_table")
public class ApplicationInfo {

    @TableId(type = IdType.ASSIGN_ID, value = "student_registration_number")
    private String regNum;

    @TableField(value = "student_passport")
    private String passport;

    @TableField(value = "student_email")
    private String email;

    @TableField(value = "student_degree")
    private int degree;

    @TableField(value = "student_age")
    private int age;

    @TableField(value = "student_en_name")
    private String name;

    @TableField(value = "student_birthday")
    private String birthday;

    @TableField(value = "student_phone")
    private String phone;

    @TableField(value = "student_major")
    private String major;

    @TableField(value = "student_country")
    private String country;

    @TableField(value = "application_status")
    private int status;

    private int isDisabled;

    @ApiModelProperty(value = "逻辑删除，1表示已删除，0表示未删除")
    @TableLogic
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private Boolean isDeleted;


    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}