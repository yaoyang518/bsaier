package com.yaoyang.bser.controller;


import com.yaoyang.bser.base.ApiResult;
import com.yaoyang.bser.constants.SiteConstants;
import com.yaoyang.bser.entity.User;
import com.yaoyang.bser.enumeration.ResponseCode;
import com.yaoyang.bser.service.UserService;
import com.yaoyang.bser.util.ApiResultBuilder;
import com.yaoyang.bser.util.CommonUtil;
import com.yaoyang.bser.util.Md5Util;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.ApiOperation;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户接口
 *
 * @author yaoyang
 * @date 2018-01-26
 */
@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "登陆-前端",notes = "level:1是超管，2是市管，3是网管")
    public ApiResult login(HttpServletRequest request, @RequestParam(value = "loginName") String loginName,
                           @RequestParam(value = "password") String password) {
        if (!CommonUtil.isRightPhone(loginName)) {
            return ApiResultBuilder.buildFailedResult(ResponseCode.MOBILE_ILLEGAL);
        }
        User user = userService.findByMobileAndPassword(loginName, password);
        if (user == null) {
            return ApiResultBuilder.buildFailedResult(ResponseCode.PASSWORD_ERROR, "账号或密码错误", null);
        }
        return ApiResultBuilder.buildSuccessResult(ResponseCode.OPT_SUCCESS, user);
    }

}
