package com.yaoyang.bser.repository;

import com.yaoyang.bser.entity.CityServer;
import net.sf.json.JSONArray;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 智能烟感排行
 *
 * @author yaoyang
 * @date 2019-03-24
 */
@Repository
public interface SomkeSortRepository extends JpaRepository<CityServer, Long> {

    //城市排行榜
    @Query(value = "SELECT sc.cityName as x,SUM(sm.smoke_time) as y from smoke_daily sm,boxs bs,dot_server ds,sys_destrict sd,sys_city sc " +
            "WHERE sm.ICCID = bs.ICCID and bs.dotid=ds.dotid and sd.districtID =ds.districtID and sc.cityID=sd.cityID and ds.stid=:stid GROUP BY sc.cityID ORDER BY y DESC", nativeQuery = true)
    public JSONArray getSomkeTimeCitySortByStidAndUsed(@Param("stid") Long stid);

    //使用网点排行榜
    @Query(value = "SELECT ds.dotname as x,SUM(sm.smoke_time) as y from smoke_daily sm,boxs bs,dot_server ds,sys_destrict sd\n" +
            "WHERE sm.ICCID = bs.ICCID and bs.dotid=ds.dotid and sd.districtID =ds.districtID and  ds.stid=:stid and sd.cityID=:cityId GROUP BY ds.dotid ORDER BY y DESC", nativeQuery = true)
    public JSONArray getSomkeTimeDotSortByStidAndCityIDAndUsed(@Param("stid") Long stid, @Param("cityId") Long cityId);
}
