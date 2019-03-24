package com.yaoyang.bser.repository;

import com.yaoyang.bser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户数据
 *
 * @author yaoyang
 * @date 2018-08-10
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("select entity from User entity where entity.username = :loginName and entity.password = :password and entity.userstaus=1 ")
    User findByUsernameAndPassword(@Param("loginName") String loginName, @Param("password") String password);

}
