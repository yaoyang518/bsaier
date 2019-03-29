package com.yaoyang.bser.repository;

import com.yaoyang.bser.entity.CityServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 智能烟感
 *
 * @author yaoyang
 * @date 2019-03-24
 */
@Repository
public interface SomkeRepository extends JpaRepository<CityServer, Long> {

    //报警次数-------
    @Query(value = "SELECT SUM(smoke_time) FROM smoke_daily WHERE createtime>=:start and  createtime<=:end  and  iccid in(select ICCID from boxs where staus=1 )", nativeQuery = true)
    Integer getSmokeTimeByDate(@Param("start") Date start, @Param("end") Date end);

    @Query(value = "SELECT SUM(smoke_time) FROM smoke_daily WHERE createtime>=:start and  createtime<=:end  and  iccid in(select ICCID from boxs where staus=1 and dotid in(SELECT dotid FROM dot_server WHERE stid =:stid))", nativeQuery = true)
    Integer getSmokeTimeByDateAndStid(@Param("start") Date start, @Param("end") Date end,@Param("stid") Long stid);

    @Query(value = "SELECT SUM(smoke_time) FROM smoke_daily WHERE createtime>=:start and  createtime<=:end and   " +
            "iccid in(select ICCID from boxs WHERE staus = 1 and dotid in(SELECT dotid FROM dot_server WHERE districtID in(SELECT districtID FROM sys_destrict WHERE cityID=:cityId) and stid =:stid))", nativeQuery = true)
    Integer getSmokeTimeByCityIdAndStidAndDate(@Param("cityId") Long cityId, @Param("stid") Long stid, @Param("start") Date start, @Param("end") Date end);

    @Query(value = "SELECT SUM(smoke_time) FROM smoke_daily WHERE createtime>=:start and  createtime<=:end and  iccid in(select ICCID from boxs where staus=1 and dotid=:dotid)", nativeQuery = true)
    Integer getSmokeTimeByDotIdAndDate(@Param("dotid") Long dotid, @Param("start") Date start, @Param("end") Date end);

    //报警城市数---------
    @Query(value = "SELECT COUNT(DISTINCT(cityID)) FROM sys_destrict WHERE districtID in(SELECT DISTINCT(districtID) from dot_server WHERE  stid=:stid and dotid in(SELECT DISTINCT(dotid) FROM boxs WHERE iccid in (SELECT DISTINCT(ICCID) FROM smoke_daily WHERE smoke_time>0)))", nativeQuery = true)
    Integer getSmokeCityCountByStid(@Param("stid") Long stid);

    //报警网点数---------
    @Query(value = "SELECT COUNT(dotid) from dot_server WHERE stid =:stid and dotid in(SELECT DISTINCT(dotid) FROM boxs WHERE iccid in (SELECT DISTINCT(ICCID) FROM smoke_daily WHERE smoke_time>0))", nativeQuery = true)
    Integer getSmokeDotCountByStid(@Param("stid") Long stid);

    @Query(value = "SELECT COUNT(dotid) FROM (SELECT * from dot_server WHERE stid =:stid and dotid in(SELECT DISTINCT(dotid) FROM boxs WHERE iccid in (SELECT DISTINCT(ICCID) FROM smoke_daily WHERE smoke_time>0))) as dot JOIN (SELECT * FROM sys_destrict WHERE cityID =:cityId) as dis ON dot.districtID=dis.districtID", nativeQuery = true)
    Integer getSmokeDotCountByStidAndCityID(@Param("stid") Long stid, @Param("cityId") Long cityId);

}
