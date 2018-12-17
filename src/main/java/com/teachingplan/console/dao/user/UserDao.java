package com.teachingplan.console.dao.user;

import com.teachingplan.console.beans.user.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by LQW on 2017/11/1.
 */
public interface UserDao {

    Account findAccount(String account);

    List<Role> findRolesByAccountId(int accountId);

    List<Permission> findPermissionsByAccountId(int accountId);

    List<User> getUserList(Map<String, Object> params);

    int getUserTotalCount(Map<String, Object> params);

    List<User> getAllUserList(Map<String, Object> params);

    void addAccount(Account account);

    List<User> getAllSchools();

    void modAccountStatus(@Param("ids") List<String> ids, @Param("status") int status);

    String getSchoolIdByName(String name);

    Teacher getTeacherById(int id);

    void modTeacher(Teacher bean);

    void delTeacher(int id);

    Student getStudentById(int id);

    void modStudent(Student bean);

    void delStudent(int id);

    void delAccount(int id);

    Student getStudentByAccount(String account);

    Teacher getTeacherByAccount(String account);

    School getSchoolIdByAccount(String account);

    School getSchoolById(int schoolId);

    int getStuCountOfTeacher(Map<String, Object> queryMap);

    List<Student> getStuOfTeacher(Map<String, Object> queryMap);

    void clearSignIn();

    int getSignInsCount(Map<String, Object> params);

    List<Sign> getSignIns(Map<String, Object> params);

    Code getCodeByAccount(String account);

    void modPassword(@Param("account") String account, @Param("password") String password);

    void setCodeExpire(String account);

    String getTeacherMsg(int studentId);

    void delAccountRole(int id);

    void delAllAccount();

    Map<String,String> getVersion();
}
