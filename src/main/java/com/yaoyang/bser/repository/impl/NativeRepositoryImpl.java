package com.yaoyang.bser.repository.impl;

import com.yaoyang.bser.repository.NativeRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * 数据
 *
 * @author yaoyang
 * @date 2019-03-24
 */
@Repository
public class NativeRepositoryImpl implements NativeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    //@Override
    public Integer getDeviceCountByDtid(Long dtId) {
        StringBuffer countSql = new StringBuffer("select COUNT(*) from boxs where staus=1 ");
        if (dtId != null) {
            countSql.append("and dtid=" + dtId);
        }
        Query query = entityManager.createNativeQuery(countSql.toString());
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

}

