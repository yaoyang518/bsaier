package com.yaoyang.bser.service;

import com.yaoyang.bser.entity.User;

/**
 * 用户服务
 *
 * @author yaoyang
 * @date 2018-08-10
 */
public interface UserService {

    User findById(Long id);

    User findByMobileAndPassword(String loginName, String password);

}
