package com.yaoyang.bser.controller;


import com.yaoyang.bser.base.ApiResult;
import com.yaoyang.bser.constants.SiteConstants;
import com.yaoyang.bser.entity.User;
import com.yaoyang.bser.enumeration.ResponseCode;
import com.yaoyang.bser.service.UserService;
import com.yaoyang.bser.util.ApiResultBuilder;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    @GetMapping("/login")
    @ApiOperation(value = "登陆-前端", notes = "level:1是超管，2是市管，3是网管")
    public ApiResult login(HttpServletRequest request, @RequestParam(value = "loginName") String loginName,
                           @RequestParam(value = "password") String password) {
        User user = userService.findByUsernameAndPassword(loginName, password);
        if (user == null) {
            return ApiResultBuilder.buildFailedResult(ResponseCode.PASSWORD_ERROR, "账号或密码错误", null);
        }
        return ApiResultBuilder.buildSuccessResult(ResponseCode.OPT_SUCCESS, user);
    }

}
