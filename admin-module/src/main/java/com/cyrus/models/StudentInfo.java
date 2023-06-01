package com.cyrus.models;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("student_info")
public class StudentInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "uid", type = IdType.ASSIGN_ID)
    private String uid;

    private String stuNumber;

    private String stuName;

    private String stuEnName;


    private String birthday;


    private String country;

    private String phone;

    private String email;

    private String major;

    private String classId;
    private String passport;

    private String enrollmentNumber;

    private String remarks;

    @ApiModelProperty(value = "逻辑删除，1表示已删除，0表示未删除")
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
