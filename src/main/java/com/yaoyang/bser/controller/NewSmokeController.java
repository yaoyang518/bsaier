package com.yaoyang.bser.controller;


import com.yaoyang.bser.entity.CityServer;
import com.yaoyang.bser.entity.User;
import com.yaoyang.bser.repository.CityServerRepository;
import com.yaoyang.bser.repository.SomkeRepository;
import com.yaoyang.bser.repository.SomkeSortRepository;
import com.yaoyang.bser.service.UserService;
import com.yaoyang.bser.util.DateUtil;
import com.yaoyang.bser.util.JSONArrayUtil;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 智能烟感接口
 *
 * @author yaoyang
 * @date 2019-03-24
 */
@RestController
@RequestMapping(path = "/api/new/smokes")
public class NewSmokeController {
    @Autowired
    private SomkeRepository somkeRepository;
    @Autowired
    private SomkeSortRepository somkeSortRepository;
    @Autowired
    private CityServerRepository cityServerRepository;
    @Autowired
    private UserService userService;


}
