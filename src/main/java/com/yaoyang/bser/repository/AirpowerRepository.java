package com.yaoyang.bser.repository;

import com.yaoyang.bser.entity.CityServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * 智能插座
 *
 * @author yaoyang
 * @date 2019-03-24
 */
@Repository
public interface AirpowerRepository extends JpaRepository<CityServer, Long> {

    //个数---
    @Query(value = "SELECT COUNT(*) FROM boxes_child WHERE  number=1 and childname=:type and is_use=:isUsed and boxid in(select boxid from boxs where staus=1 and dtid=2)", nativeQuery = true)
    Integer getAirpowerCountByTypeAndIsUsed(@Param("type") Integer type, @Param("isUsed") Integer isUsed);

    @Query(value = "SELECT COUNT(*) FROM boxes_child WHERE  number=1 and childname=:type and is_use=:isUsed and boxid in(select boxid from boxs where staus=1 and dtid=2 and dotid in(SELECT dotid FROM dot_server WHERE stid =:stid))", nativeQuery = true)
    Integer getAirpowerCountByTypeAndIsUsedAndStid(@Param("type") Integer type, @Param("isUsed") Integer isUsed,@Param("stid") Long stid);

    @Query(value = "SELECT COUNT(*) FROM boxes_child WHERE  number=1 and childname=:type and is_use=:isUsed and boxid in(select boxid from boxs WHERE staus = 1 and dtid=2 and dotid in(SELECT dotid FROM dot_server WHERE districtID in(SELECT districtID FROM sys_destrict WHERE cityID=:cityId) and stid =:stid) )", nativeQuery = true)
    Integer getAirpowerCountByTypeAndIsUsedAndCityIdAndStid(@Param("type") Integer type, @Param("isUsed") Integer isUsed, @Param("cityId") Long cityId, @Param("stid") Long stid);

    @Query(value = "SELECT COUNT(*) FROM boxes_child WHERE number=1 and childname=:type and is_use=:isUsed and boxid in(select boxid from boxs where staus=1 and dotid=:dotid and dtid=2)", nativeQuery = true)
    Integer getAirpowerCountByTypeAndIsUsedAndDotId(@Param("type") Integer type, @Param("isUsed") Integer isUsed, @Param("dotid") Long dotid);

    //度数---
    @Query(value = "SELECT SUM(sumpower) FROM airpower_daliy WHERE iccid in(SELECT ICCID FROM boxes_child WHERE number=1 and childname=:type and boxid in(select boxid from boxs where staus=1 and dtid=2))", nativeQuery = true)
    BigDecimal getRechargerAmountByType(@Param("type") Integer type);

    @Query(value = "SELECT SUM(sumpower) FROM airpower_daliy WHERE iccid in(SELECT ICCID FROM boxes_child WHERE number=1 and childname=:type and boxid in(select boxid from boxs where staus=1 and dtid=2 and dotid in(SELECT dotid FROM dot_server WHERE stid =:stid)))", nativeQuery = true)
    BigDecimal getRechargerAmountByTypeAndStid(@Param("type") Integer type,@Param("stid") Long stid);

    @Query(value = "SELECT SUM(sumpower) FROM airpower_daliy WHERE iccid in(SELECT ICCID FROM boxes_child WHERE number=1 and childname=:type and boxid in(select boxid from boxs where staus=1 and dtid=2 and dotid in(SELECT dotid FROM dot_server WHERE districtID in(SELECT districtID FROM sys_destrict WHERE cityID=:cityId) and stid =:stid) ))", nativeQuery = true)
    BigDecimal getRechargerAmountByTypeAndCityIdAndStid(@Param("type") Integer type, @Param("cityId") Long cityId, @Param("stid") Long stid);

    @Query(value = "SELECT SUM(sumpower) FROM airpower_daliy WHERE iccid in(SELECT ICCID FROM boxes_child WHERE number=1 and childname=:type and boxid in(select boxid from boxs where staus=1 and dotid=:dotid and dtid=2))", nativeQuery = true)
    BigDecimal getRechargerAmountByTypeAndDotId(@Param("type") Integer type, @Param("dotid") Long dotid);

    //控制数---
    @Query(value = "SELECT SUM(air_time) FROM airpower_daliy WHERE iccid in(SELECT ICCID FROM boxes_child WHERE number=1 and childname=:type and boxid in(select boxid from boxs where staus=1 and dtid=2))", nativeQuery = true)
    Integer getAirTimeCountByType(@Param("type") Integer type);

    @Query(value = "SELECT SUM(air_time) FROM airpower_daliy WHERE iccid in(SELECT ICCID FROM boxes_child WHERE number=1 and childname=:type and boxid in(select boxid from boxs where staus=1 and dtid=2 dotid in(SELECT dotid FROM dot_server WHERE stid =:stid)))", nativeQuery = true)
    Integer getAirTimeCountByTypeAndStid(@Param("type") Integer type,@Param("stid") Long stid);

    @Query(value = "SELECT SUM(air_time) FROM airpower_daliy WHERE iccid in(SELECT ICCID FROM boxes_child WHERE number=1 and childname=:type and boxid in(select boxid from boxs where staus=1 and dtid=2 and dotid in(SELECT dotid FROM dot_server WHERE districtID in(SELECT districtID FROM sys_destrict WHERE cityID=:cityId) and stid =:stid) ))", nativeQuery = true)
    Integer getAirTimeCountByTypeAndCityIdAndStid(@Param("type") Integer type, @Param("cityId") Long cityId, @Param("stid") Long stid);

    @Query(value = "SELECT SUM(air_time) FROM airpower_daliy WHERE iccid in(SELECT ICCID FROM boxes_child WHERE number=1 and childname=:type and boxid in(select boxid from boxs where staus=1 and dotid=:dotid and dtid=2))", nativeQuery = true)
    Integer getAirTimeCountByTypeAndDotId(@Param("type") Integer type, @Param("dotid") Long dotid);

}
