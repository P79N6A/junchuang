package com.teachingplan.console.service.impl;

import com.teachingplan.console.beans.BaseBean;
import com.teachingplan.console.beans.InsertArgs;
import com.teachingplan.console.beans.PagedSearchResult;
import com.teachingplan.console.beans.SelectArgs;
import com.teachingplan.console.beans.user.Student;
import com.teachingplan.console.beans.user.User;
import com.teachingplan.console.common.CommonUtil;
import com.teachingplan.console.common.MapUtil;
import com.teachingplan.console.dao.BaseDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by v_yanfzhang on 2018/1/24.
 */
public class BaseService {

    @Autowired
    private BaseDao baseDao;

    public PagedSearchResult queryListByPage(String tableName, int page, int rows, Map<String, String> params,Map<String, String> excludeParams,Class clazz) {
        return queryListByPage(tableName,page,rows,params,new HashMap<>(),new HashMap<>(),clazz);
    }

    /**
     * 分页查询数据
     *
     * @param page
     * @param rows
     * @param params
     * @return 带有分页的PagedSearchResult
     */
    public PagedSearchResult queryListByPage(String tableName, int page, int rows, Map<String, String> params,Class clazz) {

        return queryListByPage(tableName,page,rows,params,new HashMap<>(),clazz);

    }

    /**
     * 通用添加数据
     * @param insertArgs
     * @return
     */
    public boolean addRecord(InsertArgs insertArgs) {

        CommonUtil.removeNullValue(insertArgs.getParams());
        int count = baseDao.addRecord(insertArgs);
        if (count > 0) {
            return true;
        }
        return false;
    }

    public PagedSearchResult queryListWithEqualsCondition(String tableName, int page, int rows, Map<String, String> params, Map<String, String> equalsParams, Class clazz) {

        return queryListByPage(tableName,page,rows,params,new HashMap<>(),equalsParams,clazz);
    }

    private PagedSearchResult<User> queryListByPage(String tableName, int page, int rows, Map<String, String> params, HashMap<String, String> excludeParams, Map<String, String> equalsParams, Class clazz) {
        int beginIndex = (page - 1) * rows;

        CommonUtil.removeNullValue(params);
        CommonUtil.removeNullValue(excludeParams);
        CommonUtil.removeNullValue(equalsParams);
        SelectArgs selectArgs = new SelectArgs(tableName, beginIndex, rows, params,excludeParams,equalsParams);

        int totalCount = baseDao.getTotalCount(selectArgs);
        int pageCount = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        List<Map<String,Object>> lists = baseDao.queryList(selectArgs);
        List<Class> beanList= null;
        try {
            beanList = MapUtil.mapsToObjects(lists,clazz);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return new PagedSearchResult(page, totalCount, pageCount, beanList, null);
    }
}
