package com.cyrus.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Self Defined Fa system exception.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FaSystemException extends RuntimeException {
    private Integer code; //状态吗
    private String msg; //异常信息

    public FaSystemException(int code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }
}
