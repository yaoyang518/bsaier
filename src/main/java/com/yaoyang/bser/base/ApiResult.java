package com.yaoyang.bser.base;

import com.yaoyang.bser.util.JsonUtil;

/**
 * 接口数据实体类
 *
 * @param <T>
 * @author yaoyang
 * @date 2018-01-24
 */

public class ApiResult<T> {

    // 是否成功
    private boolean success;

    // 消息
    private String message;

    // 消息代码
    private String code;

    // 数据
    private T data;

    private Paging paging;

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }
}
