package com.yaoyang.bser.service;

import com.yaoyang.bser.entity.User;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 *
 * @author yaoyang
 * @date 2019-03-23
 */
public interface UserService {

    User findById(Long id);

    User findByUsernameAndPassword(String loginName, String password);

    User checkUser(HttpServletRequest request);

}
