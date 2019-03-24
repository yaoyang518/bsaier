package com.yaoyang.bser.controller;


import com.yaoyang.bser.base.ApiResult;
import com.yaoyang.bser.constants.SiteConstants;
import com.yaoyang.bser.entity.CityServer;
import com.yaoyang.bser.entity.User;
import com.yaoyang.bser.enumeration.ResponseCode;
import com.yaoyang.bser.repository.CityServerRepository;
import com.yaoyang.bser.repository.IndexRepository;
import com.yaoyang.bser.service.UserService;
import com.yaoyang.bser.util.ApiResultBuilder;
import com.yaoyang.bser.util.CookieUtil;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 首页接口
 *
 * @author yaoyang
 * @date 2019-03-23
 */
@RestController
@RequestMapping(path = "/api/indexs")
public class IndexController {
    @Autowired
    private IndexRepository indexRepository;
    @Autowired
    private CityServerRepository cityServerRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/totalDeviceCount")
    @ApiOperation(value = "总设备-前端")
    public JSONArray totalDeviceCount(HttpServletRequest request) {
        return getJsonArray(request, null);
    }

    @GetMapping("/totalAirpowerCount")
    @ApiOperation(value = "总智能插座数-前端")
    public JSONArray totalAirpowerCount(HttpServletRequest request) {
        return getJsonArray(request, SiteConstants.AIRPOWER_ID);
    }

    @GetMapping("/totalAmmeterCount")
    @ApiOperation(value = "总智能电表数-前端")
    public JSONArray totalAmmeterCount(HttpServletRequest request) {
        return getJsonArray(request, SiteConstants.AMMETER_ID);
    }

    @GetMapping("/totalSmokeCount")
    @ApiOperation(value = "总智能烟感数-前端")
    public JSONArray totalSmokeCount(HttpServletRequest request) {
        return getJsonArray(request, SiteConstants.SOMKE_ID);
    }

    @GetMapping("/totalChargerCount")
    @ApiOperation(value = "总智能充电桩数-前端")
    public JSONArray totalChargerCount(HttpServletRequest request) {
        return getJsonArray(request, SiteConstants.CHARGER_ID);
    }

    private JSONArray getJsonArray(HttpServletRequest request, Long dtId) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("value", 0);
        }
        if (user.getLevel() == 3) {
            jsonObject.put("value", indexRepository.getDeviceCountByDtidAndDotId(dtId, user.getDotid()));
        } else if (user.getLevel() == 2) {
            CityServer cityServer = cityServerRepository.findByCid(user.getCid());
            jsonObject.put("value", indexRepository.getDeviceCountByDtidAndCityIdAndStid(dtId, cityServer.getCityID(), cityServer.getStid()));
        } else if (user.getLevel() == 1) {
            jsonObject.put("value", indexRepository.getDeviceCountByDtid(dtId));
        } else {
            jsonObject.put("value", 0);
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

}
