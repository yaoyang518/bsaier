package com.yaoyang.bser.repository;

import com.yaoyang.bser.entity.CityServer;
import net.sf.json.JSONArray;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 充电桩排行
 *
 * @author yaoyang
 * @date 2019-03-23
 */
@Repository
public interface ChargerSortRepository extends JpaRepository<CityServer, Long>, JpaSpecificationExecutor<CityServer> {


    //报修城市排行榜
    @Query(value = "SELECT sc.cityName as x,COUNT(*) as y from boxs bs,boxs_error bse ,dot_server ds,sys_destrict sd,sys_city sc " +
            "WHERE bs.ICCID=bse.ICCID and ds.dotid = bs.dotid and stid=:stid and sd.districtID=ds.districtID and sc.cityID=sd.cityID and bse.staus=1 ORDER BY y desc", nativeQuery = true)
    public JSONArray getRechargerCitySortByStid(@Param("stid") Long stid);

    //报修网点排行榜
    @Query(value = "SELECT ds.dotname as x,COUNT(*) as y from boxs bs,boxs_error bse ,dot_server ds " +
            "WHERE bs.ICCID=bse.ICCID and ds.dotid = bs.dotid and stid=:stid  and  bse.staus=0  GROUP BY(ds.dotid) ORDER BY y desc", nativeQuery = true)
    public JSONArray getRechargerDotSortByStid(@Param("stid") Long stid);

    //报修网点排行榜
    @Query(value = "SELECT ds.dotname as x,COUNT(*) as y from boxs bs,boxs_error bse ,dot_server ds,sys_destrict sd " +
            "WHERE bs.ICCID=bse.ICCID and ds.dotid = bs.dotid and stid=:stid and sd.districtID=ds.districtID and  bse.staus=0 and sd.cityID=:cityId GROUP BY(ds.dotid) ORDER BY y desc", nativeQuery = true)
    public JSONArray getRechargerDotSortByStidAndCityID(@Param("stid") Long stid, @Param("cityId") Long cityId);

    //------
    //使用城市排行榜
    @Query(value = "SELECT sc.cityName as x,COUNT(DISTINCT(bs.boxid)) as y FROM boxs bs, boxes_child bsc,dot_server ds,sys_destrict sd,sys_city sc " +
            "WHERE bs.dotid=ds.dotid and sd.districtID =ds.districtID and sc.cityID=sd.cityID and bs.boxid=bsc.boxid and bsc.is_use=1 and ds.stid=:stid GROUP BY sc.cityID ORDER BY y DESC", nativeQuery = true)
    public JSONArray getRechargerCitySortByStidAndUsed(@Param("stid") Long stid);

    //使用网点排行榜
    @Query(value = "SELECT ds.dotname as x,COUNT(DISTINCT(bs.boxid)) as y FROM boxs bs, boxes_child bsc,dot_server ds " +
            " WHERE bs.dotid=ds.dotid  and bs.boxid=bsc.boxid and bsc.is_use=1 and ds.stid=:stid  GROUP BY ds.dotid ORDER BY y DESC", nativeQuery = true)
    public JSONArray getRechargerDotSortByStidAndUsed(@Param("stid") Long stid);

    //使用网点排行榜
    @Query(value = "SELECT ds.dotname as x,COUNT(DISTINCT(bs.boxid)) as y FROM boxs bs, boxes_child bsc,dot_server ds,sys_destrict sd " +
            "WHERE bs.dotid=ds.dotid and sd.districtID =ds.districtID  and bs.boxid=bsc.boxid and bsc.is_use=1 and ds.stid=:stid and sd.cityID=:cityId GROUP BY ds.dotid ORDER BY y DESC", nativeQuery = true)
    public JSONArray getRechargerDotSortByStidAndCityIDAndUsed(@Param("stid") Long stid, @Param("cityId") Long cityId);

    //------
    //空闲城市排行榜
    @Query(value = "SELECT sc.cityName as x,COUNT(DISTINCT(bs.boxid)) as y FROM boxs bs, dot_server ds,sys_destrict sd,sys_city sc " +
            "WHERE bs.dotid=ds.dotid and sd.districtID =ds.districtID and sc.cityID=sd.cityID and bs.boxid not in(SELECT DISTINCT(bsc.boxid) FROM boxes_child bsc WHERE bsc.is_use=1) and ds.stid=:stid GROUP BY sc.cityID ORDER BY y DESC", nativeQuery = true)
    public JSONArray getRechargerCitySortByStidAndUnused(@Param("stid") Long stid);

    //空闲网点排行榜
    @Query(value = "SELECT ds.dotname as x,COUNT(DISTINCT(bs.boxid)) as y FROM boxs bs, dot_server ds " +
            "WHERE bs.dotid=ds.dotid  and  bs.boxid not in(SELECT DISTINCT(bsc.boxid) FROM boxes_child bsc WHERE bsc.is_use=1) and ds.stid=:stid  GROUP BY ds.dotid ORDER BY y DESC", nativeQuery = true)
    public JSONArray getRechargerDotSortByStidAndUnused(@Param("stid") Long stid);

    //空闲网点排行榜
    @Query(value = "SELECT ds.dotname as x,COUNT(DISTINCT(bs.boxid)) as y FROM boxs bs, dot_server ds,sys_destrict sd " +
            "WHERE bs.dotid=ds.dotid and sd.districtID =ds.districtID and  bs.boxid not in(SELECT DISTINCT(bsc.boxid) FROM boxes_child bsc WHERE bsc.is_use=1) and ds.stid=:stid and sd.cityID=:cityId GROUP BY ds.dotid ORDER BY y DESC", nativeQuery = true)
    public JSONArray getRechargerDotSortByStidAndCityIDAndUnused(@Param("stid") Long stid, @Param("cityId") Long cityId);


}
