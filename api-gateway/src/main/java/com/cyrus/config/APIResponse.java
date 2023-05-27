package com.cyrus.config;

import lombok.Data;

@Data
public class APIResponse<T> {

    private String message;
    private int code;
    private T data;

    public static APIResponse<?> error(Object o) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.message = "Error";
        apiResponse.code = MinUnit_Constants.ERROR_CODE;
        apiResponse.data = o;
        return apiResponse;
    }

    public static APIResponse<?> error() {
        APIResponse apiResponse = new APIResponse();
        apiResponse.message = "Error";
        apiResponse.code = MinUnit_Constants.ERROR_CODE;
        apiResponse.data = null;
        return apiResponse;
    }

    public static APIResponse<?> warning(Object o) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.message = "Warning";
        apiResponse.code = MinUnit_Constants.WARNING_CODE;
        apiResponse.data = o;
        return apiResponse;
    }

    public static APIResponse<?> warning() {
        APIResponse apiResponse = new APIResponse();
        apiResponse.message = "Warning";
        apiResponse.code = MinUnit_Constants.WARNING_CODE;
        apiResponse.data = null;
        return apiResponse;
    }

    public static APIResponse<?> success(Object o) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.message = "Success";
        apiResponse.code = MinUnit_Constants.SUCCESS_CODE;
        apiResponse.data = o;
        return apiResponse;
    }

    public static APIResponse<?> success() {
        APIResponse apiResponse = new APIResponse();
        apiResponse.message = "Success";
        apiResponse.code = MinUnit_Constants.SUCCESS_CODE;
        apiResponse.data = null;
        return apiResponse;
    }

    public APIResponse<?> message(String message) {
        this.message = message;
        return this;
    }

    public APIResponse<?> code(int code) {
        this.code = code;
        return this;
    }
}