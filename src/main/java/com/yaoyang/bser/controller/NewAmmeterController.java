package com.yaoyang.bser.controller;


import com.yaoyang.bser.constants.SiteConstants;
import com.yaoyang.bser.entity.CityServer;
import com.yaoyang.bser.entity.User;
import com.yaoyang.bser.repository.AmmeterRepository;
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
 * 电表接口
 *
 * @author yaoyang
 * @date 2019-03-24
 */
@RestController
@RequestMapping(path = "/api/new/ammeters")
public class NewAmmeterController {
    @Autowired
    private AmmeterRepository ammeterRepository;
    @Autowired
    private CityServerRepository cityServerRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/ammeterDataByType")
    @ApiOperation(value = "根据区域获取电表数据-前端")
    public JSONArray ammeterDataByType(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        if (user == null) {
            generateEmptyResult(jsonArray);
        } else {
            if (user.getLevel() == 3) {
                generateResultForDot(user, jsonArray);
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                generateResultForCity(jsonArray, cityServer);
            } else if (user.getLevel() == 1) {
                generateResultForAll(user, jsonArray);
            }
        }
        return jsonArray;
    }

    @GetMapping("/getAmmeterData")
    @ApiOperation(value = "昨日今日数据-前端")
    public JSONArray getAmmeterData(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        if (user == null) {
            JSONObject jsonY = new JSONObject();
            jsonY.put("day", "昨日");
            jsonY.put("power", 0);
            jsonY.put("money", 0);
            jsonY.put("case", 0);
            JSONObject jsonT = new JSONObject();
            jsonT.put("day", "今日");
            jsonT.put("power", 0);
            jsonT.put("money", 0);
            jsonT.put("case", 0);
            jsonArray.add(jsonY);
            jsonArray.add(jsonT);
        } else {
            if (user.getLevel() == 3) {
                BigDecimal rechargerAmountYesterday = ammeterRepository.getRechargerAmountByDotIdAndDate(user.getDotid(), DateUtil.getStartDate(new DateTime().minusDays(1).toDate()), DateUtil.getEndDate(new DateTime().minusDays(1).toDate()));
                BigDecimal rechargerAmountToday = ammeterRepository.getRechargerAmountByDotIdAndDate(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()));
                JSONObject jsonY = new JSONObject();
                jsonY.put("day", "昨日");
                jsonY.put("power", rechargerAmountYesterday == null ? 0 : rechargerAmountYesterday);
                jsonY.put("money", rechargerAmountYesterday == null ? 0 : rechargerAmountYesterday.multiply(SiteConstants.ELECTRICITY_PRICE));
                jsonY.put("case", ammeterRepository.getOverLoadCountByDotIdAndDate(user.getDotid(), DateUtil.getStartDate(new DateTime().minusDays(1).toDate()), DateUtil.getEndDate(new DateTime().minusDays(1).toDate())));
                JSONObject jsonT = new JSONObject();
                jsonT.put("day", "今日");
                jsonT.put("power", rechargerAmountToday == null ? 0 : rechargerAmountToday);
                jsonT.put("money", rechargerAmountToday == null ? 0 : rechargerAmountToday.multiply(SiteConstants.ELECTRICITY_PRICE));
                jsonT.put("case", ammeterRepository.getOverLoadCountByDotIdAndDate(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date())));
                jsonArray.add(jsonY);
                jsonArray.add(jsonT);
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                BigDecimal rechargerAmountYesterday = ammeterRepository.getRechargerAmountByCityIdAndStidAndDate(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new DateTime().minusDays(1).toDate()), DateUtil.getEndDate(new DateTime().minusDays(1).toDate()));
                BigDecimal rechargerAmountToday = ammeterRepository.getRechargerAmountByCityIdAndStidAndDate(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()));
                JSONObject jsonY = new JSONObject();
                jsonY.put("day", "昨日");
                jsonY.put("power", rechargerAmountYesterday == null ? 0 : rechargerAmountYesterday);
                jsonY.put("money", rechargerAmountYesterday == null ? 0 : rechargerAmountYesterday.multiply(SiteConstants.ELECTRICITY_PRICE));
                jsonY.put("case", ammeterRepository.getOverLoadCountByCityIdAndStidAndDate(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new DateTime().minusDays(1).toDate()), DateUtil.getEndDate(new DateTime().minusDays(1).toDate())));
                JSONObject jsonT = new JSONObject();
                jsonT.put("day", "今日");
                jsonT.put("power", rechargerAmountToday == null ? 0 : rechargerAmountToday);
                jsonT.put("money", rechargerAmountToday == null ? 0 : rechargerAmountToday.multiply(SiteConstants.ELECTRICITY_PRICE));
                jsonT.put("case", ammeterRepository.getOverLoadCountByCityIdAndStidAndDate(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date())));
                jsonArray.add(jsonY);
                jsonArray.add(jsonT);
            } else if (user.getLevel() == 1) {
                BigDecimal rechargerAmountYesterday = ammeterRepository.getRechargerAmountByDateAndStid(DateUtil.getStartDate(new DateTime().minusDays(1).toDate()), DateUtil.getEndDate(new DateTime().minusDays(1).toDate()), user.getStid());
                BigDecimal rechargerAmountToday = ammeterRepository.getRechargerAmountByDateAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), user.getStid());
                JSONObject jsonY = new JSONObject();
                jsonY.put("day", "昨日");
                jsonY.put("power", rechargerAmountYesterday == null ? 0 : rechargerAmountYesterday);
                jsonY.put("money",rechargerAmountYesterday == null ? 0 : rechargerAmountYesterday.multiply(SiteConstants.ELECTRICITY_PRICE));
                jsonY.put("case", ammeterRepository.getOverLoadCountByDateAndStid(DateUtil.getStartDate(new DateTime().minusDays(1).toDate()), DateUtil.getEndDate(new DateTime().minusDays(1).toDate()), user.getStid()));
                JSONObject jsonT = new JSONObject();
                jsonT.put("day", "今日");
                jsonT.put("power", rechargerAmountToday == null ? 0 : rechargerAmountToday);
                jsonT.put("money", rechargerAmountToday == null ? 0 : rechargerAmountToday.multiply(SiteConstants.ELECTRICITY_PRICE));
                jsonT.put("case", ammeterRepository.getOverLoadCountByDateAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), user.getStid()));
                jsonArray.add(jsonY);
                jsonArray.add(jsonT);
            }
        }
        return jsonArray;
    }

    private void generateResultForAll(User user, JSONArray jsonArray) {
        JSONObject jsonR1 = new JSONObject();
        jsonR1.put("x", "充电用电");
        jsonR1.put("y", ammeterRepository.getRechargerAmountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 1, user.getStid())==null?0:ammeterRepository.getRechargerAmountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 1, user.getStid()));
        jsonR1.put("s", 1);
        JSONObject jsonR2 = new JSONObject();
        jsonR2.put("x", "充电用电");
        jsonR2.put("y", ammeterRepository.getRechargerAmountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 1, user.getStid()) == null ? 0 : ammeterRepository.getRechargerAmountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 1, user.getStid()).multiply(SiteConstants.ELECTRICITY_PRICE));
        jsonR2.put("s", 2);
        JSONObject jsonR3 = new JSONObject();
        jsonR3.put("x", "充电用电");
        jsonR3.put("y", ammeterRepository.getOverLoadCountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 1, user.getStid()));
        jsonR3.put("s", 3);
        JSONObject jsonW1 = new JSONObject();
        jsonW1.put("x", "办公用电");
        jsonW1.put("y", ammeterRepository.getRechargerAmountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 2, user.getStid()));
        jsonW1.put("s", 1);
        JSONObject jsonW2 = new JSONObject();
        jsonW2.put("x", "办公用电");
        jsonW2.put("y", ammeterRepository.getRechargerAmountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 2, user.getStid()) == null ? 0 : ammeterRepository.getRechargerAmountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 2, user.getStid()).multiply(SiteConstants.ELECTRICITY_PRICE));
        jsonW2.put("s", 2);
        JSONObject jsonW3 = new JSONObject();
        jsonW3.put("x", "办公用电");
        jsonW3.put("y", ammeterRepository.getOverLoadCountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 2, user.getStid()));
        jsonW3.put("s", 3);
        JSONObject jsonA1 = new JSONObject();
        jsonA1.put("x", "空调用电");
        jsonA1.put("y", ammeterRepository.getRechargerAmountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 3, user.getStid()));
        jsonA1.put("s", 1);
        JSONObject jsonA2 = new JSONObject();
        jsonA2.put("x", "空调用电");
        jsonA2.put("y", ammeterRepository.getRechargerAmountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 3, user.getStid()) == null ? 0 : ammeterRepository.getRechargerAmountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 3, user.getStid()).multiply(SiteConstants.ELECTRICITY_PRICE));
        jsonA2.put("s", 2);
        JSONObject jsonA3 = new JSONObject();
        jsonA3.put("x", "空调用电");
        jsonA3.put("y", ammeterRepository.getOverLoadCountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 3, user.getStid()));
        jsonA3.put("s", 3);
        JSONObject jsonO1 = new JSONObject();
        jsonO1.put("x", "操作区用电");
        jsonO1.put("y", ammeterRepository.getRechargerAmountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 4, user.getStid()));
        jsonO1.put("s", 1);
        JSONObject jsonO2 = new JSONObject();
        jsonO2.put("x", "操作区用电");
        jsonO2.put("y", ammeterRepository.getRechargerAmountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 4, user.getStid()) == null ? 0 : ammeterRepository.getRechargerAmountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 4, user.getStid()).multiply(SiteConstants.ELECTRICITY_PRICE));
        jsonO2.put("s", 2);
        JSONObject jsonO3 = new JSONObject();
        jsonO3.put("x", "操作区用电");
        jsonO3.put("y", ammeterRepository.getOverLoadCountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 4, user.getStid()));
        jsonO3.put("s", 3);
        JSONObject jsonOth1 = new JSONObject();
        jsonOth1.put("x", "其他用电");
        jsonOth1.put("y", ammeterRepository.getRechargerAmountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 5, user.getStid()));
        jsonOth1.put("s", 1);
        JSONObject jsonOth2 = new JSONObject();
        jsonOth2.put("x", "其他用电");
        jsonOth2.put("y", ammeterRepository.getRechargerAmountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 5, user.getStid()) == null ? 0 : ammeterRepository.getRechargerAmountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 5, user.getStid()).multiply(SiteConstants.ELECTRICITY_PRICE));
        jsonOth2.put("s", 2);
        JSONObject jsonOth3 = new JSONObject();
        jsonOth3.put("x", "其他用电");
        jsonOth3.put("y", ammeterRepository.getOverLoadCountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 5, user.getStid()));
        jsonOth3.put("s", 3);
        jsonArray.add(jsonR1);
        jsonArray.add(jsonR2);
        jsonArray.add(jsonR3);
        jsonArray.add(jsonW1);
        jsonArray.add(jsonW2);
        jsonArray.add(jsonW3);
        jsonArray.add(jsonA1);
        jsonArray.add(jsonA2);
        jsonArray.add(jsonA3);
        jsonArray.add(jsonO1);
        jsonArray.add(jsonO2);
        jsonArray.add(jsonO3);
        jsonArray.add(jsonOth1);
        jsonArray.add(jsonOth2);
        jsonArray.add(jsonOth3);
    }

    private void generateResultForCity(JSONArray jsonArray, CityServer cityServer) {
        JSONObject jsonR1 = new JSONObject();
        jsonR1.put("x", "充电用电");
        jsonR1.put("y", ammeterRepository.getRechargerAmountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 1));
        jsonR1.put("s", 1);
        JSONObject jsonR2 = new JSONObject();
        jsonR2.put("x", "充电用电");
        jsonR2.put("y", ammeterRepository.getRechargerAmountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 1) == null ? 0 : ammeterRepository.getRechargerAmountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 1).multiply(SiteConstants.ELECTRICITY_PRICE));
        jsonR2.put("s", 2);
        JSONObject jsonR3 = new JSONObject();
        jsonR3.put("x", "充电用电");
        jsonR3.put("y", ammeterRepository.getOverLoadCountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 1));
        jsonR3.put("s", 3);
        JSONObject jsonW1 = new JSONObject();
        jsonW1.put("x", "办公用电");
        jsonW1.put("y", ammeterRepository.getRechargerAmountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 2));
        jsonW1.put("s", 1);
        JSONObject jsonW2 = new JSONObject();
        jsonW2.put("x", "办公用电");
        jsonW2.put("y", ammeterRepository.getRechargerAmountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 2) == null ? 0 : ammeterRepository.getRechargerAmountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 2).multiply(SiteConstants.ELECTRICITY_PRICE));
        jsonW2.put("s", 2);
        JSONObject jsonW3 = new JSONObject();
        jsonW3.put("x", "办公用电");
        jsonW3.put("y", ammeterRepository.getOverLoadCountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 2));
        jsonW3.put("s", 3);
        JSONObject jsonA1 = new JSONObject();
        jsonA1.put("x", "空调用电");
        jsonA1.put("y", ammeterRepository.getRechargerAmountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 3));
        jsonA1.put("s", 1);
        JSONObject jsonA2 = new JSONObject();
        jsonA2.put("x", "空调用电");
        jsonA2.put("y", ammeterRepository.getRechargerAmountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 3) == null ? 0 : ammeterRepository.getRechargerAmountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 3).multiply(SiteConstants.ELECTRICITY_PRICE));
        jsonA2.put("s", 2);
        JSONObject jsonA3 = new JSONObject();
        jsonA3.put("x", "空调用电");
        jsonA3.put("y", ammeterRepository.getOverLoadCountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 3));
        jsonA3.put("s", 3);
        JSONObject jsonO1 = new JSONObject();
        jsonO1.put("x", "操作区用电");
        jsonO1.put("y", ammeterRepository.getRechargerAmountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 4));
        jsonO1.put("s", 1);
        JSONObject jsonO2 = new JSONObject();
        jsonO2.put("x", "操作区用电");
        jsonO2.put("y", ammeterRepository.getRechargerAmountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 4) == null ? 0 : ammeterRepository.getRechargerAmountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 4).multiply(SiteConstants.ELECTRICITY_PRICE));
        jsonO2.put("s", 2);
        JSONObject jsonO3 = new JSONObject();
        jsonO3.put("x", "操作区用电");
        jsonO3.put("y", ammeterRepository.getOverLoadCountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 4));
        jsonO3.put("s", 3);
        JSONObject jsonOth1 = new JSONObject();
        jsonOth1.put("x", "其他用电");
        jsonOth1.put("y", ammeterRepository.getRechargerAmountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 5));
        jsonOth1.put("s", 1);
        JSONObject jsonOth2 = new JSONObject();
        jsonOth2.put("x", "其他用电");
        jsonOth2.put("y", ammeterRepository.getRechargerAmountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 5) == null ? 0 : ammeterRepository.getRechargerAmountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 5).multiply(SiteConstants.ELECTRICITY_PRICE));
        jsonOth2.put("s", 2);
        JSONObject jsonOth3 = new JSONObject();
        jsonOth3.put("x", "其他用电");
        jsonOth3.put("y", ammeterRepository.getOverLoadCountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 5));
        jsonOth3.put("s", 3);
        jsonArray.add(jsonR1);
        jsonArray.add(jsonR2);
        jsonArray.add(jsonR3);
        jsonArray.add(jsonW1);
        jsonArray.add(jsonW2);
        jsonArray.add(jsonW3);
        jsonArray.add(jsonA1);
        jsonArray.add(jsonA2);
        jsonArray.add(jsonA3);
        jsonArray.add(jsonO1);
        jsonArray.add(jsonO2);
        jsonArray.add(jsonO3);
        jsonArray.add(jsonOth1);
        jsonArray.add(jsonOth2);
        jsonArray.add(jsonOth3);
    }

    private void generateResultForDot(User user, JSONArray jsonArray) {
        JSONObject jsonR1 = new JSONObject();
        jsonR1.put("x", "充电用电");
        jsonR1.put("y", ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 1));
        jsonR1.put("s", 1);
        JSONObject jsonR2 = new JSONObject();
        jsonR2.put("x", "充电用电");
        jsonR2.put("y", ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 1) == null ? 0 : ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 1).multiply(SiteConstants.ELECTRICITY_PRICE));
        jsonR2.put("s", 2);
        JSONObject jsonR3 = new JSONObject();
        jsonR3.put("x", "充电用电");
        jsonR3.put("y", ammeterRepository.getOverLoadCountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 1));
        jsonR3.put("s", 3);
        JSONObject jsonW1 = new JSONObject();
        jsonW1.put("x", "办公用电");
        jsonW1.put("y", ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 2));
        jsonW1.put("s", 1);
        JSONObject jsonW2 = new JSONObject();
        jsonW2.put("x", "办公用电");
        jsonW2.put("y", ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 2) == null ? 0 : ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 2).multiply(SiteConstants.ELECTRICITY_PRICE));
        jsonW2.put("s", 2);
        JSONObject jsonW3 = new JSONObject();
        jsonW3.put("x", "办公用电");
        jsonW3.put("y", ammeterRepository.getOverLoadCountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 2));
        jsonW3.put("s", 3);
        JSONObject jsonA1 = new JSONObject();
        jsonA1.put("x", "空调用电");
        jsonA1.put("y", ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 3));
        jsonA1.put("s", 1);
        JSONObject jsonA2 = new JSONObject();
        jsonA2.put("x", "空调用电");
        jsonA2.put("y", ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 3) == null ? 0 : ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 3).multiply(SiteConstants.ELECTRICITY_PRICE));
        jsonA2.put("s", 2);
        JSONObject jsonA3 = new JSONObject();
        jsonA3.put("x", "空调用电");
        jsonA3.put("y", ammeterRepository.getOverLoadCountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 3));
        jsonA3.put("s", 3);
        JSONObject jsonO1 = new JSONObject();
        jsonO1.put("x", "操作区用电");
        jsonO1.put("y", ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 4));
        jsonO1.put("s", 1);
        JSONObject jsonO2 = new JSONObject();
        jsonO2.put("x", "操作区用电");
        jsonO2.put("y", ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 4) == null ? 0 : ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 4).multiply(SiteConstants.ELECTRICITY_PRICE));
        jsonO2.put("s", 2);
        JSONObject jsonO3 = new JSONObject();
        jsonO3.put("x", "操作区用电");
        jsonO3.put("y", ammeterRepository.getOverLoadCountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 4));
        jsonO3.put("s", 3);
        JSONObject jsonOth1 = new JSONObject();
        jsonOth1.put("x", "其他用电");
        jsonOth1.put("y", ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 5));
        jsonOth1.put("s", 1);
        JSONObject jsonOth2 = new JSONObject();
        jsonOth2.put("x", "其他用电");
        jsonOth2.put("y", ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 5) == null ? 0 : ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 5).multiply(SiteConstants.ELECTRICITY_PRICE));
        jsonOth2.put("s", 2);
        JSONObject jsonOth3 = new JSONObject();
        jsonOth3.put("x", "其他用电");
        jsonOth3.put("y", ammeterRepository.getOverLoadCountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 5));
        jsonOth3.put("s", 3);
        jsonArray.add(jsonR1);
        jsonArray.add(jsonR2);
        jsonArray.add(jsonR3);
        jsonArray.add(jsonW1);
        jsonArray.add(jsonW2);
        jsonArray.add(jsonW3);
        jsonArray.add(jsonA1);
        jsonArray.add(jsonA2);
        jsonArray.add(jsonA3);
        jsonArray.add(jsonO1);
        jsonArray.add(jsonO2);
        jsonArray.add(jsonO3);
        jsonArray.add(jsonOth1);
        jsonArray.add(jsonOth2);
        jsonArray.add(jsonOth3);
    }


    private void generateEmptyResult(JSONArray jsonArray) {
        JSONObject jsonR1 = new JSONObject();
        jsonR1.put("x", "充电用电");
        jsonR1.put("y", 0);
        jsonR1.put("s", 1);
        JSONObject jsonR2 = new JSONObject();
        jsonR2.put("x", "充电用电");
        jsonR2.put("y", 0);
        jsonR2.put("s", 2);
        JSONObject jsonR3 = new JSONObject();
        jsonR3.put("x", "充电用电");
        jsonR3.put("y", 0);
        jsonR3.put("s", 3);
        JSONObject jsonW1 = new JSONObject();
        jsonW1.put("x", "办公用电");
        jsonW1.put("y", 0);
        jsonW1.put("s", 1);
        JSONObject jsonW2 = new JSONObject();
        jsonW2.put("x", "办公用电");
        jsonW2.put("y", 0);
        jsonW2.put("s", 2);
        JSONObject jsonW3 = new JSONObject();
        jsonW3.put("x", "办公用电");
        jsonW3.put("y", 0);
        jsonW3.put("s", 3);
        JSONObject jsonA1 = new JSONObject();
        jsonA1.put("x", "空调用电");
        jsonA1.put("y", 0);
        jsonA1.put("s", 1);
        JSONObject jsonA2 = new JSONObject();
        jsonA2.put("x", "空调用电");
        jsonA2.put("y", 0);
        jsonA2.put("s", 2);
        JSONObject jsonA3 = new JSONObject();
        jsonA3.put("x", "空调用电");
        jsonA3.put("y", 0);
        jsonA3.put("s", 3);
        JSONObject jsonO1 = new JSONObject();
        jsonO1.put("x", "操作区用电");
        jsonO1.put("y", 0);
        jsonO1.put("s", 1);
        JSONObject jsonO2 = new JSONObject();
        jsonO2.put("x", "操作区用电");
        jsonO2.put("y", 0);
        jsonO2.put("s", 2);
        JSONObject jsonO3 = new JSONObject();
        jsonO3.put("x", "操作区用电");
        jsonO3.put("y", 0);
        jsonO3.put("s", 3);
        JSONObject jsonOth1 = new JSONObject();
        jsonOth1.put("x", "其他用电");
        jsonOth1.put("y", 0);
        jsonOth1.put("s", 1);
        JSONObject jsonOth2 = new JSONObject();
        jsonOth2.put("x", "其他用电");
        jsonOth2.put("y", 0);
        jsonOth2.put("s", 2);
        JSONObject jsonOth3 = new JSONObject();
        jsonOth3.put("x", "其他用电");
        jsonOth3.put("y", 0);
        jsonOth3.put("s", 3);
        jsonArray.add(jsonR1);
        jsonArray.add(jsonR2);
        jsonArray.add(jsonR3);
        jsonArray.add(jsonW1);
        jsonArray.add(jsonW2);
        jsonArray.add(jsonW3);
        jsonArray.add(jsonA1);
        jsonArray.add(jsonA2);
        jsonArray.add(jsonA3);
        jsonArray.add(jsonO1);
        jsonArray.add(jsonO2);
        jsonArray.add(jsonO3);
        jsonArray.add(jsonOth1);
        jsonArray.add(jsonOth2);
        jsonArray.add(jsonOth3);
    }

}
