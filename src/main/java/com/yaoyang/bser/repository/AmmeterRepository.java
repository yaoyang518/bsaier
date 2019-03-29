package com.yaoyang.bser.repository;

import com.yaoyang.bser.entity.CityServer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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


    //用电排行榜
    @Query(value = "SELECT ds.dotname as name, SUM(electricity_consumption) as value from boxs bs,ammeter_daily_power adp,dot_server ds WHERE bs.iccid =adp.iccid and ds.dotid=bs.dotid and ds.stid=:stid " +
            " and adp.`date`>:start and adp.`date`<:end GROUP BY bs.dotid order by value desc", nativeQuery = true)
    JSONArray getDotServerSortByStidAndDate(@Param("stid") Long stid, @Param("start") Date start, @Param("end") Date end);

    //用电排行榜
    @Query(value = "SELECT ds.dotname as name, SUM(electricity_consumption) as value from boxs bs,ammeter_daily_power adp,dot_server ds ,sys_destrict sd " +
            "WHERE bs.iccid =adp.iccid and ds.dotid=bs.dotid and ds.stid=:stid and adp.`date`>:start and adp.`date`<:end  " +
            "and ds.districtID=sd.districtID and sd.cityID =:cityId GROUP BY bs.dotid order by value desc", nativeQuery = true)
    JSONArray getDotServerSortByStidAndDateAndCityId(@Param("stid") Long stid, @Param("start") Date start, @Param("end") Date end, @Param("cityId") Long cityId);

    //
    @Query(value = "select date_format(adp.`date`, '%Y-%m') name,sum(adp.electricity_consumption) as value  from boxs bs,ammeter_daily_power adp,dot_server ds " +
            "WHERE (adp.`date` BETWEEN :start AND :end) and bs.iccid =adp.iccid and ds.dotid=bs.dotid  and ds.dotid=:dotid  group by date_format(adp.`date`, '%Y-%m') ORDER BY value desc", nativeQuery = true)
    JSONArray getDotServerMonthSortByDateAnddotId( @Param("start") Date start, @Param("end") Date end, @Param("dotid") Long dotid);

    //网点类型用电排行
    @Query(value = "SELECT number as x,SUM(electricity_consumption) as y FROM ammeter_everyline_dailypower " +
            "WHERE  `date`>=:start and  `date`<=:end  and iccid in(select ICCID from boxs where staus=1 and dotid=:dotid) GROUP BY x ORDER BY y", nativeQuery = true)
    JSONArray getTypeSortByDotIdAndDate(@Param("dotid") Long dotid, @Param("start") Date start, @Param("end") Date end);

    //网点类型报警量排行
    @Query(value = "SELECT number as x,SUM(overload_time) as y FROM ammeter_everyline_dailypower WHERE `date`>=:start and  `date`<=:end and  " +
            "iccid in(select ICCID from boxs where staus=1 and dotid=:dotid) GROUP BY y ORDER BY y", nativeQuery = true)
    JSONArray getTypeOverLoadSotrByDotIdAndDate(@Param("dotid") Long dotid, @Param("start") Date start, @Param("end") Date end);

}
