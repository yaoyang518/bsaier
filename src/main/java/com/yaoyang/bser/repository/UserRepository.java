package com.yaoyang.bser.repository;

import com.yaoyang.bser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 用户数据
 *
 * @author yaoyang
 * @date 2018-08-10
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByMobileAndPassword(String loginName, String password);


}
