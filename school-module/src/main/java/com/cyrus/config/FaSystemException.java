package com.cyrus.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Self Defined Fa system exception.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaSystemException extends RuntimeException {
    private Integer code; //状态吗
    private String msg; //异常信息
}
