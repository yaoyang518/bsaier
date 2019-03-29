package com.yaoyang.bser.service.impl;


import com.yaoyang.bser.entity.User;
import com.yaoyang.bser.repository.UserRepository;
import com.yaoyang.bser.service.UserService;
import com.yaoyang.bser.util.CookieUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    public User findByUsernameAndPassword(String loginName, String password) {
        return userRepository.findByUsernameAndPassword(loginName, password);
    }

    @Override
    public User checkUser(HttpServletRequest request) {
        JSONObject json = CookieUtil.cookieToJson(request);
        Long userid = json.getLong("userid");
        if (userid == null) {
            return null;
        }
        User user = findById(userid);
        return user;
    }


}
