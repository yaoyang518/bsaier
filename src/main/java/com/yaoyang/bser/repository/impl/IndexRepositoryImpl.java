package com.yaoyang.bser.repository.impl;

import com.yaoyang.bser.repository.IndexRepository;
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
public class IndexRepositoryImpl implements IndexRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Integer getDeviceCountByDtid(Long dtId) {
        StringBuffer countSql = new StringBuffer("select COUNT(*) from boxs where staus=1 ");
        if (dtId != null) {
            countSql.append("and dtid=" + dtId);
        }
        Query query = entityManager.createNativeQuery(countSql.toString());
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    @Override
    public Integer getDeviceCountByDtidAndCityIdAndStid(Long dtId, Long cityId, Long stid) {
        StringBuffer countSql = new StringBuffer("select COUNT(*) from boxs WHERE staus = 1 and ");
        if (dtId != null) {
            countSql.append("dtid=" + dtId + " and ");
        }
        countSql.append("dotid in(SELECT dotid FROM dot_server WHERE districtID in(SELECT districtID FROM sys_destrict WHERE cityID=" + cityId + ") and stid =" + stid + ") ");
        Query query = entityManager.createNativeQuery(countSql.toString());
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    @Override
    public Integer getDeviceCountByDtidAndDotId(Long dtId, Long dotid) {
        StringBuffer countSql = new StringBuffer("select COUNT(*) from boxs where staus=1 and dotid= " + dotid);
        if (dtId != null) {
            countSql.append(" and dtid=" + dtId);
        }
        Query query = entityManager.createNativeQuery(countSql.toString());
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }
}

