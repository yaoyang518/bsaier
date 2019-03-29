package com.yaoyang.bser.repository.impl;

import com.yaoyang.bser.constants.SiteConstants;
import com.yaoyang.bser.repository.ChargerRepository;
import com.yaoyang.bser.repository.IndexRepository;
import com.yaoyang.bser.util.DateUtil;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据
 *
 * @author yaoyang
 * @date 2019-03-24
 */
@Repository
public class ChargerRepositoryImpl implements ChargerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Integer getDeviceChildCountByDtid(Long dtId) {
        StringBuffer countSql = new StringBuffer("select sum(boxcount) from boxs where staus=1 ");
        if (dtId != null) {
            countSql.append("and dtid=" + dtId);
        }
        Query query = entityManager.createNativeQuery(countSql.toString());
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    @Override
    public Integer getDeviceChildCountByDtidAndStid(Long dtId, Long stid) {
        StringBuffer countSql = new StringBuffer("select sum(boxcount) from boxs where staus=1 ");
        if (dtId != null) {
            countSql.append("and dtid=" + dtId);
        }
        if (stid != null) {
            countSql.append(" and dotid in(SELECT dotid FROM dot_server WHERE stid =" + stid + ")");
        }
        Query query = entityManager.createNativeQuery(countSql.toString());
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    @Override
    public Integer getDeviceChildCountByDtidAndCityIdAndStid(Long dtId, Long cityId, Long stid) {
        StringBuffer countSql = new StringBuffer("select sum(boxcount) from boxs WHERE staus = 1 and ");
        if (dtId != null) {
            countSql.append(" dtid=" + dtId + " and ");
        }
        countSql.append("dotid in(SELECT dotid FROM dot_server WHERE districtID in(SELECT districtID FROM sys_destrict WHERE cityID=" + cityId + ") and stid =" + stid + ") ");
        Query query = entityManager.createNativeQuery(countSql.toString());
        if (query.getSingleResult() == null) {
            return new Integer(0);
        }
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    @Override
    public Integer getDeviceChildCountByDtidAndDotId(Long dtId, Long dotid) {
        StringBuffer countSql = new StringBuffer("select sum(boxcount) from boxs where staus=1 and dotid=" + dotid);
        if (dtId != null) {
            countSql.append(" and dtid=" + dtId);
        }
        Query query = entityManager.createNativeQuery(countSql.toString());
        if (query.getSingleResult() == null) {
            return new Integer(0);
        }
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    //------------------------------------------------

    @Override
    public Integer getRechargerCountByUsed() {
        StringBuffer countSql = new StringBuffer("select COUNT(*) from boxs where staus=1 and boxid in(SELECT DISTINCT (boxid) FROM boxes_child WHERE boxid in (SELECT boxid FROM boxs WHERE dtid=");
        countSql.append(SiteConstants.CHARGER_ID);
        countSql.append(") and is_use=1)");
        Query query = entityManager.createNativeQuery(countSql.toString());
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    @Override
    public Integer getRechargerCountByUsedAndStid(Long stid) {
        StringBuffer countSql = new StringBuffer("select COUNT(*) from boxs where staus=1 and boxid in(SELECT DISTINCT (boxid) FROM boxes_child WHERE boxid in (SELECT boxid FROM boxs WHERE dtid=");
        countSql.append(SiteConstants.CHARGER_ID);
        countSql.append(" and dotid in(SELECT dotid FROM dot_server WHERE stid =");
        countSql.append(stid);
        countSql.append(")) and is_use=1)");
        Query query = entityManager.createNativeQuery(countSql.toString());
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    @Override
    public Integer getRechargerCountByCityIdAndStidAndUsed(Long cityId, Long stid) {
        StringBuffer countSql = new StringBuffer("select COUNT(*) from boxs WHERE staus = 1 and  dtid=");
        countSql.append(SiteConstants.CHARGER_ID);
        countSql.append(" and dotid in(SELECT dotid FROM dot_server WHERE districtID in(SELECT districtID FROM sys_destrict WHERE cityID=");
        countSql.append(cityId);
        countSql.append(") and stid =");
        countSql.append(stid);
        countSql.append(") and boxid in (SELECT DISTINCT (boxid) FROM boxes_child WHERE boxid in (SELECT boxid FROM boxs WHERE dtid=");
        countSql.append(SiteConstants.CHARGER_ID);
        countSql.append(" ) and is_use=1) ");
        Query query = entityManager.createNativeQuery(countSql.toString());
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    @Override
    public Integer getRechargerCountByDotIdAndUsed(Long dotid) {
        StringBuffer countSql = new StringBuffer("select COUNT(*) from boxs where staus=1 and dotid= ");
        countSql.append(dotid);
        countSql.append(" and boxid in (SELECT DISTINCT (boxid) FROM boxes_child WHERE boxid in (SELECT boxid FROM boxs WHERE dtid=");
        countSql.append(SiteConstants.CHARGER_ID);
        countSql.append(") and is_use=1)");
        Query query = entityManager.createNativeQuery(countSql.toString());
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    //----------------------------------------------------------

    @Override
    public Integer getRechargerCountByRepair() {
        StringBuffer countSql = new StringBuffer("select COUNT(*) from boxs where staus=1 and boxid in(SELECT boxid FROM boxs_error WHERE staus =0)");
        Query query = entityManager.createNativeQuery(countSql.toString());
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    @Override
    public Integer getRechargerCountByRepairAndStid(Long stid) {
        StringBuffer countSql = new StringBuffer("select COUNT(*) from boxs where staus=1 and boxid in(SELECT boxid FROM boxs_error WHERE staus =0) and dotid in(SELECT dotid FROM dot_server WHERE stid =" + stid + ")");
        Query query = entityManager.createNativeQuery(countSql.toString());
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    @Override
    public Integer getRechargerCountByCityIdAndStidAndRepair(Long cityId, Long stid) {
        StringBuffer countSql = new StringBuffer("select COUNT(*) from boxs WHERE staus = 1 and dotid in(SELECT dotid FROM dot_server WHERE districtID in(SELECT districtID FROM sys_destrict WHERE cityID=");
        countSql.append(cityId);
        countSql.append(") and stid =");
        countSql.append(stid);
        countSql.append(") and boxid in(SELECT boxid FROM boxs_error WHERE staus =0)");
        Query query = entityManager.createNativeQuery(countSql.toString());
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    @Override
    public Integer getRechargerCountByDotIdAndRepair(Long dotid) {
        StringBuffer countSql = new StringBuffer("select COUNT(*) from boxs where staus=1 and dotid=");
        countSql.append(dotid);
        countSql.append(" and boxid in(SELECT boxid FROM boxs_error WHERE staus =0);");
        Query query = entityManager.createNativeQuery(countSql.toString());
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    //---------------------------------------------

    @Override
    public Integer getRechargerRrequency(Boolean distinct) {
        StringBuffer countSql = new StringBuffer("SELECT ");
        if (distinct) {
            countSql.append(" COUNT(DISTINCT(openid)) ");
        } else {
            countSql.append(" COUNT(*)");
        }
        countSql.append(" FROM charger_socket_usrrecord WHERE ");
        countSql.append(" boxid in (select boxid from boxs where staus=1 )");
        Query query = entityManager.createNativeQuery(countSql.toString());
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    @Override
    public Integer getRechargerRrequencyByStid(Boolean distinct, Long stid) {
        StringBuffer countSql = new StringBuffer("SELECT ");
        if (distinct) {
            countSql.append(" COUNT(DISTINCT(openid)) ");
        } else {
            countSql.append(" COUNT(*)");
        }
        countSql.append(" FROM charger_socket_usrrecord WHERE ");
        countSql.append(" boxid in (select boxid from boxs where staus=1 and dotid in(SELECT dotid FROM dot_server WHERE stid =" + stid + "))");
        Query query = entityManager.createNativeQuery(countSql.toString());
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    @Override
    public Integer getRechargerRrequencyByCityIdAndStid(Long cityId, Long stid, Boolean distinct) {
        StringBuffer countSql = new StringBuffer("SELECT ");
        if (distinct) {
            countSql.append(" COUNT(DISTINCT(openid)) ");
        } else {
            countSql.append(" COUNT(*)");
        }
        countSql.append(" FROM charger_socket_usrrecord WHERE ");
        countSql.append(" boxid in (select boxid from boxs WHERE staus = 1 and dotid in(SELECT dotid FROM dot_server WHERE districtID in(SELECT districtID FROM sys_destrict WHERE cityID=");
        countSql.append(cityId);
        countSql.append(") and stid =");
        countSql.append(stid);
        countSql.append("))");
        Query query = entityManager.createNativeQuery(countSql.toString());
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    @Override
    public Integer getRechargerRrequencyByDotId(Long dotid, Boolean distinct) {
        StringBuffer countSql = new StringBuffer("SELECT ");
        if (distinct) {
            countSql.append(" COUNT(DISTINCT(openid)) ");
        } else {
            countSql.append(" COUNT(*)");
        }
        countSql.append(" FROM charger_socket_usrrecord WHERE ");
        countSql.append(" boxid in (select boxid from boxs where staus=1 and dotid=");
        countSql.append(dotid);
        countSql.append(")");
        Query query = entityManager.createNativeQuery(countSql.toString());
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    @Override
    public BigDecimal getRechargerAmountByDate(Date date) {
        StringBuffer countSql = new StringBuffer("SELECT SUM(sumpower) FROM charger_socket_usrrecord WHERE ");
        countSql.append(" createtime>" + DateUtil.getStartDate(date));
        countSql.append(" and  createtime<" + DateUtil.getEndDate(date));
        countSql.append(" and boxid in (select boxid from boxs where staus=1 )");
        Query query = entityManager.createNativeQuery(countSql.toString());
        BigDecimal total = new BigDecimal(query.getSingleResult().toString());
        return total;
    }

    @Override
    public BigDecimal getRechargerAmountByDateAndStid(Date date, Long stid) {
        StringBuffer countSql = new StringBuffer("SELECT SUM(sumpower) FROM charger_socket_usrrecord WHERE ");
        countSql.append(" createtime>:start and  createtime<:end");
        countSql.append(" and boxid in (select boxid from boxs where staus=1 and dotid in(SELECT dotid FROM dot_server WHERE stid =" + stid + "))");
        Query query = entityManager.createNativeQuery(countSql.toString());
        query.setParameter("start", DateUtil.getStartDate(date));
        query.setParameter("end", DateUtil.getEndDate(date));
        if (query.getSingleResult() == null) {
            return new BigDecimal(0);
        }
        BigDecimal total = new BigDecimal(query.getSingleResult().toString());
        return total;
    }

    @Override
    public BigDecimal getRechargerAmountByCityIdAndStidAndDate(Long cityId, Long stid, Date date) {
        StringBuffer countSql = new StringBuffer("SELECT SUM(sumpower) FROM charger_socket_usrrecord WHERE ");
        countSql.append(" createtime> :start and  createtime< :end");
        countSql.append(" and boxid in (select boxid from boxs WHERE staus = 1 and dotid in(SELECT dotid FROM dot_server WHERE districtID in(SELECT districtID FROM sys_destrict WHERE cityID=");
        countSql.append(cityId);
        countSql.append(") and stid =");
        countSql.append(stid);
        countSql.append("))");
        Query query = entityManager.createNativeQuery(countSql.toString());
        query.setParameter("start", DateUtil.getStartDate(date));
        query.setParameter("end", DateUtil.getEndDate(date));
        if (query.getSingleResult() == null) {
            return new BigDecimal(0);
        }
        BigDecimal total = new BigDecimal(query.getSingleResult().toString());
        return total;
    }

    @Override
    public BigDecimal getRechargerAmountByDotIdAndDate(Long dotid, Date date) {
        StringBuffer countSql = new StringBuffer("SELECT SUM(sumpower) FROM charger_socket_usrrecord WHERE ");
        countSql.append(" createtime> :start and  createtime< :end ");
        countSql.append(" and boxid in (select boxid from boxs where staus=1 and dotid=");
        countSql.append(dotid);
        countSql.append(")");
        Query query = entityManager.createNativeQuery(countSql.toString());
        query.setParameter("start", DateUtil.getStartDate(date));
        query.setParameter("end", DateUtil.getEndDate(date));
        if (query.getSingleResult() == null) {
            return new BigDecimal(0);
        }
        BigDecimal total = new BigDecimal(query.getSingleResult().toString());
        return total;
    }

}

