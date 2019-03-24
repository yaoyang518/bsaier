package com.yaoyang.bser.repository;

import com.yaoyang.bser.entity.CityServer;
import com.yaoyang.bser.entity.ServerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 服务商类型
 *
 * @author yaoyang
 * @date 2019-03-23
 */
@Repository
public interface ServerTypeRepository extends JpaRepository<ServerType, Long>, JpaSpecificationExecutor<ServerType> {

    ServerType findByStid(Long stid);

}
