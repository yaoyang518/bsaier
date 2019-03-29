package com.yaoyang.bser.repository;

import com.yaoyang.bser.entity.CityServer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * 智能插座排序
 *
 * @author yaoyang
 * @date 2019-03-24
 */
@Repository
public interface AirpowerSortRepository extends JpaRepository<CityServer, Long> {

    //城市排行榜
    @Query(value = "SELECT ds.dotname as x,SUM(ad.air_time) as y from airpower_daliy ad,boxs bs,dot_server ds,sys_destrict sd,sys_city sc \n" +
            "WHERE ad.ICCID = bs.ICCID and bs.dotid=ds.dotid and sd.districtID =ds.districtID and sc.cityID=sd.cityID and ds.stid=:stid GROUP BY sc.cityID ORDER BY y DESC", nativeQuery = true)
    public JSONArray getAirTimeCitySortByStid(@Param("stid") Long stid);

    //使用网点排行榜
    @Query(value = "SELECT ds.dotname as x,SUM(ad.air_time) as y from airpower_daliy ad,boxs bs,dot_server ds " +
            "WHERE ad.ICCID = bs.ICCID and bs.dotid=ds.dotid  and  ds.stid=:stid  GROUP BY ds.dotid ORDER BY y DESC", nativeQuery = true)
    public JSONArray getAirTimeDotSortByStid(@Param("stid") Long stid);

    //使用网点排行榜
    @Query(value = "SELECT ds.dotname as x,SUM(ad.air_time) as y from airpower_daliy ad,boxs bs,dot_server ds,sys_destrict sd " +
            "WHERE ad.ICCID = bs.ICCID and bs.dotid=ds.dotid and sd.districtID =ds.districtID and  ds.stid=:stid and sd.cityID=:cityId GROUP BY ds.dotid ORDER BY y DESC", nativeQuery = true)
    public JSONArray getAirTimeDotSortByStidAndCityID(@Param("stid") Long stid, @Param("cityId") Long cityId);
}
