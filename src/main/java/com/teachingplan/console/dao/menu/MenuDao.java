package com.teachingplan.console.dao.menu;

import com.teachingplan.console.beans.menu.Menu;

import java.util.List;
import java.util.Map;

/**
 * Created by v_yanfzhang on 2018/1/22.
 */
public interface MenuDao {

    List<Menu> getMenusByAccount(String account);

    List<Menu> getChildMenus(Map<String,Object> params);
}
