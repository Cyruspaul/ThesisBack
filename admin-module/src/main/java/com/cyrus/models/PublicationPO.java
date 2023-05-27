package com.cyrus.models;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("publication_table")
public class PublicationPO implements Serializable {

    @TableId(value = "pid", type = IdType.ASSIGN_ID)
    private String anid;

    @TableField(value = "ptitle")
    private String atitle;

    @TableField(value = "pdate")
    private Date adate;

    @TableField(value = "phost")
    private String ahost;

    @TableField(value = "ptopic")
    private String atopic;


    @TableField(value = "pcontent")
    private String acontent;

    @TableField(value = "pviews")
    private int aviews;

    @TableField(value = "plocation")
    private String alocation;

    @TableField(value = "pauthor")
    private String author;

    @TableField(value = "pdoclink")
    private String alink;


    @ApiModelProperty(value = "逻辑删除，1表示已删除，0表示未删除")
    @TableLogic
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private Boolean isDeleted;


    @TableField(value = "pcomment_boolean")
    private boolean allowComment;


    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
