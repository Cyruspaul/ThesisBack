package com.cyrus.models.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ucenter_member", schema = "fasystem_main")
//@Access(value= AccessType.FIELD)
//@MappedSuperclass
public class UserModelPO {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField(value = "account")
    private String username;

    @TableField
    private String password;

//    @TableField
//    private String email;

    @TableField
    private String role;

    @TableField(fill = FieldFill.INSERT)
    private Integer isDisabled;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
