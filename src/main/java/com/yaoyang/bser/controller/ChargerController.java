package com.yaoyang.bser.controller;


import com.yaoyang.bser.constants.SiteConstants;
import com.yaoyang.bser.entity.CityServer;
import com.yaoyang.bser.entity.User;
import com.yaoyang.bser.repository.ChargerRepository;
import com.yaoyang.bser.repository.CityServerRepository;
import com.yaoyang.bser.repository.IndexRepository;
import com.yaoyang.bser.service.UserService;
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
@RequestMapping(path = "/api/chargers")
public class ChargerController {
    @Autowired
    private IndexRepository indexRepository;
    @Autowired
    private CityServerRepository cityServerRepository;
    @Autowired
    private ChargerRepository chargerRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/totalDeviceChildCount")
    @ApiOperation(value = "线路-前端")
    public JSONArray totalDeviceChildCount(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("value", 0);
        } else {
            if (user.getLevel() == 3) {
                jsonObject.put("value", chargerRepository.getDeviceChildCountByDtidAndDotId(SiteConstants.CHARGER_ID, user.getDotid()));
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                jsonObject.put("value", chargerRepository.getDeviceChildCountByDtidAndCityIdAndStid(SiteConstants.CHARGER_ID, cityServer.getCityID(), cityServer.getStid()));
            } else if (user.getLevel() == 1) {
                jsonObject.put("value", chargerRepository.getDeviceChildCountByDtid(SiteConstants.CHARGER_ID));
            } else {
                jsonObject.put("value", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    @GetMapping("/getRechargerCountByUsed")
    @ApiOperation(value = "使用中-前端")
    public JSONArray getRechargerCountByUsed(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("value", 0);
        } else {
            if (user.getLevel() == 3) {
                jsonObject.put("value", chargerRepository.getRechargerCountByDotIdAndUsed(user.getDotid()));
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                jsonObject.put("value", chargerRepository.getRechargerCountByCityIdAndStidAndUsed(cityServer.getCityID(), cityServer.getStid()));
            } else if (user.getLevel() == 1) {
                jsonObject.put("value", chargerRepository.getRechargerCountByUsed());
            } else {
                jsonObject.put("value", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    @GetMapping("/getRechargerCountByRepair")
    @ApiOperation(value = "报修中-前端")
    public JSONArray getRechargerCountByRepair(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("value", 0);
        } else {
            if (user.getLevel() == 3) {
                jsonObject.put("value", chargerRepository.getRechargerCountByDotIdAndRepair(user.getDotid()));
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                jsonObject.put("value", chargerRepository.getRechargerCountByCityIdAndStidAndRepair(cityServer.getCityID(), cityServer.getStid()));
            } else if (user.getLevel() == 1) {
                jsonObject.put("value", chargerRepository.getRechargerCountByRepair());
            } else {
                jsonObject.put("value", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    @GetMapping("/getRechargerCountByUnused")
    @ApiOperation(value = "空闲中-前端")
    public JSONArray getRechargerCountByUnused(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("value", 0);
        } else {
            if (user.getLevel() == 3) {
                jsonObject.put("value", indexRepository.getDeviceCountByDtidAndDotId(SiteConstants.CHARGER_ID, user.getDotid()) -
                        chargerRepository.getRechargerCountByDotIdAndUsed(user.getDotid()) - chargerRepository.getRechargerCountByDotIdAndRepair(user.getDotid()));
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                jsonObject.put("value", indexRepository.getDeviceCountByDtidAndCityIdAndStid(SiteConstants.CHARGER_ID, cityServer.getCityID(), cityServer.getStid()) -
                        chargerRepository.getRechargerCountByCityIdAndStidAndUsed(cityServer.getCityID(), cityServer.getStid()) - chargerRepository.getRechargerCountByCityIdAndStidAndRepair(cityServer.getCityID(), cityServer.getStid()));
            } else if (user.getLevel() == 1) {
                jsonObject.put("value", indexRepository.getDeviceCountByDtid(SiteConstants.CHARGER_ID) -
                        chargerRepository.getRechargerCountByUsed() - chargerRepository.getRechargerCountByRepair());
            } else {
                jsonObject.put("value", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    @GetMapping("/tableData")
    @ApiOperation(value = "表中数据-前端")
    public JSONArray tableData(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("使用频次", 0);
            jsonObject.put("使用员工", 0);
            jsonObject.put("昨日用电度数", 0);
            jsonObject.put("昨日费用", 0);
            jsonObject.put("今日用电度数", 0);
            jsonObject.put("今日费用", 0);
        } else {
            if (user.getLevel() == 3) {
                BigDecimal rechargerAmountYesterday = chargerRepository.getRechargerAmountByDotIdAndDate(user.getDotid(), new DateTime().minusDays(1).toDate());
                BigDecimal rechargerAmountToday = chargerRepository.getRechargerAmountByDotIdAndDate(user.getDotid(), new Date());
                jsonObject.put("使用频次", chargerRepository.getRechargerRrequencyByDotId(user.getDotid(), false));
                jsonObject.put("使用员工", chargerRepository.getRechargerRrequencyByDotId(user.getDotid(), true));
                jsonObject.put("昨日用电度数", rechargerAmountYesterday);
                jsonObject.put("昨日费用", rechargerAmountYesterday.multiply(SiteConstants.ELECTRICITY_PRICE));
                jsonObject.put("今日用电度数", rechargerAmountToday);
                jsonObject.put("今日费用", rechargerAmountToday.multiply(SiteConstants.ELECTRICITY_PRICE));
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                BigDecimal rechargerAmountYesterday = chargerRepository.getRechargerAmountByCityIdAndStidAndDate(cityServer.getCityID(), cityServer.getStid(), new DateTime().minusDays(1).toDate());
                BigDecimal rechargerAmountToday = chargerRepository.getRechargerAmountByCityIdAndStidAndDate(cityServer.getCityID(), cityServer.getStid(), new Date());
                jsonObject.put("使用频次", chargerRepository.getRechargerRrequencyByCityIdAndStid(cityServer.getCityID(), cityServer.getStid(), false));
                jsonObject.put("使用员工", chargerRepository.getRechargerRrequencyByCityIdAndStid(cityServer.getCityID(), cityServer.getStid(), true));
                jsonObject.put("昨日用电度数", rechargerAmountYesterday);
                jsonObject.put("昨日费用", rechargerAmountYesterday.multiply(SiteConstants.ELECTRICITY_PRICE));
                jsonObject.put("今日用电度数", rechargerAmountToday);
                jsonObject.put("今日费用", rechargerAmountToday.multiply(SiteConstants.ELECTRICITY_PRICE));
            } else if (user.getLevel() == 1) {
                BigDecimal rechargerAmountYesterday = chargerRepository.getRechargerAmountByDate(new DateTime().minusDays(1).toDate());
                BigDecimal rechargerAmountToday = chargerRepository.getRechargerAmountByDate(new Date());
                jsonObject.put("使用频次", chargerRepository.getRechargerRrequency(false));
                jsonObject.put("使用员工", chargerRepository.getRechargerRrequency(true));
                jsonObject.put("昨日用电度数", rechargerAmountYesterday);
                jsonObject.put("昨日费用", rechargerAmountYesterday.multiply(SiteConstants.ELECTRICITY_PRICE));
                jsonObject.put("今日用电度数", rechargerAmountToday);
                jsonObject.put("今日费用", rechargerAmountToday.multiply(SiteConstants.ELECTRICITY_PRICE));
            } else {
                jsonObject.put("使用频次", 0);
                jsonObject.put("使用员工", 0);
                jsonObject.put("昨日用电度数", 0);
                jsonObject.put("昨日费用", 0);
                jsonObject.put("今日用电度数", 0);
                jsonObject.put("今日费用", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

}
