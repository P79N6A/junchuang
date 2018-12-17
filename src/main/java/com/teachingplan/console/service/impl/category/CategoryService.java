package com.teachingplan.console.service.impl.category;

import com.teachingplan.console.beans.InsertArgs;
import com.teachingplan.console.beans.category.Category;
import com.teachingplan.console.beans.menu.Menu;
import com.teachingplan.console.common.CommonContent;
import com.teachingplan.console.dao.category.CategoryDao;
import com.teachingplan.console.dao.menu.MenuDao;
import com.teachingplan.console.exception.ParameterException;
import com.teachingplan.console.service.impl.BaseService;
import com.teachingplan.console.service.impl.classes.ClassesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by v_yanfzhang on 2018/1/31.
 */
@Service("categoryService")
public class CategoryService extends BaseService{

    @Autowired
    private CategoryDao categoryDao;

    private static final Logger logger = Logger.getLogger(CategoryService.class);

    public List<Category> getCategoryByType(int type) {
        List<Category> categoryList = categoryDao.getCategoryByType(type);
        for (Category category : categoryList) {
            getChildCategories(category);
        }
        return categoryList;
    }

    private void getChildCategories(Category category) {

        List<Category> childCategories = categoryDao.getChildCategories(category.getId());
        if (childCategories != null && childCategories.size() > 0) {
            category.setNodes(childCategories);
            for (Category c : childCategories) {
                getChildCategories(c);
            }
        }
    }

    public boolean checkNameIsExist(String text, int parentId) {

        Category category = categoryDao.findCategory(text,parentId);
        if (null == category) {
            return false;
        }
        return true;
    }

    public void addCategory(Category category) {
        Map<String, Object> map = new HashMap<>();

        map.put("text",category.getText());
        map.put("parent_id",category.getParentId());
        map.put("cover",category.getCover());
        map.put("file_path",category.getFilePath());
        InsertArgs insertArgs = new InsertArgs("category_t",map);

        super.addRecord(insertArgs);
    }

    public void modCategory(Category category) {

        Category bean = categoryDao.findCategoryById(category.getId());

        if(null == bean) {
            logger.error("classes is not exist");
            throw new ParameterException(CommonContent.ILLEGAL_REQUEST);
        }
        bean.setText(category.getText());
        bean.setCover(category.getCover());
        bean.setFilePath(category.getFilePath());
        categoryDao.modCategory(bean);
    }

    public Category findCategory(String text, int parentId) {
        Category category = categoryDao.findCategory(text,parentId);
        return category;
    }

    /**
     * 判断该类别下是否有教案或者子类别
     * @param id
     * @return
     */
    public boolean hasChildOrPlan(int id) {

        List<Category> categoryList = categoryDao.getChildCategories(id);
        if (null != categoryList && categoryList.size() > 0) {
            return true;
        }
        return false;
    }

    public void delCategory(int id) {
        categoryDao.delCategory(id);
    }

    /**
     * 根据subType查询类别详情
     *
     * @param type 1-教案，2-文件
     * @param subType 1-基础课程，2-特色课程，3-场景，4-主题/1-招生，2-教学，3-管理
     * @return
     */
    public List<Category> getCategoryBySubType(String type, String subType) {

        return categoryDao.getCategoryBySubType(type,subType);
    }
}
