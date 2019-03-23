package com.yaoyang.bser.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * 查询工具类
 *
 * @author yaoyang
 * @date 2018/1/26
 */
public class QueryUtil {

    public static PageRequest buildPageRequest(int page, int size, String field, boolean desc) {
        Sort sort = null;
        if (StringUtil.isEmpty(field)) {
            sort = new Sort(Sort.Direction.DESC, "id");
        } else {
            if (desc) {
                sort = new Sort(Sort.Direction.DESC, field);
            } else {
                sort = new Sort(Sort.Direction.ASC, field);
            }
        }
        return new PageRequest(page - 1, size, sort);
    }
}
