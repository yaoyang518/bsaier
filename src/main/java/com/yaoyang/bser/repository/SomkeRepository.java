package com.yaoyang.bser.repository;

import com.yaoyang.bser.entity.CityServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 智能烟感
 *
 * @author yaoyang
 * @date 2019-03-24
 */
@Repository
public interface SomkeRepository extends JpaRepository<CityServer, Long> {


}
