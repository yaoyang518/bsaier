package com.yaoyang.bser.controller;


import com.yaoyang.bser.entity.CityServer;
import com.yaoyang.bser.entity.User;
import com.yaoyang.bser.repository.CityServerRepository;
import com.yaoyang.bser.repository.IndexRepository;
import com.yaoyang.bser.service.UserService;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 电表接口
 *
 * @author yaoyang
 * @date 2019-03-24
 */
@RestController
@RequestMapping(path = "/api/ammeters")
public class AmmeterController {
    @Autowired
    private IndexRepository indexRepository;
    @Autowired
    private CityServerRepository cityServerRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/totalDeviceCount")
    @ApiOperation(value = "总设备-前端")
    public JSONArray totalDeviceCount(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("value", 0);
        }
        if (user.getLevel() == 3) {
            jsonObject.put("value", indexRepository.getDeviceCountByDtidAndDotId(null, user.getDotid()));
        } else if (user.getLevel() == 2) {
            CityServer cityServer = cityServerRepository.findByCid(user.getCid());
            jsonObject.put("value", indexRepository.getDeviceCountByDtidAndCityIdAndStid(null, cityServer.getCityID(), cityServer.getStid()));
        } else if (user.getLevel() == 1) {
            jsonObject.put("value", indexRepository.getDeviceCountByDtid(null));
        } else {
            jsonObject.put("value", 0);
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

}
