package com.teachingplan.console.dao.classes;

import com.teachingplan.console.beans.classes.Classes;
import com.teachingplan.console.beans.user.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by LQW on 2018/1/27.
 */
public interface ClassesDao {
    Classes findClasses(@Param("name") String name, @Param("schoolId") int schoolId);

    Classes findClassesById(int id);

    void modClasses(Classes bean);

    List<Classes> getClassBySchoolId(int schoolId);

    /**
     * 根据accountId删除教师/学生和班级的关系
     * @param accountId
     */
    void removeUserClasses(int accountId);

    /**
     * 该班级下是否有学生/教师
     * @param id
     * @return
     */
    int hasUser(int id);

    void delClasses(int id);

    void userUnbindClasses(@Param("classId")int classId, @Param("accountId")int accountId);

    List<Student> getClassesList(Map<String, Object> params);

    int getClassesCount(Map<String, Object> params);
}
