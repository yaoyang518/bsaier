package com.yaoyang.bser.util;

import com.yaoyang.bser.base.ApiResult;
import com.yaoyang.bser.base.Paging;
import com.yaoyang.bser.enumeration.ResponseCode;

/**
 * 数据接口返回信息构建
 *
 * @author yaoyang
 * @date 2018-01-24
 */
public class ApiResultBuilder {


    public static <T> ApiResult<T> buildSuccessResult(ResponseCode code) {
        return buildSuccessResult(code, null);
    }

    public static <T> ApiResult<T> buildSuccessResult(ResponseCode code, T t) {
        ApiResult<T> result = new ApiResult<T>();
        result.setSuccess(true);
        result.setCode(code.getCode());
        result.setMessage(code.getMessage());
        if (t != null) {
            result.setData(t);
        }
        return result;
    }

    public static <T> ApiResult<T> buildFailedResult(ResponseCode code) {
        return buildFailedResult(code, null, null);
    }

    public static <T> ApiResult<T> buildFailedResult(ResponseCode code, T t, Paging paging) {
        ApiResult<T> result = new ApiResult<T>();
        result.setSuccess(false);
        result.setCode(code.getCode());
        result.setMessage(code.getMessage());
        if (t != null) {
            result.setData(t);
        }
        if (paging != null) {
            result.setPaging(paging);
        }
        return result;
    }
}
