package com.yaoyang.bser.constants;

import com.yaoyang.bser.util.HttpUtil;
import net.sf.json.JSONObject;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 网站常量设置
 *
 * @author yaoyang
 * @date 2018/1/25
 */
public class SiteConstants {

    /**
     * 整型常量
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final long CHARGER_ID = 1L;

    public static final long AIRPOWER_ID = 2L;

    public static final long AMMETER_ID = 3L;

    public static final long SOMKE_ID = 4L;

    public static final int MANAGER_TYPE = 1;

    public static final int EMPLOYEE_TYPE = 2;

    public static final BigDecimal ELECTRICITY_PRICE = new BigDecimal(1.2);
}
