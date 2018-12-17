package com.teachingplan.console.service.impl.classes;

import com.teachingplan.console.beans.InsertArgs;
import com.teachingplan.console.beans.PagedSearchResult;
import com.teachingplan.console.beans.classes.Classes;
import com.teachingplan.console.beans.user.Account;
import com.teachingplan.console.beans.user.Student;
import com.teachingplan.console.common.CommonContent;
import com.teachingplan.console.common.CommonUtil;
import com.teachingplan.console.dao.classes.ClassesDao;
import com.teachingplan.console.exception.ParameterException;
import com.teachingplan.console.service.impl.BaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LQW on 2018/1/27.
 */
@Service("classesService")
public class ClassesService extends BaseService{

    @Autowired
    private ClassesDao classesDao;

    private static final Logger logger = Logger.getLogger(ClassesService.class);

    public boolean checkNameExist(String name, int schoolId) {

        Classes classes = classesDao.findClasses(name,schoolId);

        if (null == classes) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addClasses(Classes classes) {

        if(checkNameExist(classes.getName(),classes.getSchoolId())) {
            logger.error("classes name is exist");
            throw new ParameterException(CommonContent.ILLEGAL_REQUEST);
        }

        Map<String, Object> map = new HashMap<>();

        map.put("name",classes.getName());
        map.put("school_id",classes.getSchoolId());
        map.put("remark",classes.getRemark());
        InsertArgs insertArgs = new InsertArgs("classes_t",map);

        super.addRecord(insertArgs);
        return true;
    }

    public boolean modClasses(Classes classes) {

        Classes bean = classesDao.findClassesById(classes.getId());


        if(null == bean) {
            logger.error("classes is not exist");
            throw new ParameterException(CommonContent.ILLEGAL_REQUEST);
        }

        bean.setName(classes.getName());
        bean.setRemark(classes.getRemark());
        classesDao.modClasses(bean);
        return true;
    }

    public List<Classes> getClassBySchoolId(int schoolId) {

        return classesDao.getClassBySchoolId(schoolId);
    }

    public boolean hasUser(int id) {

       int count = classesDao.hasUser(id);
       if (count > 0) {
           return true;
       }
        return false;
    }

    public void delClasses(int id) {
        classesDao.delClasses(id);
    }

    public Classes findClassesById(int classesId) {
        return classesDao.findClassesById(classesId);
    }

    public void userUnbindClasses(int classId, int accountId) {

        classesDao.userUnbindClasses(classId,accountId);
    }

    public PagedSearchResult<Student> getClasses(Map<String, Object> params, int rows, int page) {

        params.put("beginIndex",(page - 1) * rows);
        params.put("pageSize",rows);

        int totalCount = classesDao.getClassesCount(params);
        int pageCount = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        List<Student> classesList = classesDao.getClassesList(params);
        return new PagedSearchResult(page, totalCount, pageCount, classesList, null);
    }
}
