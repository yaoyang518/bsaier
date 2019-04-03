package com.yaoyang.bser.controller;


import com.yaoyang.bser.constants.SiteConstants;
import com.yaoyang.bser.entity.CityServer;
import com.yaoyang.bser.entity.User;
import com.yaoyang.bser.repository.*;
import com.yaoyang.bser.service.UserService;
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
 * 充电桩接口
 *
 * @author yaoyang
 * @date 2019-03-24
 */
@RestController
@RequestMapping(path = "/api/new/chargers")
public class NewChargerController {
    @Autowired
    private IndexRepository indexRepository;
    @Autowired
    private CityServerRepository cityServerRepository;
    @Autowired
    private ChargerRepository chargerRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/getChargeData")
    @ApiOperation(value = "昨日今日数据-前端")
    public JSONArray getChargeData(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        if (user == null) {
            JSONObject jsonY = new JSONObject();
            jsonY.put("day", "昨日");
            jsonY.put("power", 0);
            jsonY.put("money", 0);
            JSONObject jsonT = new JSONObject();
            jsonT.put("day", "今日");
            jsonT.put("power", 0);
            jsonT.put("money", 0);
            jsonArray.add(jsonY);
            jsonArray.add(jsonT);
        } else {
            if (user.getLevel() == 3) {
                BigDecimal rechargerAmountYesterday = chargerRepository.getRechargerAmountByDotIdAndDate(user.getDotid(), new DateTime().minusDays(1).toDate());
                BigDecimal rechargerAmountToday = chargerRepository.getRechargerAmountByDotIdAndDate(user.getDotid(), new Date());
                JSONObject jsonY = new JSONObject();
                jsonY.put("day", "昨日");
                jsonY.put("power", rechargerAmountYesterday == null ? 0 : rechargerAmountYesterday);
                jsonY.put("money", rechargerAmountYesterday == null ? 0 : rechargerAmountYesterday.multiply(SiteConstants.ELECTRICITY_PRICE));
                JSONObject jsonT = new JSONObject();
                jsonT.put("day", "今日");
                jsonT.put("power", rechargerAmountToday == null ? 0 : rechargerAmountToday);
                jsonT.put("money", rechargerAmountToday == null ? 0 : rechargerAmountToday.multiply(SiteConstants.ELECTRICITY_PRICE));
                jsonArray.add(jsonY);
                jsonArray.add(jsonT);
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                BigDecimal rechargerAmountYesterday = chargerRepository.getRechargerAmountByCityIdAndStidAndDate(cityServer.getCityID(), cityServer.getStid(), new DateTime().minusDays(1).toDate());
                BigDecimal rechargerAmountToday = chargerRepository.getRechargerAmountByCityIdAndStidAndDate(cityServer.getCityID(), cityServer.getStid(), new Date());
                JSONObject jsonY = new JSONObject();
                jsonY.put("day", "昨日");
                jsonY.put("power", rechargerAmountYesterday == null ? 0 : rechargerAmountYesterday);
                jsonY.put("money", rechargerAmountYesterday == null ? 0 : rechargerAmountYesterday.multiply(SiteConstants.ELECTRICITY_PRICE));
                JSONObject jsonT = new JSONObject();
                jsonT.put("day", "今日");
                jsonT.put("power", rechargerAmountToday == null ? 0 : rechargerAmountToday);
                jsonT.put("money", rechargerAmountToday == null ? 0 : rechargerAmountToday.multiply(SiteConstants.ELECTRICITY_PRICE));
                jsonArray.add(jsonY);
                jsonArray.add(jsonT);
            } else if (user.getLevel() == 1) {
                BigDecimal rechargerAmountYesterday = chargerRepository.getRechargerAmountByDateAndStid(new DateTime().minusDays(1).toDate(), user.getStid());
                BigDecimal rechargerAmountToday = chargerRepository.getRechargerAmountByDateAndStid(new Date(), user.getStid());
                JSONObject jsonY = new JSONObject();
                jsonY.put("day", "昨日");
                jsonY.put("power", rechargerAmountYesterday == null ? 0 : rechargerAmountYesterday);
                jsonY.put("money", rechargerAmountYesterday == null ? 0 : rechargerAmountYesterday.multiply(SiteConstants.ELECTRICITY_PRICE));
                JSONObject jsonT = new JSONObject();
                jsonT.put("day", "今日");
                jsonT.put("power", rechargerAmountToday == null ? 0 : rechargerAmountToday);
                jsonT.put("money", rechargerAmountToday == null ? 0 : rechargerAmountToday.multiply(SiteConstants.ELECTRICITY_PRICE));
                jsonArray.add(jsonY);
                jsonArray.add(jsonT);
            }
        }
        return jsonArray;
    }

    @GetMapping("/getRechargerRrequency")
    @ApiOperation(value = "使用频次-前端")
    public JSONArray getRechargerRrequency(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        if (user == null) {
            JSONObject json = new JSONObject();
            json.put("value", "0");
            jsonArray.add(json);
        } else {
            if (user.getLevel() == 3) {
                JSONObject json = new JSONObject();
                json.put("value", chargerRepository.getRechargerRrequencyByDotId(user.getDotid(), false));
                jsonArray.add(json);
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                JSONObject json = new JSONObject();
                json.put("value", chargerRepository.getRechargerRrequencyByCityIdAndStid(cityServer.getCityID(), cityServer.getStid(), false));
                jsonArray.add(json);
            } else if (user.getLevel() == 1) {
                JSONObject json = new JSONObject();
                json.put("value", chargerRepository.getRechargerRrequencyByStid(false, user.getStid()));
                jsonArray.add(json);
            }
        }
        return jsonArray;
    }

    @GetMapping("/getRechargerUser")
    @ApiOperation(value = "使用员工-前端")
    public JSONArray getRechargerUser(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        if (user == null) {
            JSONObject json = new JSONObject();
            json.put("value", "0");
            jsonArray.add(json);
        } else {
            if (user.getLevel() == 3) {
                JSONObject json = new JSONObject();
                json.put("value", chargerRepository.getRechargerRrequencyByDotId(user.getDotid(), true));
                jsonArray.add(json);
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                JSONObject json = new JSONObject();
                json.put("value", chargerRepository.getRechargerRrequencyByCityIdAndStid(cityServer.getCityID(), cityServer.getStid(), true));
                jsonArray.add(json);
            } else if (user.getLevel() == 1) {
                JSONObject json = new JSONObject();
                json.put("value", chargerRepository.getRechargerRrequencyByStid(true, user.getStid()));
                jsonArray.add(json);
            }
        }
        return jsonArray;
    }

    @GetMapping("/getRechargerCountByStatus")
    @ApiOperation(value = "使用空闲维修数据-前端")
    public JSONArray getRechargerCountByStatus(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        if (user == null) {
            JSONObject jsonU = new JSONObject();
            jsonU.put("type", "使用中");
            jsonU.put("value", 0);
            JSONObject jsonUn = new JSONObject();
            jsonUn.put("type", "空闲中");
            jsonUn.put("value", 0);
            JSONObject jsonR = new JSONObject();
            jsonR.put("type", "报修中");
            jsonR.put("value", 0);
            jsonArray.add(jsonU);
            jsonArray.add(jsonUn);
            jsonArray.add(jsonR);
        } else {
            if (user.getLevel() == 3) {
                JSONObject jsonU = new JSONObject();
                jsonU.put("type", "使用中");
                jsonU.put("value", chargerRepository.getRechargerCountByDotIdAndUsed(user.getDotid()));
                JSONObject jsonUn = new JSONObject();
                jsonUn.put("type", "空闲中");
                jsonUn.put("value",indexRepository.getDeviceCountByDtidAndDotId(SiteConstants.CHARGER_ID, user.getDotid()) -
                        chargerRepository.getRechargerCountByDotIdAndUsed(user.getDotid()) - chargerRepository.getRechargerCountByDotIdAndRepair(user.getDotid()));
                JSONObject jsonR = new JSONObject();
                jsonR.put("type", "报修中");
                jsonR.put("value", chargerRepository.getRechargerCountByDotIdAndRepair(user.getDotid()));
                jsonArray.add(jsonU);
                jsonArray.add(jsonUn);
                jsonArray.add(jsonR);
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                JSONObject jsonU = new JSONObject();
                jsonU.put("type", "使用中");
                jsonU.put("value", chargerRepository.getRechargerCountByCityIdAndStidAndUsed(cityServer.getCityID(), cityServer.getStid()));
                JSONObject jsonUn = new JSONObject();
                jsonUn.put("type", "空闲中");
                jsonUn.put("value", indexRepository.getDeviceCountByDtidAndCityIdAndStid(SiteConstants.CHARGER_ID, cityServer.getCityID(), cityServer.getStid()) -
                        chargerRepository.getRechargerCountByCityIdAndStidAndUsed(cityServer.getCityID(), cityServer.getStid()) - chargerRepository.getRechargerCountByCityIdAndStidAndRepair(cityServer.getCityID(), cityServer.getStid()));
                JSONObject jsonR = new JSONObject();
                jsonR.put("type", "报修中");
                jsonR.put("value", chargerRepository.getRechargerCountByCityIdAndStidAndRepair(cityServer.getCityID(), cityServer.getStid()));
                jsonArray.add(jsonU);
                jsonArray.add(jsonUn);
                jsonArray.add(jsonR);
            } else if (user.getLevel() == 1) {
                JSONObject jsonU = new JSONObject();
                jsonU.put("type", "使用中");
                jsonU.put("value", chargerRepository.getRechargerCountByUsedAndStid(user.getStid()));
                JSONObject jsonUn = new JSONObject();
                jsonUn.put("type", "空闲中");
                jsonUn.put("value", indexRepository.getDeviceCountByDtidAndStid(SiteConstants.CHARGER_ID, user.getStid()) -
                        chargerRepository.getRechargerCountByUsedAndStid(user.getStid()) - chargerRepository.getRechargerCountByRepairAndStid(user.getStid()));
                JSONObject jsonR = new JSONObject();
                jsonR.put("type", "报修中");
                jsonR.put("value", chargerRepository.getRechargerCountByRepairAndStid(user.getStid()));
                jsonArray.add(jsonU);
                jsonArray.add(jsonUn);
                jsonArray.add(jsonR);
            }
        }
        return jsonArray;
    }


}
