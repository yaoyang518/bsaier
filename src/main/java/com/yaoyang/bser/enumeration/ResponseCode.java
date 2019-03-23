package com.yaoyang.bser.enumeration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 系统响应码
 *
 * @author yaoyang
 * @date 2018-01-24
 */
public enum ResponseCode {

    //0 通用
    QUERY_SUCCESS("0001", "查询成功"),
    OPT_SUCCESS("0002", "操作成功"),

    QUERY_FAIL("0003", "查询失败"),
    OPT_FAIL("0004", "操作失败"),
    UPLOAD_FAIL("0005", "上传失败"),

    TOKEN_NULL("0006", "令牌为空"),
    TOKEN_ERROR("0007", "令牌错误"),

    NOT_LOGIN("0008", "请先登录"),
    LOGIN_EXPIRE("0009", "请重新登录"),
    REQUEST_ILLEGAL("0010", "非法请求"),


    //1 已存在
    TOKEN__EXIST("1001", "代币已存在"),
    WALLET_NAME_EXIST("1002", "钱包名已存在"),
    ROLE_EXIST("1003", "角色已存在"),
    ADMIN_EXIST("1004", "管理员已存在"),
    PERMISSION_EXIST("1005", "权限名已存在"),
    MOBILE_EXIST("1006", "手机号码已存在"),

    //2 不存在
    NO_PAYOUT_RECORD("2002", "提现记录不存在"),
    NO_ADMIN("2001", "管理员不存在"),
    NO_ROLE("2003", "角色不存在"),
    NO_PERMISSION("2004", "权限不存在"),
    NO_MOBILE("2007", "手机号码不存在"),
    NO_MONEY_ACCOUNT("2008", "提现账号不存在"),
    NO_USER("2009", "用户不存在"),

    //3 无法操作
    PERMISSION_DENY("3001", "权限不足"),
    UNABLE_LOGIN("3002", "无法登录"),
    UNABLE_CREATE("3003", "无法创建"),
    UNABLE_EDIT("3004", "无法修改"),
    UNABLE_DELETE("3005", "无法删除"),
    USER_DISABLE("3006", "用户被禁用"),


    //4 参数
    PARAM_ILLEGAL("4001", "非法参数"),
    LOGIN_NAME_NULL("4002", "登录名为空"),
    PASSWORD_NULL("4003", "请输入登录密码"),
    WALLET_PASSWORD_NULL("4004", "请输入密码"),
    PASSWORD_ILLEGAL("4005", "密码长度不能少于8位，必须是数字加字母"),
    WALLET_REPEAT_PASSWORD_ERROR("4006", "请输入重复密码"),
    REPEAT_PASSWORD_NO_EQUAL("4007", "两次输入密码不一致，请重新输入"),
    MOBILE_ILLEGAL("4008", "请输入正确的手机号码"),
    ACCOUNT_ABNORMAL("409", "您的账号出现异常，暂时不能登录，稍后再试。"),
    PASSWORD_ERROR("4010", "密码输入错误，请重新输入"),
    MOBILE_NULL("4011", "请输入手机号码"),
    IMG_CODE_ERROR("4012", "图片验证码错误"),
    IMG_CODE_EXPIRE("4013", "图片验证码过期"),
    SMS_CODE_ERROR("4014", "短信验证码错误"),
    SMS_CODE_EXPIRE("4015", "短信验证码过期"),
    SMS_CODE_EXIST("4016", "验证码已发送，稍后再试。"),
    PROMOTE_AWARD_LESS("4017", "推广金不足"),
    ADVERT_AWARD_LESS("4018", "广告分红不足"),
    MANAGE_AWARD_LESS("4019", "管理奖不足"),
    PAYOUT_LESS("4020", "小于最低提现金额"),
    NOT_EQUAL("4021", "设备价格不符"),
    NOT_REMAINDER("4022", "不是整数倍");

    private String code;
    private String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Map<String, ResponseCode> getCodeMap() {
        Map<String, ResponseCode> result = new LinkedHashMap<>();
        for (ResponseCode commonCode : ResponseCode.values()) {
            result.put(commonCode.getCode() + "", commonCode);
        }
        return result;
    }

    public static Map<String, ResponseCode> getMessageMap() {
        Map<String, ResponseCode> result = new LinkedHashMap<>();
        for (ResponseCode commonCode : ResponseCode.values()) {
            result.put(commonCode.getMessage() + "", commonCode);
        }
        return result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
