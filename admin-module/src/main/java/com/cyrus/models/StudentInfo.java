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
    @TableId(value = "uid", type = IdType.ASSIGN_UUID)
    private String uid;

    @ApiModelProperty(value = "学号")
    private String stuNumber;

    @ApiModelProperty(value = "姓名")
    private String stuName;

    @ApiModelProperty(value = "所属系部")
    private String department;

    @ApiModelProperty(value = "所学专业")
    private String major;

    @ApiModelProperty(value = "所在班级")
    private String classId;

    @ApiModelProperty(value = "学籍编号")
    private String enrollmentNumber;

    @ApiModelProperty(value = "备注")
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
