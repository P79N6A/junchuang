package com.teachingplan.console.dao;

import com.teachingplan.console.beans.BaseBean;
import com.teachingplan.console.beans.InsertArgs;
import com.teachingplan.console.beans.SelectArgs;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by v_yanfzhang on 2018/1/24.
 */
public interface BaseDao {

    int getTotalCount(SelectArgs selectArgs);

    List<Map<String,Object>> queryList(SelectArgs selectArgs);

    int addRecord (InsertArgs insertArgs);
}
