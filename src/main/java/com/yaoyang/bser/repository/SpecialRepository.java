package com.yaoyang.bser.repository;


import com.yaoyang.bser.entity.CityServer;
import org.springframework.data.jpa.repository.JpaRepository;
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
public interface SpecialRepository extends JpaRepository<CityServer, Long> {

    @Query(value = "select sum(boxcount) from boxs  " +
            "WHERE staus = 1 and  dtid = :dtId and dotid in(SELECT dotid FROM dot_server WHERE districtID in(SELECT districtID FROM sys_destrict WHERE cityID = :cityId ) and stid = :stid)",nativeQuery = true)
    int getDeviceChildCountByDtidAndCityIdAndStidSpecial(@Param("dtId") Long dtId, @Param("cityId") Long cityId, @Param("stid") Long stid);

}
