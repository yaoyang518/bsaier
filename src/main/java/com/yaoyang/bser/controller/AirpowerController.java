package com.yaoyang.bser.controller;


import com.yaoyang.bser.constants.SiteConstants;
import com.yaoyang.bser.entity.CityServer;
import com.yaoyang.bser.entity.User;
import com.yaoyang.bser.repository.AirpowerRepository;
import com.yaoyang.bser.repository.AirpowerSortRepository;
import com.yaoyang.bser.repository.CityServerRepository;
import com.yaoyang.bser.repository.IndexRepository;
import com.yaoyang.bser.service.UserService;
import com.yaoyang.bser.util.DateUtil;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 空调插座接口
 *
 * @author yaoyang
 * @date 2019-03-24
 */
@RestController
@RequestMapping(path = "/api/airpowers")
public class AirpowerController {
    @Autowired
    private AirpowerRepository airpowerRepository;
    @Autowired
    private AirpowerSortRepository airpowerSortRepository;
    @Autowired
    private CityServerRepository cityServerRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/tableData")
    @ApiOperation(value = "表格数据-前端")
    public JSONArray totalDeviceCount(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("经理区域使用中", 0);
            jsonObject.put("经理区域空闲中", 0);
            jsonObject.put("员工区域使用中", 0);
            jsonObject.put("员工区域空闲中", 0);
        } else {
            if (user.getLevel() == 3) {
                jsonObject.put("经理区域使用中", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndDotId(SiteConstants.MANAGER_TYPE, 1, user.getDotid()));
                jsonObject.put("经理区域空闲中", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndDotId(SiteConstants.MANAGER_TYPE, 0, user.getDotid()));
                jsonObject.put("员工区域使用中", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndDotId(SiteConstants.EMPLOYEE_TYPE, 1, user.getDotid()));
                jsonObject.put("员工区域空闲中", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndDotId(SiteConstants.EMPLOYEE_TYPE, 0, user.getDotid()));
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                jsonObject.put("经理区域使用中", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndCityIdAndStid(SiteConstants.MANAGER_TYPE, 1, cityServer.getCityID(), cityServer.getStid()));
                jsonObject.put("经理区域空闲中", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndCityIdAndStid(SiteConstants.MANAGER_TYPE, 0, cityServer.getCityID(), cityServer.getStid()));
                jsonObject.put("员工区域使用中", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndCityIdAndStid(SiteConstants.EMPLOYEE_TYPE, 1, cityServer.getCityID(), cityServer.getStid()));
                jsonObject.put("员工区域空闲中", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndCityIdAndStid(SiteConstants.EMPLOYEE_TYPE, 0, cityServer.getCityID(), cityServer.getStid()));
            } else if (user.getLevel() == 1) {
                if (user.getStid() != null) {
                    jsonObject.put("经理区域使用中", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndStid(SiteConstants.MANAGER_TYPE, 1, user.getStid()));
                    jsonObject.put("经理区域空闲中", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndStid(SiteConstants.MANAGER_TYPE, 0, user.getStid()));
                    jsonObject.put("员工区域使用中", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndStid(SiteConstants.EMPLOYEE_TYPE, 1, user.getStid()));
                    jsonObject.put("员工区域空闲中", airpowerRepository.getAirpowerCountByTypeAndIsUsedAndStid(SiteConstants.EMPLOYEE_TYPE, 0, user.getStid()));
                } else {
                    jsonObject.put("经理区域使用中", airpowerRepository.getAirpowerCountByTypeAndIsUsed(SiteConstants.MANAGER_TYPE, 1));
                    jsonObject.put("经理区域空闲中", airpowerRepository.getAirpowerCountByTypeAndIsUsed(SiteConstants.MANAGER_TYPE, 0));
                    jsonObject.put("员工区域使用中", airpowerRepository.getAirpowerCountByTypeAndIsUsed(SiteConstants.EMPLOYEE_TYPE, 1));
                    jsonObject.put("员工区域空闲中", airpowerRepository.getAirpowerCountByTypeAndIsUsed(SiteConstants.EMPLOYEE_TYPE, 0));
                }
            } else {
                jsonObject.put("经理区域使用中", 0);
                jsonObject.put("经理区域空闲中", 0);
                jsonObject.put("员工区域使用中", 0);
                jsonObject.put("员工区域空闲中", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    @GetMapping("/getRechargerAmountManage")
    @ApiOperation(value = "经理用电-前端")
    public JSONArray getRechargerAmountManage(HttpServletRequest request) {
        return getJsonArrayForRechargerAmount(request, SiteConstants.MANAGER_TYPE);
    }

    @GetMapping("/getRechargerAmountEmployee")
    @ApiOperation(value = "员工用电-前端")
    public JSONArray getRechargerAmountEmployee(HttpServletRequest request) {
        return getJsonArrayForRechargerAmount(request, SiteConstants.EMPLOYEE_TYPE);
    }

    private JSONArray getJsonArrayForRechargerAmount(HttpServletRequest request, Integer type) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("value", 0);
        } else {
            if (user.getLevel() == 3) {
                jsonObject.put("value", airpowerRepository.getRechargerAmountByTypeAndDotId(type, user.getDotid()));
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                jsonObject.put("value", airpowerRepository.getRechargerAmountByTypeAndCityIdAndStid(type, cityServer.getCityID(), cityServer.getStid()));
            } else if (user.getLevel() == 1) {
                if (user.getStid() != null) {
                    jsonObject.put("value", airpowerRepository.getRechargerAmountByTypeAndStid(type, user.getStid()));
                } else {
                    jsonObject.put("value", airpowerRepository.getRechargerAmountByType(type));
                }
            } else {
                jsonObject.put("value", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    @GetMapping("/getRechargerFeeManage")
    @ApiOperation(value = "经理用电费用-前端")
    public JSONArray getRechargerFeeManage(HttpServletRequest request) {
        return getJsonArrayForRechargerFee(request, SiteConstants.MANAGER_TYPE);
    }

    @GetMapping("/getRechargerFeeEmployee")
    @ApiOperation(value = "员工用电费用-前端")
    public JSONArray getRechargerFeeEmployee(HttpServletRequest request) {
        return getJsonArrayForRechargerFee(request, SiteConstants.EMPLOYEE_TYPE);
    }

    private JSONArray getJsonArrayForRechargerFee(HttpServletRequest request, Integer type) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("value", 0);
        } else {
            if (user.getLevel() == 3) {
                jsonObject.put("value", airpowerRepository.getRechargerAmountByTypeAndDotId(type, user.getDotid()).multiply(SiteConstants.ELECTRICITY_PRICE));
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                jsonObject.put("value", airpowerRepository.getRechargerAmountByTypeAndCityIdAndStid(type, cityServer.getCityID(), cityServer.getStid()).multiply(SiteConstants.ELECTRICITY_PRICE));
            } else if (user.getLevel() == 1) {
                if (user.getStid() != null) {
                    jsonObject.put("value", airpowerRepository.getRechargerAmountByTypeAndStid(type, user.getStid()).multiply(SiteConstants.ELECTRICITY_PRICE));
                } else {
                    jsonObject.put("value", airpowerRepository.getRechargerAmountByType(type).multiply(SiteConstants.ELECTRICITY_PRICE));
                }
            } else {
                jsonObject.put("value", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    @GetMapping("/getAirTimeCountManage")
    @ApiOperation(value = "经理区域报警次数-前端")
    public JSONArray getAirTimeCountManage(HttpServletRequest request) {
        return getJsonArrayForAirTimeCount(request, SiteConstants.MANAGER_TYPE);
    }

    @GetMapping("/getAirTimeCountEmployee")
    @ApiOperation(value = "员工区域报警次数-前端")
    public JSONArray getAirTimeCountEmployee(HttpServletRequest request) {
        return getJsonArrayForAirTimeCount(request, SiteConstants.EMPLOYEE_TYPE);
    }

    private JSONArray getJsonArrayForAirTimeCount(HttpServletRequest request, Integer type) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("value", 0);
        } else {
            if (user.getLevel() == 3) {
                jsonObject.put("value", airpowerRepository.getAirTimeCountByTypeAndDotId(type, user.getDotid()));
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                jsonObject.put("value", airpowerRepository.getAirTimeCountByTypeAndCityIdAndStid(type, cityServer.getCityID(), cityServer.getStid()));
            } else if (user.getLevel() == 1) {
                if (user.getStid() != null) {
                    jsonObject.put("value", airpowerRepository.getAirTimeCountByTypeAndStid(type, user.getStid()));
                } else {
                    jsonObject.put("value", airpowerRepository.getAirTimeCountByType(type));
                }
            } else {
                jsonObject.put("value", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    @GetMapping("/getAirTimeCitySort")
    @ApiOperation(value = "城市排行榜-前端")
    public JSONArray getAirTimeCitySort(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("x", 0);
            jsonObject.put("y", 0);
        } else {
            if (user.getLevel() == 1) {
                if (user.getStid() != null) {
                    return airpowerSortRepository.getAirTimeCitySortByStid(user.getStid());
                }
            } else {
                jsonObject.put("x", 0);
                jsonObject.put("y", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    @GetMapping("/getAirTimeDotSort")
    @ApiOperation(value = "网点排行榜-前端")
    public JSONArray getAirTimeDotSort(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("x", 0);
            jsonObject.put("y", 0);
        } else {
            if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                return airpowerSortRepository.getAirTimeDotSortByStidAndCityID(cityServer.getStid(), cityServer.getCityID());
            } else if (user.getLevel() == 1) {
                if (user.getStid() != null) {
                    return airpowerSortRepository.getAirTimeDotSortByStid(user.getStid());
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
