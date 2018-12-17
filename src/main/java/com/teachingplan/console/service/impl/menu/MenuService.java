package com.teachingplan.console.service.impl.menu;

import com.teachingplan.console.beans.menu.Menu;
import com.teachingplan.console.dao.menu.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by v_yanfzhang on 2018/1/22.
 */
@Service("menuService")
public class MenuService {

    @Autowired
    private MenuDao menuDao;

    public List<Menu> getMenusByAccount(String account) {
        List<Menu> menuList = menuDao.getMenusByAccount(account);
        for (Menu menu : menuList) {
            getChildMenus(menu, account);
        }
        return menuList;
    }

    private void getChildMenus(Menu menu, String account) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("menuId", menu.getId());
        params.put("account", account);
        List<Menu> childMenus = menuDao.getChildMenus(params);
        if (childMenus != null && childMenus.size() > 0) {
            menu.setChildMenus(childMenus);
            for (Menu m : childMenus) {
                getChildMenus(m, account);
            }
        }
    }
}
