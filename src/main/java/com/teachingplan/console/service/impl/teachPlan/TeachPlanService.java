package com.teachingplan.console.service.impl.teachPlan;

import com.teachingplan.console.beans.InsertArgs;
import com.teachingplan.console.beans.PagedSearchResult;
import com.teachingplan.console.beans.classes.Classes;
import com.teachingplan.console.beans.teachPlan.TeachPlan;
import com.teachingplan.console.beans.user.Account;
import com.teachingplan.console.beans.user.School;
import com.teachingplan.console.beans.user.Sign;
import com.teachingplan.console.beans.user.Teacher;
import com.teachingplan.console.common.CommonContent;
import com.teachingplan.console.common.FileUtil;
import com.teachingplan.console.common.StringUtil;
import com.teachingplan.console.dao.classes.ClassesDao;
import com.teachingplan.console.dao.teachPlan.TeachPlanDao;
import com.teachingplan.console.dao.user.UserDao;
import com.teachingplan.console.exception.ParameterException;
import com.teachingplan.console.service.impl.BaseService;
import com.teachingplan.console.service.impl.user.UserService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by LQW on 2018/1/27.
 */
@Service("teachPlanService")
public class TeachPlanService extends BaseService{

    @Autowired
    private TeachPlanDao teachPlanDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    private static final Logger logger = Logger.getLogger(TeachPlanService.class);

    public boolean addTeachPlan(TeachPlan teachPlan) {

        Map<String, Object> map = new HashMap<>();

        map.put("name",teachPlan.getName());
        map.put("description",teachPlan.getDescription());
        map.put("type",teachPlan.getType());
        map.put("course_id",teachPlan.getCourseId());
        map.put("scene_id",teachPlan.getSceneId());
        map.put("theme_id",teachPlan.getThemeId());
        map.put("summary",teachPlan.getSummary());
        map.put("prepare",teachPlan.getPrepare());
        map.put("prepare_file_path",teachPlan.getPrepareFilePath());
        map.put("prepare_file_name",teachPlan.getPrepareFileName());
        map.put("ppt_file_path",teachPlan.getPptFilePath());
        map.put("ppt_file_name",teachPlan.getPptFileName());

        String content = teachPlan.getContent();
        if (!StringUtil.isNullOrEmpty(content)) {
            String contentFileName = UUID.randomUUID().toString() + ".html";
            String proClassPath = System.getProperty("user.dir");
            String path = proClassPath + "/file/html/";
            try {
                FileUtil.uploadFile(content.getBytes(), path, contentFileName);
                content = "/file/html/" + contentFileName;
            } catch (Exception e) {
                logger.error("生成HTML文件失败：fileName：" + contentFileName);
                return false;
            }
        }
        map.put("content",content);
        InsertArgs insertArgs = new InsertArgs("teach_plan_t",map);

        super.addRecord(insertArgs);
        return true;
    }

    public TeachPlan getTeachPlanById(int teachPlanId) {

        return teachPlanDao.getTeachPlanById(teachPlanId);
    }

    public boolean modTeachPlan(TeachPlan teachPlan) {

        TeachPlan bean = teachPlanDao.getTeachPlanById(teachPlan.getId());

        if(null == bean) {
            logger.error("classes is not exist");
            throw new ParameterException(CommonContent.ILLEGAL_REQUEST);
        }

        String content = teachPlan.getContent();
        if (!StringUtil.isNullOrEmpty(content)) {
            String contentFileName = UUID.randomUUID().toString() + ".html";
            String proClassPath = System.getProperty("user.dir");
            String path = proClassPath + "/file/html/";
            try {
                FileUtil.uploadFile(content.getBytes(), path, contentFileName);
                content = "/file/html/" + contentFileName;
            } catch (Exception e) {
                logger.error("生成HTML文件失败：fileName：" + contentFileName);
                return false;
            }
        }
        teachPlan.setContent(content);
        teachPlanDao.modTeachPlan(teachPlan);
        return true;
    }

    public void delTeachPlan(int id) {

        TeachPlan bean = teachPlanDao.getTeachPlanById(id);

        if(null == bean) {
            logger.error("classes is not exist");
            throw new ParameterException(CommonContent.ILLEGAL_REQUEST);
        }
        // todo 检查是否有学校或教师正在使用教案，若正在使用，不允许删除

        teachPlanDao.delTeachPlan(id);

    }

    /**
     * 分配教案
     * @param teachPlanIds
     * @param type
     * @param accountId
     * @return
     */
    public boolean allotTeachPlan(String teachPlanIds, String type, int accountId) {

        if (!StringUtil.isNullOrEmpty(teachPlanIds)) {
            List<String> teachPlanList = Arrays.asList(teachPlanIds.split(","));
            for (int i=0; i<teachPlanList.size(); i++) {
                Map<String,Object> insertMap = new HashMap<>();
                insertMap.put("account_id",accountId);
                insertMap.put("teach_plan_id",teachPlanList.get(i));
                InsertArgs InsertArgs = new InsertArgs("user_teach_plan_t",insertMap);
                super.addRecord(InsertArgs);
            }
        }
        return true;
    }

    public boolean checkIsAllot(int id, String teachPlanIds) {

        if (!StringUtil.isNullOrEmpty(teachPlanIds)) {
            List<String> teachPlanList = Arrays.asList(teachPlanIds.split(","));
            for (int i=0; i<teachPlanList.size(); i++) {
                int count = teachPlanDao.getTeachPlanCountOfUser(id, teachPlanList.get(i));
                if (count > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 若分配的账号是教师账号，则判断账号是否是自己学校的
     *
     * @param schoolId
     * @param type
     * @param account
     * @return
     */
    public boolean isMySchoolUser(Integer schoolId, String type, String account) {

        // 分配给教师
        if (type.equals("2")) {

            Teacher teacher = userService.getTeacherByAccount(account);
            int schId = teacher.getSchoolId();

            if (schId != schoolId) {
                return false;
            }
        }

        return true;
    }

    public PagedSearchResult<TeachPlan> getDetailList(Map<String, Object> params, int rows, int page) {

        params.put("beginIndex",(page - 1) * rows);
        params.put("pageSize",rows);

        int totalCount = teachPlanDao.getDetailCount(params);
        int pageCount = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        List<TeachPlan> signList = teachPlanDao.getDetails(params);
        return new PagedSearchResult(page, totalCount, pageCount, signList, null);
    }
}
