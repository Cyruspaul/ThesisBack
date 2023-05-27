package com.cyrus.common.config;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MinUnit_Constants {
    public static final int ERROR_CODE = 4000;
    public static final int SUCCESS_CODE = 2000;
    public static final int INFO_CODE = 1000;
    public static final int WARNING_CODE = 3000;
    public static final Date LOCALDATE = Date.valueOf(LocalDate.now().toString());
    public static final LocalDateTime LOCALDATETIME = LocalDateTime.now();


}