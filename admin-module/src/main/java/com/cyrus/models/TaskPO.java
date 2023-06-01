package com.cyrus.models;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tasktable")
public class TaskPO implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String senderid;
    private String sendername;
    private String request;
    private String requestvalue;
    private int status;

    @TableLogic
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private Boolean isDeleted;


    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @JsonIgnore
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}
