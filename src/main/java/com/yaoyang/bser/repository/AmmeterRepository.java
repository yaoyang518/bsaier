package com.yaoyang.bser.repository;

import com.yaoyang.bser.entity.CityServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 智能电表
 *
 * @author yaoyang
 * @date 2019-03-24
 */
@Repository
public interface AmmeterRepository extends JpaRepository<CityServer, Long> {

    @Query(value = "SELECT SUM(electricity_consumption) FROM ammeter_daily_power WHERE `date`>=:start and  `date`<=:end and  " +
            "iccid in(select ICCID from boxs where staus=1 )", nativeQuery = true)
    BigDecimal getRechargerAmountByDate(@Param("start") Date start, @Param("end") Date end);

    @Query(value = "SELECT SUM(electricity_consumption) FROM ammeter_daily_power WHERE `date`>=:start and  `date`<=:end and  " +
            "iccid in(select ICCID from boxs where staus=1 and dotid in(SELECT dotid FROM dot_server WHERE stid =:stid))", nativeQuery = true)
    BigDecimal getRechargerAmountByDateAndStid(@Param("start") Date start, @Param("end") Date end, @Param("stid") Long stid);

    @Query(value = "SELECT SUM(electricity_consumption) FROM ammeter_daily_power WHERE `date`>=:start and  `date`<=:end and  " +
            "iccid in(select ICCID from boxs WHERE staus = 1 and dotid in(SELECT dotid FROM dot_server WHERE districtID in(SELECT districtID FROM sys_destrict WHERE cityID=:cityId) and stid =:stid))", nativeQuery = true)
    BigDecimal getRechargerAmountByCityIdAndStidAndDate(@Param("cityId") Long cityId, @Param("stid") Long stid, @Param("start") Date start, @Param("end") Date end);

    @Query(value = "SELECT SUM(electricity_consumption) FROM ammeter_daily_power WHERE `date`>=:start and  `date`<=:end and  " +
            "iccid in(select ICCID from boxs where staus=1 and dotid=:dotid)", nativeQuery = true)
    BigDecimal getRechargerAmountByDotIdAndDate(@Param("dotid") Long dotid, @Param("start") Date start, @Param("end") Date end);

    //----------
    @Query(value = "SELECT SUM(overload_time) FROM ammeter_daily_power WHERE `date`>=:start and  `date`<=:end and  " +
            "iccid in(select ICCID from boxs where staus=1 )", nativeQuery = true)
    BigDecimal getOverLoadCountByDate(@Param("start") Date start, @Param("end") Date end);

    @Query(value = "SELECT SUM(overload_time) FROM ammeter_daily_power WHERE `date`>=:start and  `date`<=:end and  " +
            "iccid in(select ICCID from boxs where staus=1 and dotid in(SELECT dotid FROM dot_server WHERE stid =:stid))", nativeQuery = true)
    BigDecimal getOverLoadCountByDateAndStid(@Param("start") Date start, @Param("end") Date end, @Param("stid") Long stid);

    @Query(value = "SELECT SUM(overload_time) FROM ammeter_daily_power WHERE `date`>=:start and  `date`<=:end and  " +
            "iccid in(select ICCID from boxs WHERE staus = 1 and dotid in(SELECT dotid FROM dot_server WHERE districtID in(SELECT districtID FROM sys_destrict WHERE cityID=:cityId) and stid =:stid))", nativeQuery = true)
    BigDecimal getOverLoadCountByCityIdAndStidAndDate(@Param("cityId") Long cityId, @Param("stid") Long stid, @Param("start") Date start, @Param("end") Date end);

    @Query(value = "SELECT SUM(overload_time) FROM ammeter_daily_power WHERE `date`>=:start and  `date`<=:end and  " +
            "iccid in(select ICCID from boxs where staus=1 and dotid=:dotid)", nativeQuery = true)
    BigDecimal getOverLoadCountByDotIdAndDate(@Param("dotid") Long dotid, @Param("start") Date start, @Param("end") Date end);

    //分类
    @Query(value = "SELECT SUM(electricity_consumption) FROM ammeter_everyline_dailypower WHERE `date`>=:start and  `date`<=:end and number=:type and  " +
            "iccid in(select ICCID from boxs where staus=1 )", nativeQuery = true)
    BigDecimal getRechargerAmountByDateAndType(@Param("start") Date start, @Param("end") Date end, @Param("type") Integer type);

    @Query(value = "SELECT SUM(electricity_consumption) FROM ammeter_everyline_dailypower WHERE `date`>=:start and  `date`<=:end and number=:type and  " +
            "iccid in(select ICCID from boxs where staus=1 and dotid in(SELECT dotid FROM dot_server WHERE stid =:stid))", nativeQuery = true)
    BigDecimal getRechargerAmountByDateAndTypeAndStid(@Param("start") Date start, @Param("end") Date end, @Param("type") Integer type, @Param("stid") Long stid);

    @Query(value = "SELECT SUM(electricity_consumption) FROM ammeter_everyline_dailypower WHERE `date`>=:start and  `date`<=:end and  number=:type and  " +
            "iccid in(select ICCID from boxs WHERE staus = 1 and dotid in(SELECT dotid FROM dot_server WHERE districtID in(SELECT districtID FROM sys_destrict WHERE cityID=:cityId) and stid =:stid))", nativeQuery = true)
    BigDecimal getRechargerAmountByCityIdAndStidAndDateAndType(@Param("cityId") Long cityId, @Param("stid") Long stid, @Param("start") Date start, @Param("end") Date end, @Param("type") Integer type);

    @Query(value = "SELECT SUM(electricity_consumption) FROM ammeter_everyline_dailypower WHERE `date`>=:start and  `date`<=:end and  number=:type and  " +
            "iccid in(select ICCID from boxs where staus=1 and dotid=:dotid)", nativeQuery = true)
    BigDecimal getRechargerAmountByDotIdAndDateAndType(@Param("dotid") Long dotid, @Param("start") Date start, @Param("end") Date end, @Param("type") Integer type);

    //--------
    @Query(value = "SELECT SUM(overload_time) FROM ammeter_everyline_dailypower WHERE `date`>=:start and  `date`<=:end and  number=:type and  " +
            "iccid in(select ICCID from boxs where staus=1 )", nativeQuery = true)
    Integer getOverLoadCountByDateAndType(@Param("start") Date start, @Param("end") Date end, @Param("type") Integer type);

    @Query(value = "SELECT SUM(overload_time) FROM ammeter_everyline_dailypower WHERE `date`>=:start and  `date`<=:end and  number=:type and  " +
            "iccid in(select ICCID from boxs where staus=1 and dotid in(SELECT dotid FROM dot_server WHERE stid =:stid))", nativeQuery = true)
    Integer getOverLoadCountByDateAndTypeAndStid(@Param("start") Date start, @Param("end") Date end, @Param("type") Integer type, @Param("stid") Long stid);

    @Query(value = "SELECT SUM(overload_time) FROM ammeter_everyline_dailypower WHERE `date`>=:start and  `date`<=:end and  number=:type and  " +
            "iccid in(select ICCID from boxs WHERE staus = 1 and dotid in(SELECT dotid FROM dot_server WHERE districtID in(SELECT districtID FROM sys_destrict WHERE cityID=:cityId) and stid =:stid))", nativeQuery = true)
    Integer getOverLoadCountByCityIdAndStidAndDateAndType(@Param("cityId") Long cityId, @Param("stid") Long stid, @Param("start") Date start, @Param("end") Date end, @Param("type") Integer type);

    @Query(value = "SELECT SUM(overload_time) FROM ammeter_everyline_dailypower WHERE `date`>=:start and  `date`<=:end and  number=:type and  " +
            "iccid in(select ICCID from boxs where staus=1 and dotid=:dotid)", nativeQuery = true)
    Integer getOverLoadCountByDotIdAndDateAndType(@Param("dotid") Long dotid, @Param("start") Date start, @Param("end") Date end, @Param("type") Integer type);


}
