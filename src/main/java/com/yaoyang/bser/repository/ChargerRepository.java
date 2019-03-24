package com.yaoyang.bser.repository;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据
 *
 * @author yaoyang
 * @date 2018-08-10
 */
public interface ChargerRepository {


    Integer getDeviceChildCountByDtid(Long dtId);

    Integer getDeviceChildCountByDtidAndCityIdAndStid(Long dtId, Long cityId, Long stid);

    Integer getDeviceChildCountByDtidAndDotId(Long dtId, Long dotid);

    Integer getRechargerCountByUsed();

    Integer getRechargerCountByCityIdAndStidAndUsed(Long cityId, Long stid);

    Integer getRechargerCountByDotIdAndUsed(Long dotid);

    Integer getRechargerCountByRepair();

    Integer getRechargerCountByCityIdAndStidAndRepair(Long cityId, Long stid);

    Integer getRechargerCountByDotIdAndRepair(Long dotid);

    //增加是否去重
    Integer getRechargerRrequency(Boolean distinct);

    Integer getRechargerRrequencyByCityIdAndStid(Long cityId, Long stid, Boolean distinct);

    Integer getRechargerRrequencyByDotId(Long dotid, Boolean distinct);

    BigDecimal getRechargerAmountByDate(Date date);

    BigDecimal getRechargerAmountByCityIdAndStidAndDate(Long cityId, Long stid, Date date);

    BigDecimal getRechargerAmountByDotIdAndRepairDate(Long dotid, Date date);

}
