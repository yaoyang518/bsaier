package com.yaoyang.bser.repository;

import com.yaoyang.bser.entity.DotServer;
import com.yaoyang.bser.entity.User;
import net.sf.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户数据
 *
 * @author yaoyang
 * @date 2018-08-10
 */
@Repository
public interface DotServerRepository extends JpaRepository<DotServer, Long>, JpaSpecificationExecutor<DotServer> {

    @Query(value = "SELECT lat,lng,dotname AS info,'ok' as 'type' from dot_server where staus=1 and stid=:stid", nativeQuery = true)
    List<JSONObject> findDotByStid(@Param("stid") Long stid);

    @Query(value = " SELECT lat,lng,dotname AS info,'ok' as 'type' FROM dot_server  WHERE stid=:stid and districtID in(SELECT districtID FROM sys_destrict WHERE cityID=:cityId)", nativeQuery = true)
    List<JSONObject> findDotByStidAndCityId(@Param("stid") Long stid, @Param("cityId") Long cityId);

}
