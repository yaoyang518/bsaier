package com.yaoyang.bser.controller;


import com.yaoyang.bser.constants.SiteConstants;
import com.yaoyang.bser.entity.CityServer;
import com.yaoyang.bser.entity.User;
import com.yaoyang.bser.repository.AmmeterRepository;
import com.yaoyang.bser.repository.CityServerRepository;
import com.yaoyang.bser.repository.IndexRepository;
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
@RequestMapping(path = "/api/ammeters")
public class AmmeterController {
    @Autowired
    private AmmeterRepository ammeterRepository;
    @Autowired
    private CityServerRepository cityServerRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/tableData")
    @ApiOperation(value = "表中数据-前端")
    public JSONArray totalDeviceCount(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("昨日用电度数", 0);
            jsonObject.put("昨日费用", 0);
            jsonObject.put("昨日过载报警次数", 0);
            jsonObject.put("今日用电度数", 0);
            jsonObject.put("今日费用", 0);
            jsonObject.put("今日过载报警次数", 0);
        } else {
            if (user.getLevel() == 3) {
                BigDecimal rechargerAmountYesterday = ammeterRepository.getRechargerAmountByDotIdAndDate(user.getDotid(), DateUtil.getStartDate(new DateTime().minusDays(1).toDate()), DateUtil.getEndDate(new DateTime().minusDays(1).toDate()));
                BigDecimal rechargerAmountToday = ammeterRepository.getRechargerAmountByDotIdAndDate(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()));
                jsonObject.put("昨日用电度数", rechargerAmountYesterday == null ? 0 : rechargerAmountYesterday);
                jsonObject.put("昨日费用", rechargerAmountYesterday == null ? 0 : rechargerAmountYesterday.multiply(SiteConstants.ELECTRICITY_PRICE));
                jsonObject.put("昨日过载报警次数", ammeterRepository.getOverLoadCountByDotIdAndDate(user.getDotid(), DateUtil.getStartDate(new DateTime().minusDays(1).toDate()), DateUtil.getEndDate(new DateTime().minusDays(1).toDate())));
                jsonObject.put("今日用电度数", rechargerAmountToday == null ? 0 : rechargerAmountToday);
                jsonObject.put("今日费用", rechargerAmountToday == null ? 0 : rechargerAmountToday.multiply(SiteConstants.ELECTRICITY_PRICE));
                jsonObject.put("今日过载报警次数", ammeterRepository.getOverLoadCountByDotIdAndDate(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date())));
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                BigDecimal rechargerAmountYesterday = ammeterRepository.getRechargerAmountByCityIdAndStidAndDate(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new DateTime().minusDays(1).toDate()), DateUtil.getEndDate(new DateTime().minusDays(1).toDate()));
                BigDecimal rechargerAmountToday = ammeterRepository.getRechargerAmountByCityIdAndStidAndDate(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()));
                jsonObject.put("昨日用电度数", rechargerAmountYesterday == null ? 0 : rechargerAmountYesterday);
                jsonObject.put("昨日费用", rechargerAmountYesterday == null ? 0 : rechargerAmountYesterday.multiply(SiteConstants.ELECTRICITY_PRICE));
                jsonObject.put("昨日过载报警次数", ammeterRepository.getOverLoadCountByCityIdAndStidAndDate(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new DateTime().minusDays(1).toDate()), DateUtil.getEndDate(new DateTime().minusDays(1).toDate())));
                jsonObject.put("今日用电度数", rechargerAmountToday == null ? 0 : rechargerAmountToday);
                jsonObject.put("今日费用", rechargerAmountToday == null ? 0 : rechargerAmountToday.multiply(SiteConstants.ELECTRICITY_PRICE));
                jsonObject.put("今日过载报警次数", ammeterRepository.getOverLoadCountByCityIdAndStidAndDate(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date())));
            } else if (user.getLevel() == 1) {
                if (user.getStid() != null) {
                    BigDecimal rechargerAmountYesterday = ammeterRepository.getRechargerAmountByDateAndStid(DateUtil.getStartDate(new DateTime().minusDays(1).toDate()), DateUtil.getEndDate(new DateTime().minusDays(1).toDate()), user.getStid());
                    BigDecimal rechargerAmountToday = ammeterRepository.getRechargerAmountByDateAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), user.getStid());
                    jsonObject.put("昨日用电度数", rechargerAmountYesterday == null ? 0 : rechargerAmountYesterday);
                    jsonObject.put("昨日费用", rechargerAmountYesterday == null ? 0 : rechargerAmountYesterday.multiply(SiteConstants.ELECTRICITY_PRICE));
                    jsonObject.put("昨日过载报警次数", ammeterRepository.getOverLoadCountByDateAndStid(DateUtil.getStartDate(new DateTime().minusDays(1).toDate()), DateUtil.getEndDate(new DateTime().minusDays(1).toDate()), user.getStid()));
                    jsonObject.put("今日用电度数", rechargerAmountToday == null ? 0 : rechargerAmountToday);
                    jsonObject.put("今日费用", rechargerAmountToday == null ? 0 : rechargerAmountToday.multiply(SiteConstants.ELECTRICITY_PRICE));
                    jsonObject.put("今日过载报警次数", ammeterRepository.getOverLoadCountByDateAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), user.getStid()));
                } else {
                    BigDecimal rechargerAmountYesterday = ammeterRepository.getRechargerAmountByDate(DateUtil.getStartDate(new DateTime().minusDays(1).toDate()), DateUtil.getEndDate(new DateTime().minusDays(1).toDate()));
                    BigDecimal rechargerAmountToday = ammeterRepository.getRechargerAmountByDate(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()));
                    jsonObject.put("昨日用电度数", rechargerAmountYesterday == null ? 0 : rechargerAmountYesterday);
                    jsonObject.put("昨日费用", rechargerAmountYesterday == null ? 0 : rechargerAmountYesterday.multiply(SiteConstants.ELECTRICITY_PRICE));
                    jsonObject.put("昨日过载报警次数", ammeterRepository.getOverLoadCountByDate(DateUtil.getStartDate(new DateTime().minusDays(1).toDate()), DateUtil.getEndDate(new DateTime().minusDays(1).toDate())));
                    jsonObject.put("今日用电度数", rechargerAmountToday == null ? 0 : rechargerAmountToday);
                    jsonObject.put("今日费用", rechargerAmountToday == null ? 0 : rechargerAmountToday.multiply(SiteConstants.ELECTRICITY_PRICE));
                    jsonObject.put("今日过载报警次数", ammeterRepository.getOverLoadCountByDate(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date())));
                }
            } else {
                jsonObject.put("昨日用电度数", 0);
                jsonObject.put("昨日费用", 0);
                jsonObject.put("昨日过载报警次数", 0);
                jsonObject.put("今日用电度数", 0);
                jsonObject.put("今日费用", 0);
                jsonObject.put("今日过载报警次数", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    //---------------------------------------------------------------
    @GetMapping("/rechargerAmount")
    @ApiOperation(value = "充电用电-前端")
    public JSONArray rechargerAmount(HttpServletRequest request) {
        return getRechargerAmountJsonArray(request, 1);
    }

    @GetMapping("/workAmount")
    @ApiOperation(value = "办公用电-前端")
    public JSONArray workAmount(HttpServletRequest request) {
        return getRechargerAmountJsonArray(request, 2);
    }

    @GetMapping("/airAmount")
    @ApiOperation(value = "空调用电-前端")
    public JSONArray airAmount(HttpServletRequest request) {
        return getRechargerAmountJsonArray(request, 3);
    }

    @GetMapping("/optAmount")
    @ApiOperation(value = "操作用电-前端")
    public JSONArray optAmount(HttpServletRequest request) {
        return getRechargerAmountJsonArray(request, 4);
    }

    @GetMapping("/otherAmount")
    @ApiOperation(value = "其他用电-前端")
    public JSONArray otherAmount(HttpServletRequest request) {
        return getRechargerAmountJsonArray(request, 5);
    }

    private JSONArray getRechargerAmountJsonArray(HttpServletRequest request, Integer type) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("value", 0);
        } else {
            if (user.getLevel() == 3) {
                jsonObject.put("value", ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), type));
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                jsonObject.put("value", ammeterRepository.getRechargerAmountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), type));
            } else if (user.getLevel() == 1) {
                if (user.getStid() != null) {
                    jsonObject.put("value", ammeterRepository.getRechargerAmountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), type, user.getStid()));
                } else {
                    jsonObject.put("value", ammeterRepository.getRechargerAmountByDateAndType(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), type));
                }
            } else {
                jsonObject.put("value", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    //---------------------------------------------------------------
    @GetMapping("/rechargerFee")
    @ApiOperation(value = "充电用电费用-前端")
    public JSONArray rechargerFee(HttpServletRequest request) {
        return getRechargerFeeJsonArray(request, 1);
    }

    @GetMapping("/workFee")
    @ApiOperation(value = "办公用电费用-前端")
    public JSONArray workFee(HttpServletRequest request) {
        return getRechargerFeeJsonArray(request, 2);
    }

    @GetMapping("/airFee")
    @ApiOperation(value = "空调用电费用-前端")
    public JSONArray airFee(HttpServletRequest request) {
        return getRechargerFeeJsonArray(request, 3);
    }

    @GetMapping("/optFee")
    @ApiOperation(value = "操作用电费用-前端")
    public JSONArray optFee(HttpServletRequest request) {
        return getRechargerFeeJsonArray(request, 4);
    }

    @GetMapping("/otherFee")
    @ApiOperation(value = "其他用电费用-前端")
    public JSONArray otherFee(HttpServletRequest request) {
        return getRechargerFeeJsonArray(request, 5);
    }

    private JSONArray getRechargerFeeJsonArray(HttpServletRequest request, Integer type) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("value", 0);
        } else {
            if (user.getLevel() == 3) {
                jsonObject.put("value", ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), type) == null ? 0 : ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), type).multiply(SiteConstants.ELECTRICITY_PRICE));
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                jsonObject.put("value", ammeterRepository.getRechargerAmountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), type) == null ? 0 : ammeterRepository.getRechargerAmountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), type).multiply(SiteConstants.ELECTRICITY_PRICE));
            } else if (user.getLevel() == 1) {
                if (user.getStid() != null) {
                    jsonObject.put("value", ammeterRepository.getRechargerAmountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), type, user.getStid()) == null ? 0 : ammeterRepository.getRechargerAmountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), type, user.getStid()).multiply(SiteConstants.ELECTRICITY_PRICE));
                } else {
                    jsonObject.put("value", ammeterRepository.getRechargerAmountByDateAndType(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), type) == null ? 0 : ammeterRepository.getRechargerAmountByDateAndType(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), type).multiply(SiteConstants.ELECTRICITY_PRICE));
                }
            } else {
                jsonObject.put("value", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    //---------------------------------------------------------------
    @GetMapping("/rechargerOver")
    @ApiOperation(value = "充电用电过载-前端")
    public JSONArray rechargerOver(HttpServletRequest request) {
        return getRechargerOverJsonArray(request, 1);
    }

    @GetMapping("/workOver")
    @ApiOperation(value = "办公用电过载-前端")
    public JSONArray workOver(HttpServletRequest request) {
        return getRechargerOverJsonArray(request, 2);
    }

    @GetMapping("/airOver")
    @ApiOperation(value = "空调用电过载-前端")
    public JSONArray airOver(HttpServletRequest request) {
        return getRechargerOverJsonArray(request, 3);
    }

    @GetMapping("/optOver")
    @ApiOperation(value = "操作用电过载-前端")
    public JSONArray optOver(HttpServletRequest request) {
        return getRechargerOverJsonArray(request, 4);
    }

    @GetMapping("/otherOver")
    @ApiOperation(value = "其他用电过载-前端")
    public JSONArray otherOver(HttpServletRequest request) {
        return getRechargerOverJsonArray(request, 5);
    }

    private JSONArray getRechargerOverJsonArray(HttpServletRequest request, Integer type) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("value", 0);
        } else {
            if (user.getLevel() == 3) {
                jsonObject.put("value", ammeterRepository.getOverLoadCountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), type));
            } else if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                jsonObject.put("value", ammeterRepository.getOverLoadCountByCityIdAndStidAndDateAndType(cityServer.getCityID(), cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), type));
            } else if (user.getLevel() == 1) {
                if (user.getStid() != null) {
                    jsonObject.put("value", ammeterRepository.getOverLoadCountByDateAndTypeAndStid(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), type, user.getStid()));
                } else {
                    jsonObject.put("value", ammeterRepository.getOverLoadCountByDateAndType(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), type));
                }
            } else {
                jsonObject.put("value", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }


    @GetMapping("/dotAmmeterSort")
    @ApiOperation(value = "用电排行-前端")
    public JSONArray dotAmmeterSort(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("x", 0);
            jsonObject.put("y", 0);
        } else {
            if (user.getLevel() == 2) {
                CityServer cityServer = cityServerRepository.findByCid(user.getCid());
                return JSONArrayUtil.toJSONArray(ammeterRepository.getDotServerSortByStidAndDateAndCityId(cityServer.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), cityServer.getCityID()));
            } else if (user.getLevel() == 1) {
                if (user.getStid() != null) {
                    return JSONArrayUtil.toJSONArray(ammeterRepository.getDotServerSortByStidAndDate(user.getStid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date())));
                }
            } else {
                jsonObject.put("x", 0);
                jsonObject.put("y", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    @GetMapping("/dotAmmeterMonthSort")
    @ApiOperation(value = "点位月份用电排行-前端")
    public JSONArray dotAmmeterMonthSort(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("x", 0);
            jsonObject.put("y", 0);
        } else {
            if (user.getLevel() == 3) {
                return JSONArrayUtil.toJSONArray(ammeterRepository.getDotServerMonthSortByDateAnddotId(DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), user.getDotid()));
            } else {
                jsonObject.put("x", 0);
                jsonObject.put("y", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    //---------------点位饼图--------------
    @GetMapping("/dotAmmeterPieChart")
    @ApiOperation(value = "点位区域用电饼图-前端")
    public JSONArray dotAmmeterPieChart(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("x", "全是零");
            jsonObject.put("y", 0);
        } else {
            if (user.getLevel() == 3) {
                JSONObject json1 = new JSONObject();
                json1.put("x", "充电用电");
                json1.put("y", ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 1));
                jsonArray.add(json1);
                JSONObject json2 = new JSONObject();
                json2.put("x", "办公区域用电");
                json2.put("y", ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 2));
                jsonArray.add(json2);
                JSONObject json3 = new JSONObject();
                json3.put("x", "空调用电");
                json3.put("y", ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 3));
                jsonArray.add(json3);
                JSONObject json4 = new JSONObject();
                json4.put("x", "操作区用电");
                json4.put("y", ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 4));
                jsonArray.add(json4);
                JSONObject json5 = new JSONObject();
                json5.put("x", "其他用电");
                json5.put("y", ammeterRepository.getRechargerAmountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 5));
                jsonArray.add(json5);
                return jsonArray;
            } else {
                jsonObject.put("x", "全是零");
                jsonObject.put("y", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    @GetMapping("/dotOverPieChart")
    @ApiOperation(value = "点位区域过载饼图-前端")
    public JSONArray dotOverPieChart(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("x", "全是零");
            jsonObject.put("y", 0);
        } else {
            if (user.getLevel() == 3) {
                JSONObject json1 = new JSONObject();
                json1.put("x", "充电用电");
                json1.put("y", ammeterRepository.getOverLoadCountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 1));
                jsonArray.add(json1);
                JSONObject json2 = new JSONObject();
                json2.put("x", "办公区域用电");
                json2.put("y", ammeterRepository.getOverLoadCountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 2));
                jsonArray.add(json2);
                JSONObject json3 = new JSONObject();
                json3.put("x", "空调用电");
                json3.put("y", ammeterRepository.getOverLoadCountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 3));
                jsonArray.add(json3);
                JSONObject json4 = new JSONObject();
                json4.put("x", "操作区用电");
                json4.put("y", ammeterRepository.getOverLoadCountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 4));
                jsonArray.add(json4);
                JSONObject json5 = new JSONObject();
                json5.put("x", "其他用电");
                json5.put("y", ammeterRepository.getOverLoadCountByDotIdAndDateAndType(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date()), 5));
                jsonArray.add(json5);
            } else {
                jsonObject.put("x", "全是零");
                jsonObject.put("y", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    //根据类型的用电量排行
    @GetMapping("/getTypeSortByDotId")
    @ApiOperation(value = "根据类型的用电量排行-前端")
    public JSONArray getTypeSortByDotId(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("x", 0);
            jsonObject.put("y", 0);
        } else {
            if (user.getLevel() == 3) {
                return JSONArrayUtil.toJSONArray(ammeterRepository.getTypeSortByDotIdAndDate(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date())));
            } else {
                jsonObject.put("x", 0);
                jsonObject.put("y", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    //根据类型的报警排行
    @GetMapping("/getTypeOverLoadSotrByDotId")
    @ApiOperation(value = "根据类型的报警排行-前端")
    public JSONArray getTypeOverLoadSotrByDotId(HttpServletRequest request) {
        User user = userService.checkUser(request);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("x", 0);
            jsonObject.put("y", 0);
        } else {
            if (user.getLevel() == 3) {
                return JSONArrayUtil.toJSONArray(ammeterRepository.getTypeOverLoadSotrByDotIdAndDate(user.getDotid(), DateUtil.getStartDate(new Date()), DateUtil.getEndDate(new Date())));
            } else {
                jsonObject.put("x", 0);
                jsonObject.put("y", 0);
            }
        }
        jsonArray.add(jsonObject);
        return jsonArray;
    }

}
