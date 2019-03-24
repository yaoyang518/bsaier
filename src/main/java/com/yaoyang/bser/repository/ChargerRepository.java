package com.yaoyang.bser.repository;

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


}
