package com.yaoyang.bser.repository;

import com.yaoyang.bser.entity.CityServer;
import com.yaoyang.bser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 城市服务商
 *
 * @author yaoyang
 * @date 2019-03-23
 */
@Repository
public interface CityServerRepository extends JpaRepository<CityServer, Long>, JpaSpecificationExecutor<CityServer> {

    CityServer findByCid(Long cid);

}
