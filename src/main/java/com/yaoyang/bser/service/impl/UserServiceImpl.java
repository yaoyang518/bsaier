package com.yaoyang.bser.service.impl;


import com.yaoyang.bser.entity.User;
import com.yaoyang.bser.repository.UserRepository;
import com.yaoyang.bser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 用户服务
 *
 * @author yaoyang
 * @date 2018-08-10
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.isPresent() ? user.get() : null;
    }

    @Override
    public User findByMobileAndPassword(String loginName, String password) {
        return userRepository.findByMobileAndPassword(loginName, password);
    }




}
