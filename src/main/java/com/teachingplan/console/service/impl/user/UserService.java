package com.teachingplan.console.service.impl.user;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.teachingplan.console.beans.InsertArgs;
import com.teachingplan.console.beans.PagedSearchResult;
import com.teachingplan.console.beans.user.*;
import com.teachingplan.console.common.CommonContent;
import com.teachingplan.console.common.CommonUtil;
import com.teachingplan.console.common.PropertiesUtil;
import com.teachingplan.console.common.StringUtil;
import com.teachingplan.console.dao.classes.ClassesDao;
import com.teachingplan.console.dao.user.UserDao;
import com.teachingplan.console.exception.BusinessException;
import com.teachingplan.console.exception.ParameterException;
import com.teachingplan.console.service.impl.BaseService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by LQW on 2017/11/4.
 */
@Service("userService")
public class UserService extends BaseService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ClassesDao classesDao;

    private static final Logger logger = Logger.getLogger(UserService.class);

    public List<User> getUserList(Map<String,Object> params){
        return userDao.getUserList(params);
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public int getUserTotalCount(Map<String, Object> params) {
        return userDao.getUserTotalCount(params);
    }

    public List<User> getAllUserList(Map<String, Object> params) {
        return userDao.getAllUserList(params);
    }

    @Transactional
    public boolean addAccount(Map<String,Object> requestMap,String schoolId) {

        String account = (String) requestMap.get("account");
        if(checkAccountExist(account)) {
            logger.error("account is exist");
            throw new ParameterException(CommonContent.ILLEGAL_REQUEST);
        }

        String password = (String) requestMap.get("password");

        if (StringUtil.isNullOrEmpty(password)) {
            // 设置默认密码
            requestMap.put("password", CommonUtil.toMD5String(CommonContent.DEFAULT_PASSWORD));
        } else {
            requestMap.put("password", CommonUtil.toMD5String(password));
        }

        requestMap.put("mod_default",0);
        InsertArgs insertArgs = new InsertArgs("account_t",requestMap);
        super.addRecord(insertArgs);

        insertUser(requestMap,schoolId);

        return true;
    }

    private void insertUser(Map<String, Object> requestMap, String schoolId) {

        String account = (String) requestMap.get("account");
        String name = (String) requestMap.get("name");
        String type = (String) requestMap.get("type");
        String tableName = "";
        Account bean = userDao.findAccount(account);

        Map<String,Object> insertMap = new HashMap<>();
        switch(type) {
            case "1": // 学校
                schoolId = userDao.getSchoolIdByName(name);
                if (!StringUtil.isNullOrEmpty(schoolId)) {
                    insertMap.put("school_id",schoolId);
                }
                insertMap.put("name",name);
                insertMap.put("account_id",bean.getId());
                tableName = "school_t";
                InsertArgs schoolInsertArgs = new InsertArgs(tableName,insertMap);
                super.addRecord(schoolInsertArgs);
                break;
            case "2": // 教师
                insertMap.put("name",name);
                insertMap.put("remark",requestMap.get("remark"));
                insertMap.put("account_id",bean.getId());
                insertMap.put("school_id",schoolId);
                tableName = "teacher_t";
                InsertArgs teacherInsertArgs = new InsertArgs(tableName,insertMap);
                super.addRecord(teacherInsertArgs);
                insertUserClassesInfo((String) requestMap.get("classes"),bean.getId());
                break;
            case "3": // 学员
                insertMap.put("name",name);
                insertMap.put("account_id",bean.getId());
                insertMap.put("parent_phone",requestMap.get("parentPhone"));
                insertMap.put("birthday",requestMap.get("birthday"));
                insertMap.put("school_id",schoolId);
                tableName = "student_t";
                InsertArgs studentInsertArgs = new InsertArgs(tableName,insertMap);
                super.addRecord(studentInsertArgs);
                insertUserClassesInfo((String) requestMap.get("classes"),bean.getId());
                break;
            default:
                break;
        }

        insertUserRole(bean.getId(),bean.getType());

    }

    private void insertUserRole(int id, String type) {
        Map<String,Object> insertMap = new HashMap<>();
        insertMap.put("account_id",id);
        insertMap.put("role_id",type);

        InsertArgs teacherInsertArgs = new InsertArgs("account_role_t",insertMap);
        super.addRecord(teacherInsertArgs);

    }

    private void insertUserClassesInfo(String classes, int accountId) {

        if (!StringUtil.isNullOrEmpty(classes)) {
            List<String> classesList = Arrays.asList(classes.split(","));
            for (int i=0; i<classesList.size(); i++) {
                Map<String,Object> insertMap = new HashMap<>();
                insertMap.put("account_id",accountId);
                insertMap.put("classes_id",classesList.get(i));
                InsertArgs InsertArgs = new InsertArgs("user_classes_t",insertMap);
                super.addRecord(InsertArgs);
            }
        }
    }

    /**
     * 校验账号是否存在
     * @param account
     * @return
     */
    public boolean checkAccountExist(String account) {
        Account accountBean = userDao.findAccount(account);

        if (null == accountBean) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取所有的学校信息
     */
    public List<User> getAllSchools() {
        return userDao.getAllSchools();
    }

    /**
     * 根据账号ID修改账号状态
     * @param ids
     * @param status
     */
    public void modAccountStatus(List<String> ids, int status) {
        userDao.modAccountStatus(ids,status);
    }

    /**
     * 根据账号获取账号信息
     * @param account
     * @return
     */
    public Account findAccount(String account) {
        return userDao.findAccount(account);
    }

    @Transactional
    public boolean modTeacher(Teacher teacher) {

        Teacher bean = userDao.getTeacherById(teacher.getId());
        if(null == bean) {
            logger.error("teacher is not exist");
            throw new ParameterException(CommonContent.ILLEGAL_REQUEST);
        }

        bean.setName(teacher.getName());
        bean.setRemark(teacher.getRemark());
        userDao.modTeacher(bean);

        Account accountBean = userDao.findAccount(teacher.getAccount());
        classesDao.removeUserClasses(accountBean.getId());
        if (!StringUtil.isNullOrEmpty(teacher.getClasses())) {
            List<String> classesList = Arrays.asList(teacher.getClasses().split(","));
            for (int i=0; i<classesList.size(); i++) {
                Map<String,Object> insertMap = new HashMap<>();
                insertMap.put("account_id",accountBean.getId());
                insertMap.put("classes_id",classesList.get(i));
                InsertArgs InsertArgs = new InsertArgs("user_classes_t",insertMap);
                super.addRecord(InsertArgs);
            }
        }
        return true;
    }

    /**
     * 删除教师信息
     * @param id
     */
    @Transactional
    public void delTeacher(int id) {

        Teacher bean = userDao.getTeacherById(id);
        if (null == bean) {
            logger.error("teacher is not exist");
            throw new ParameterException(CommonContent.ILLEGAL_REQUEST);
        }
        Account accountBean = userDao.findAccount(bean.getAccount());
        userDao.delAccountRole(accountBean.getId());
        userDao.delTeacher(id);
        userDao.delAccount(accountBean.getId());
        classesDao.removeUserClasses(accountBean.getId());
    }

    @Transactional
    public boolean modStudent(Student student) {

        Student bean = userDao.getStudentById(student.getId());
        if(null == bean) {
            logger.error("Student is not exist");
            throw new ParameterException(CommonContent.ILLEGAL_REQUEST);
        }

        bean.setName(student.getName());
        bean.setParentPhone(student.getParentPhone());
        bean.setBirthday(student.getBirthday());
        userDao.modStudent(bean);

        Account accountBean = userDao.findAccount(student.getAccount());
        classesDao.removeUserClasses(accountBean.getId());
        if (!StringUtil.isNullOrEmpty(student.getClassesId())) {
            List<String> classesList = Arrays.asList(student.getClassesId().split(","));
            for (int i=0; i<classesList.size(); i++) {
                Map<String,Object> insertMap = new HashMap<>();
                insertMap.put("account_id",accountBean.getId());
                insertMap.put("classes_id",classesList.get(i));
                InsertArgs InsertArgs = new InsertArgs("user_classes_t",insertMap);
                super.addRecord(InsertArgs);
            }
        }
        return true;
    }

    @Transactional
    public void delStudent(int id) {

        Student bean = userDao.getStudentById(id);
        if (null == bean) {
            logger.error("Student is not exist");
            throw new ParameterException(CommonContent.ILLEGAL_REQUEST);
        }
        Account accountBean = userDao.findAccount(bean.getAccount());
        userDao.delAccountRole(accountBean.getId());
        userDao.delStudent(id);
        userDao.delAccount(accountBean.getId());
        classesDao.removeUserClasses(accountBean.getId());
    }

    public Student getStudentByAccount(String account) {

        return userDao.getStudentByAccount(account);
    }

    public Teacher getTeacherByAccount(String account) {

        return userDao.getTeacherByAccount(account);
    }

    public int getSchoolIdByAccount(String account) {

        School school = userDao.getSchoolIdByAccount(account);

        if (null != school) {
            if (school.getSchoolId() > 0) {
                return school.getSchoolId();
            } else {
                return school.getId();
            }
        }
        return 0;
    }

    public School getSchoolByAccount(String account) {

        School school = userDao.getSchoolIdByAccount(account);

        if (null != school) {
            if (school.getSchoolId() > 0) {
                School bean = userDao.getSchoolById(school.getSchoolId());
                return bean;
            } else {
                return school;
            }
        }
        return school;
    }

    public School getSchoolById(int schoolId) {
        return userDao.getSchoolById(schoolId);
    }

    public PagedSearchResult<Student> getStuOfTeacher(Map<String, Object> queryMap, int page, int rows) {

        int totalCount = userDao.getStuCountOfTeacher(queryMap);
        int pageCount = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        List<Student> students = userDao.getStuOfTeacher(queryMap);
        return new PagedSearchResult(page, totalCount, pageCount, students, null);
    }

    public void clearSignIn() {

        userDao.clearSignIn();
    }

    @Transactional
    public void signIn(int studentId) {

        Student bean = userDao.getStudentById(studentId);
        if(null == bean) {
            logger.error("Student is not exist");
            throw new ParameterException(CommonContent.ILLEGAL_REQUEST);
        }

        bean.setSign(1);
        userDao.modStudent(bean);

        Subject currentUser = SecurityUtils.getSubject();
        Account accountBean = (Account) currentUser.getSession().getAttribute("account");

        int operaterId = accountBean.getId();

        Map<String, Object> map = new HashMap<>();
        map.put("student_id",studentId);
        map.put("student_account",bean.getAccount());
        map.put("student_name",bean.getName());
        map.put("operater_id",operaterId);
        map.put("operater_name",accountBean.getAccount());
        if (accountBean.getType().equals("1")) {
            map.put("school_id",getSchoolByAccount(accountBean.getAccount()).getId());
            map.put("school_name",getSchoolByAccount(accountBean.getAccount()).getName());
        } else {

            School school = getSchoolById(bean.getSchoolId());
            map.put("school_id",school.getId());
            map.put("school_name",school.getName());
        }

        map.put("classes_name",bean.getClasses());
        map.put("sign_in_date",new Date());
        InsertArgs insertArgs = new InsertArgs("sign_in_t",map);

        super.addRecord(insertArgs);

    }

    public PagedSearchResult<Sign> getSignInList(Map<String, Object> params, int rows, int page) {

        params.put("beginIndex",(page - 1) * rows);
        params.put("pageSize",rows);

        int totalCount = userDao.getSignInsCount(params);
        int pageCount = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        List<Sign> signList = userDao.getSignIns(params);
        return new PagedSearchResult(page, totalCount, pageCount, signList, null);
    }

    @Transactional
    public void sendMessage(String studentIds, String content) {

        Subject currentUser = SecurityUtils.getSubject();
        Account accountBean = (Account) currentUser.getSession().getAttribute("account");
        Teacher teacher = getTeacherByAccount(accountBean.getAccount());

        if (!StringUtil.isNullOrEmpty(studentIds)) {
            List<String> studentIdList = Arrays.asList(studentIds.split(","));
            for (int i=0; i<studentIdList.size(); i++) {
                Map<String,Object> insertMap = new HashMap<>();
                insertMap.put("content",content);
                insertMap.put("student_id",studentIdList.get(i));
                insertMap.put("teacher_id",teacher.getId());
                InsertArgs InsertArgs = new InsertArgs("message_t",insertMap);
                super.addRecord(InsertArgs);
            }
        }

    }

    public Account getAccountByAccount(String account) {
        return userDao.findAccount(account);
    }

    @Transactional
    public String getCode(String phoneNum) {

        String code = getRandomCode();

        try {
            senCodeToPhone(code,phoneNum);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        Date now = new Date();
        Date expireTime = new Date(now.getTime() + 600000);

        Map<String,Object> insertMap = new HashMap<>();
        insertMap.put("account",phoneNum);
        insertMap.put("code",code);
        insertMap.put("expire_time",expireTime);
        InsertArgs InsertArgs = new InsertArgs("code_t",insertMap);
        super.addRecord(InsertArgs);

        return String.valueOf(code);
    }

    public String getRandomCode() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 6; i++) {

            int a = (int) (Math.random() * 10);
            if (a == 0 && i == 0) {
                s.append(a + 1);
            } else {
                s.append(a);
            }
        }
        return s.toString();
    }

    private SendSmsResponse senCodeToPhone(String code, String phoneNum) throws ClientException {

        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
//        System.setProperty("http.proxyHost", "dev-proxy.oa.com");
//        System.setProperty("http.proxyPort", "8080");
        //初始化ascClient需要的几个参数
        final String product = PropertiesUtil.getStringByKey("sms.product");//短信API产品名称（短信产品名固定，无需修改）
        final String domain = PropertiesUtil.getStringByKey("sms.domain");//短信API产品域名（接口地址固定，无需修改）
        //替换成你的AK
        final String accessKeyId = PropertiesUtil.getStringByKey("sms.accessKeyId");//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = PropertiesUtil.getStringByKey("sms.accessKeySecret");//你的accessKeySecret，参考本文档步骤2
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(phoneNum);
        //必填:短信签名-可在短信控制台中找到
        try {
            request.setSignName(new String(PropertiesUtil.getStringByKey("sms.signName").getBytes("ISO-8859-1"),"gbk"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(PropertiesUtil.getStringByKey("sms.templateCode"));
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam("{\"code\":\""+code+"\"}");
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//        request.setOutId("yourOutId");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        return  sendSmsResponse;
    }

    public void register(String account, String password, String name, int schoolId, String code) throws BusinessException{

        checkCode(account,code);

        if(checkAccountExist(account)) {
            throw new BusinessException("1003","账号已存在");
        }

        School school = userDao.getSchoolById(schoolId);
        if (null == school) {
            throw new BusinessException("1004","学校ID不正确");
        }

        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("account",account);
        requestMap.put("type","3");
        requestMap.put("name",name);
        requestMap.put("password",password);
        requestMap.put("modDefault","1");
        addAccount(requestMap,String.valueOf(schoolId));
    }

    private void checkCode(String account, String code) {

        Code codeBean =  userDao.getCodeByAccount(account);
        Date now = new Date();
        if (null == codeBean || !code.equals(codeBean.getCode())) {
            throw new BusinessException("1006","验证码错误");
        } else if (now.getTime() > codeBean.getExpireTime().getTime()) {
            throw new BusinessException("1007","验证码已过期");
        }

        userDao.setCodeExpire(account);
    }

    public void resetPassword(String account, String password, String code) throws BusinessException{

        checkCode(account,code);

        if(!checkAccountExist(account)) {
            throw new BusinessException("1005","账号不存在");
        }

        userDao.modPassword(account,CommonUtil.toMD5String(password));
    }

    public String getTeacherMsg(String studentAccount) {

        Student student = userDao.getStudentByAccount(studentAccount);
        if (null == student) {
            throw new BusinessException("1005","账号不存在");
        }

        return userDao.getTeacherMsg(student.getId());
    }

    public void modPassword(String account, String password) {
        userDao.modPassword(account,CommonUtil.toMD5String(password));
    }

    public void delAllUser() {
        userDao.delAllAccount();
    }

    public Map<String,String> getVersion() {

        return userDao.getVersion();
    }
}
