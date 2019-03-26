package com.yaoyang.bser.repository;

import com.yaoyang.bser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 数据
 *
 * @author yaoyang
 * @date 2018-08-10
 */
public interface IndexRepository {


    //@Query(value = "select COUNT(*) from boxs where staus=1 ", nativeQuery = true)
    Integer getDeviceCountByDtid(Long dtId);

    Integer getDeviceCountByDtidAndStid(Long dtId, Long stid);

    //@Query(value = "select COUNT(*) from boxs WHERE staus = 1 and dotid in(SELECT dotid FROM dot_server WHERE districtID in(SELECT districtID FROM sys_destrict WHERE cityID=:cityId) and stid =:stid) ", nativeQuery = true)
    Integer getDeviceCountByDtidAndCityIdAndStid(Long dtId, Long cityId, Long stid);

    //@Query(value = "select COUNT(*) from boxs where staus=1 and dotid=:dotid", nativeQuery = true)
    Integer getDeviceCountByDtidAndDotId(Long dtId, Long dotid);


}
