package com.yaoyang.bser.controller;


import com.yaoyang.bser.base.ApiResult;
import com.yaoyang.bser.entity.CityServer;
import com.yaoyang.bser.entity.User;
import com.yaoyang.bser.enumeration.ResponseCode;
import com.yaoyang.bser.repository.CityServerRepository;
import com.yaoyang.bser.repository.DotServerRepository;
import com.yaoyang.bser.service.UserService;
import com.yaoyang.bser.util.ApiResultBuilder;
import com.yaoyang.bser.util.JSONArrayUtil;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户接口
 *
 * @author yaoyang
 * @date 2018-01-26
 */
@RestController
@RequestMapping(path = "/api/maps")
public class MapController {

    @Autowired
    private DotServerRepository dotServerRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CityServerRepository cityServerRepository;

    @GetMapping("/findDotByStid")
    @ApiOperation(value = "通过服务商找网点-前端")
    public List findDotByStid(HttpServletRequest request) {
        User user = userService.checkUser(request);
        List list = new ArrayList();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("lat", 0);
            jsonObject.put("lng", 0);
            jsonObject.put("type", "ok");
        } else {
            if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                return dotServerRepository.findDotByStidAndCityId(cityServer.getStid(), cityServer.getCityID());
            } else if (user.getLevel() == 1) {
                if (user.getStid() != null) {
                    return dotServerRepository.findDotByStid(user.getStid());
                }
            } else {
                jsonObject.put("lat", 0);
                jsonObject.put("lng", 0);
                jsonObject.put("type", "ok");
            }
        }
        list.add(jsonObject);
        return list;
    }

    @GetMapping("/findCityCode")
    @ApiOperation(value = "查找城市code-前端")
    public List findCityCode(HttpServletRequest request) {
        User user = userService.checkUser(request);
        List list = new ArrayList();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("adcode", 0);
        } else {
            if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                jsonObject.put("adcode", cityServer.getCityID());
            } else {
                jsonObject.put("adcode", 0);
            }
        }
        list.add(jsonObject);
        return list;
    }

}
