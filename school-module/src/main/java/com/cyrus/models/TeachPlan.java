package com.cyrus.models;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TeachPlan {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("courseNo")
    private String courseNo;

    @TableField("majorNo")
    private String majorNo;

    @TableField("teacher")
    private String teacher;

    @TableField("studyLevel")
    private String studyLevel;

    @TableField("semester")
    private String semester;

    @TableField("courseAttr")
    private String courseAttr;

    @TableField("classHour")
    private Integer classHour;

    @TableField("credit")
    private double credit;


    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}