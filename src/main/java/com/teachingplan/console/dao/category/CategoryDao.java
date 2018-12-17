package com.teachingplan.console.dao.category;

import com.teachingplan.console.beans.category.Category;
import com.teachingplan.console.beans.menu.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by v_yanfzhang on 2018/1/31.
 */
public interface CategoryDao {

    List<Category> getCategoryByType(int type);

    List<Category> getChildCategories(int parentId);

    Category findCategory(@Param("text") String text, @Param("parentId") int parentId);

    void modCategory(Category bean);

    Category findCategoryById(int id);

    void delCategory(int id);

    List<Category> getCategoryBySubType(@Param("type") String type, @Param("subType") String subType);
}
