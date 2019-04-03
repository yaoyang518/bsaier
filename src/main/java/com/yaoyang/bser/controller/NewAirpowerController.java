package com.yaoyang.bser.controller;


import com.yaoyang.bser.constants.SiteConstants;
import com.yaoyang.bser.entity.CityServer;
import com.yaoyang.bser.entity.User;
import com.yaoyang.bser.repository.AirpowerRepository;
import com.yaoyang.bser.repository.AirpowerSortRepository;
import com.yaoyang.bser.repository.CityServerRepository;
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
import java.math.BigDecimal;
import java.util.Date;

/**
 * 空调插座接口
 *
 * @author yaoyang
 * @date 2019-03-24
 */
@RestController
@RequestMapping(path = "/api/new/airpowers")
public class NewAirpowerController {
    @Autowired
    private AirpowerRepository airpowerRepository;
    @Autowired
    private AirpowerSortRepository airpowerSortRepository;
    @Autowired
    private CityServerRepository cityServerRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/manageUse")
    @ApiOperation(value = "经理区使用情况-前端")
    public JSONArray manageUse(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            JSONObject jsonY = new JSONObject();
            jsonY.put("type", "使用中");
            jsonY.put("value", 0);
            JSONObject jsonT = new JSONObject();
            jsonT.put("type", "空闲中");
            jsonT.put("value", 0);
            jsonArray.add(jsonY);
            jsonArray.add(jsonT);
        } else {
            if (user.getLevel() == 3) {
                JSONObject jsonY = new JSONObject();
                jsonY.put("type", "使用中");
                jsonY.put("value", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndDotId(SiteConstants.MANAGER_TYPE, 1, user.getDotid()));
                JSONObject jsonT = new JSONObject();
                jsonT.put("type", "空闲中");
                jsonT.put("value", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndDotId(SiteConstants.MANAGER_TYPE, 0, user.getDotid()));
                jsonArray.add(jsonY);
                jsonArray.add(jsonT);
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                JSONObject jsonY = new JSONObject();
                jsonY.put("type", "使用中");
                jsonY.put("value", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndCityIdAndStid(SiteConstants.MANAGER_TYPE, 1, cityServer.getCityID(), cityServer.getStid()));
                JSONObject jsonT = new JSONObject();
                jsonT.put("type", "空闲中");
                jsonT.put("value", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndCityIdAndStid(SiteConstants.MANAGER_TYPE, 0, cityServer.getCityID(), cityServer.getStid()));
                jsonArray.add(jsonY);
                jsonArray.add(jsonT);
            } else if (user.getLevel() == 1) {
                JSONObject jsonY = new JSONObject();
                jsonY.put("type", "使用中");
                jsonY.put("value", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndStid(SiteConstants.MANAGER_TYPE, 1, user.getStid()));
                JSONObject jsonT = new JSONObject();
                jsonT.put("type", "空闲中");
                jsonT.put("value", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndStid(SiteConstants.MANAGER_TYPE, 0, user.getStid()));
                jsonArray.add(jsonY);
                jsonArray.add(jsonT);
            }
        }
        return jsonArray;
    }

    @GetMapping("/employeeUse")
    @ApiOperation(value = "员工区使用情况-前端")
    public JSONArray employeeUse(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            JSONObject jsonY = new JSONObject();
            jsonY.put("type", "使用中");
            jsonY.put("value", 0);
            JSONObject jsonT = new JSONObject();
            jsonT.put("type", "空闲中");
            jsonT.put("value", 0);
            jsonArray.add(jsonY);
            jsonArray.add(jsonT);
        } else {
            if (user.getLevel() == 3) {
                JSONObject jsonY = new JSONObject();
                jsonY.put("type", "使用中");
                jsonY.put("value", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndDotId(SiteConstants.EMPLOYEE_TYPE, 1, user.getDotid()));
                JSONObject jsonT = new JSONObject();
                jsonT.put("type", "空闲中");
                jsonT.put("value", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndDotId(SiteConstants.EMPLOYEE_TYPE, 0, user.getDotid()));
                jsonArray.add(jsonY);
                jsonArray.add(jsonT);
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                JSONObject jsonY = new JSONObject();
                jsonY.put("type", "使用中");
                jsonY.put("value", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndCityIdAndStid(SiteConstants.EMPLOYEE_TYPE, 1, cityServer.getCityID(), cityServer.getStid()));
                JSONObject jsonT = new JSONObject();
                jsonT.put("type", "空闲中");
                jsonT.put("value", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndCityIdAndStid(SiteConstants.EMPLOYEE_TYPE, 0, cityServer.getCityID(), cityServer.getStid()));
                jsonArray.add(jsonY);
                jsonArray.add(jsonT);
            } else if (user.getLevel() == 1) {
                JSONObject jsonY = new JSONObject();
                jsonY.put("type", "使用中");
                jsonY.put("value", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndStid(SiteConstants.EMPLOYEE_TYPE, 1, user.getStid()));
                JSONObject jsonT = new JSONObject();
                jsonT.put("type", "空闲中");
                jsonT.put("value", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndStid(SiteConstants.EMPLOYEE_TYPE, 0, user.getStid()));
                jsonArray.add(jsonY);
                jsonArray.add(jsonT);
            }
        }
        return jsonArray;
    }

    @GetMapping("/getAirpowerData")
    @ApiOperation(value = "区域数据-前端")
    public JSONArray getAmmeterData(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        if (user == null) {
            JSONObject jsonY = new JSONObject();
            jsonY.put("day", "经理办公区域");
            jsonY.put("power", 0);
            jsonY.put("money", 0);
            jsonY.put("case", 0);
            JSONObject jsonT = new JSONObject();
            jsonT.put("day", "员工办公区域");
            jsonT.put("power", 0);
            jsonT.put("money", 0);
            jsonT.put("case", 0);
            jsonArray.add(jsonY);
            jsonArray.add(jsonT);
        } else {
            if (user.getLevel() == 3) {
                JSONObject jsonY = new JSONObject();
                jsonY.put("day", "经理办公区域");
                jsonY.put("power", airpowerRepository.getRechargerAmountByTypeAndDotId(SiteConstants.MANAGER_TYPE, user.getDotid()));
                jsonY.put("money", airpowerRepository.getRechargerAmountByTypeAndDotId(SiteConstants.MANAGER_TYPE, user.getDotid()) == null ? 0 : airpowerRepository.getRechargerAmountByTypeAndDotId(SiteConstants.MANAGER_TYPE, user.getDotid()).multiply(SiteConstants.ELECTRICITY_PRICE));
                jsonY.put("case", airpowerRepository.getAirTimeCountByTypeAndDotId(SiteConstants.MANAGER_TYPE, user.getDotid()));
                JSONObject jsonT = new JSONObject();
                jsonT.put("day", "员工办公区域");
                jsonT.put("power", airpowerRepository.getRechargerAmountByTypeAndDotId(SiteConstants.EMPLOYEE_TYPE, user.getDotid()));
                jsonT.put("money", airpowerRepository.getRechargerAmountByTypeAndDotId(SiteConstants.EMPLOYEE_TYPE, user.getDotid()) == null ? 0 : airpowerRepository.getRechargerAmountByTypeAndDotId(SiteConstants.EMPLOYEE_TYPE, user.getDotid()).multiply(SiteConstants.ELECTRICITY_PRICE));
                jsonT.put("case", airpowerRepository.getAirTimeCountByTypeAndDotId(SiteConstants.EMPLOYEE_TYPE, user.getDotid()));
                jsonArray.add(jsonY);
                jsonArray.add(jsonT);
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                JSONObject jsonY = new JSONObject();
                jsonY.put("day", "经理办公区域");
                jsonY.put("power", airpowerRepository.getRechargerAmountByTypeAndCityIdAndStid(SiteConstants.MANAGER_TYPE, cityServer.getCityID(), cityServer.getStid()));
                jsonY.put("money", airpowerRepository.getRechargerAmountByTypeAndCityIdAndStid(SiteConstants.MANAGER_TYPE, cityServer.getCityID(), cityServer.getStid()) == null ? 0 : airpowerRepository.getRechargerAmountByTypeAndCityIdAndStid(SiteConstants.MANAGER_TYPE, cityServer.getCityID(), cityServer.getStid()).multiply(SiteConstants.ELECTRICITY_PRICE));
                jsonY.put("case", airpowerRepository.getAirTimeCountByTypeAndCityIdAndStid(SiteConstants.MANAGER_TYPE, cityServer.getCityID(), cityServer.getStid()));
                JSONObject jsonT = new JSONObject();
                jsonT.put("day", "员工办公区域");
                jsonT.put("power", airpowerRepository.getRechargerAmountByTypeAndCityIdAndStid(SiteConstants.EMPLOYEE_TYPE, cityServer.getCityID(), cityServer.getStid()));
                jsonT.put("money", airpowerRepository.getRechargerAmountByTypeAndCityIdAndStid(SiteConstants.EMPLOYEE_TYPE, cityServer.getCityID(), cityServer.getStid()) == null ? 0 : airpowerRepository.getRechargerAmountByTypeAndCityIdAndStid(SiteConstants.EMPLOYEE_TYPE, cityServer.getCityID(), cityServer.getStid()).multiply(SiteConstants.ELECTRICITY_PRICE));
                jsonT.put("case", airpowerRepository.getAirTimeCountByTypeAndCityIdAndStid(SiteConstants.EMPLOYEE_TYPE, cityServer.getCityID(), cityServer.getStid()));
                jsonArray.add(jsonY);
                jsonArray.add(jsonT);
            } else if (user.getLevel() == 1) {
                JSONObject jsonY = new JSONObject();
                jsonY.put("day", "经理办公区域");
                jsonY.put("power", airpowerRepository.getRechargerAmountByTypeAndStid(SiteConstants.MANAGER_TYPE, user.getStid()));
                jsonY.put("money", airpowerRepository.getRechargerAmountByTypeAndStid(SiteConstants.MANAGER_TYPE, user.getStid()) == null ? 0 : airpowerRepository.getRechargerAmountByTypeAndStid(SiteConstants.MANAGER_TYPE, user.getStid()).multiply(SiteConstants.ELECTRICITY_PRICE));
                jsonY.put("case", airpowerRepository.getAirTimeCountByTypeAndStid(SiteConstants.MANAGER_TYPE, user.getStid()));
                JSONObject jsonT = new JSONObject();
                jsonT.put("day", "员工办公区域");
                jsonT.put("power", airpowerRepository.getRechargerAmountByTypeAndStid(SiteConstants.EMPLOYEE_TYPE, user.getStid()));
                jsonT.put("money", airpowerRepository.getRechargerAmountByTypeAndStid(SiteConstants.EMPLOYEE_TYPE, user.getStid()) == null ? 0 : airpowerRepository.getRechargerAmountByTypeAndStid(SiteConstants.EMPLOYEE_TYPE, user.getStid()).multiply(SiteConstants.ELECTRICITY_PRICE));
                jsonT.put("case", airpowerRepository.getAirTimeCountByTypeAndStid(SiteConstants.EMPLOYEE_TYPE, user.getStid()));
                jsonArray.add(jsonY);
                jsonArray.add(jsonT);
            }
        }
        return jsonArray;
    }


}
