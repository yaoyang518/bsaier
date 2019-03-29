package com.yaoyang.bser.controller;


import com.yaoyang.bser.entity.CityServer;
import com.yaoyang.bser.entity.User;
import com.yaoyang.bser.repository.CityServerRepository;
import com.yaoyang.bser.repository.IndexRepository;
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
@RequestMapping(path = "/api/smokes")
public class SmokeController {
    @Autowired
    private SomkeRepository somkeRepository;
    @Autowired
    private SomkeSortRepository somkeSortRepository;
    @Autowired
    private CityServerRepository cityServerRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/getSmokeTimeToday")
    @ApiOperation(value = "今日报警次数-前端")
    public JSONArray getSmokeTimeToday(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("value", 0);
        } else {
            if (user.getLevel() == 3) {
                jsonObject.put("value", somkeRepository.getSmokeTimeByDotIdAndDate(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date())));
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                jsonObject.put("value", somkeRepository.getSmokeTimeByCityIdAndStidAndDate(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date())) == null ? 0 : somkeRepository.getSmokeTimeByCityIdAndStidAndDate(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date())));
            } else if (user.getLevel() == 1) {
                if (user.getStid() != null) {
                    jsonObject.put("value", somkeRepository.getSmokeTimeByDateAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), user.getStid()));
                } else {
                    jsonObject.put("value", somkeRepository.getSmokeTimeByDate(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date())));
                }
            } else {
                jsonObject.put("value", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    @GetMapping("/getSmokeTimeYesterday")
    @ApiOperation(value = "昨日报警次数-前端")
    public JSONArray getSmokeTimeYesterday(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("value", 0);
        } else {
            if (user.getLevel() == 3) {
                jsonObject.put("value", somkeRepository.getSmokeTimeByDotIdAndDate(user.getDotid(), DateUtil.getStartDate(new DateTime().minus(1l).toDate()), DateUtil.getEndDate(new DateTime().minus(1l).toDate())));
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                jsonObject.put("value", somkeRepository.getSmokeTimeByCityIdAndStidAndDate(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new DateTime().minus(1l).toDate()), DateUtil.getEndDate(new DateTime().minus(1l).toDate())));
            } else if (user.getLevel() == 1) {
                if (user.getStid() != null) {
                    jsonObject.put("value", somkeRepository.getSmokeTimeByDateAndStid(DateUtil.getStartDate(new DateTime().minus(1l).toDate()), DateUtil.getEndDate(new DateTime().minus(1l).toDate()), user.getStid()));
                } else {
                    jsonObject.put("value", somkeRepository.getSmokeTimeByDate(DateUtil.getStartDate(new DateTime().minus(1l).toDate()), DateUtil.getEndDate(new DateTime().minus(1l).toDate())));
                }
            } else {
                jsonObject.put("value", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    @GetMapping("/getSmokeCityCount")
    @ApiOperation(value = "报警城市数-前端")
    public JSONArray getSmokeCityCount(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("value", 0);
        } else {
            if (user.getLevel() == 1 && user.getStid() != null) {
                jsonObject.put("value", somkeRepository.getSmokeCityCountByStid(user.getStid()));
            } else {
                jsonObject.put("value", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    @GetMapping("/getSmokeDotCount")
    @ApiOperation(value = "报警网点数-前端")
    public JSONArray getSmokeDotCount(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("value", 0);
        } else {
            if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                jsonObject.put("value", somkeRepository.getSmokeDotCountByStidAndCityID(cityServer.getStid(), cityServer.getCityID()));
            } else if (user.getLevel() == 1 && user.getStid() != null) {
                jsonObject.put("value", somkeRepository.getSmokeDotCountByStid(user.getStid()));
            } else {
                jsonObject.put("value", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    @GetMapping("/getSomkeTimeCitySort")
    @ApiOperation(value = "城市排行榜-前端")
    public JSONArray getSomkeTimeCitySort(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("x", 0);
            jsonObject.put("y", 0);
        } else {
            if (user.getLevel() == 1) {
                if (user.getStid() != null) {
                    return JSONArrayUtil.toJSONArray(somkeSortRepository.getSomkeTimeCitySortByStid(user.getStid()));
                }
            } else {
                jsonObject.put("x", 0);
                jsonObject.put("y", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    @GetMapping("/getSomkeTimeDotSort")
    @ApiOperation(value = "网点排行榜-前端")
    public JSONArray getSomkeTimeDotSort(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("x", 0);
            jsonObject.put("y", 0);
        } else {
            if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                return JSONArrayUtil.toJSONArray(somkeSortRepository.getSomkeTimeDotSortByStidAndCityID(cityServer.getStid(), cityServer.getCityID()));
            } else if (user.getLevel() == 1) {
                if (user.getStid() != null) {
                    return JSONArrayUtil.toJSONArray(somkeSortRepository.getSomkeTimeDotSortByStid(user.getStid()));
                }
            } else {
                jsonObject.put("x", 0);
                jsonObject.put("y", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }


}
