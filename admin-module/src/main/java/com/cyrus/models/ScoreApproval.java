package com.cyrus.models;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "score_approval")
public class ScoreApproval implements Serializable {

    @TableId(type = IdType.ASSIGN_ID, value = "score_id")
    private String id;
    @TableField(value = "class_id")
    private String classid;

    @TableField(value = "course_id")
    private String course;

    @TableField(value = "tea_number")
    private String teacher;

    @TableField(value = "course_attr")
    private String courseattr;

    @TableField(value = "approval_by")
    private String approvedby;

    @TableField(value = "file")
    private String file;

    private String remark;
    private String semester;


    private int status;

    @ApiModelProperty(value = "逻辑删除，1表示已删除，0表示未删除")
    @TableLogic
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private Boolean isDeleted;


    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate = Date.valueOf(String.valueOf(LocalDateTime.now()));

    @ApiModelProperty(value = "更新时间")
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified = Date.valueOf(String.valueOf(LocalDateTime.now()));


}
