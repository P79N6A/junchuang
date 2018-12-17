package com.teachingplan.console.common;

import com.teachingplan.console.service.impl.user.UserService;
import org.apache.log4j.Logger;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by v_yanfzhang on 2018/1/24.
 */
public class CommonUtil {

    private static final Logger logger = Logger.getLogger(CommonUtil.class);

    /**
     * MD5加密
     * @param value
     * @return
     */
    public static String toMD5String(String value){
        String result=null;
        try {
            MessageDigest messageDigest=MessageDigest.getInstance("MD5");
            BASE64Encoder base64Encoder=new BASE64Encoder();
            result=base64Encoder.encode(messageDigest.digest(value.getBytes("utf-8")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(CommonUtil.toMD5String("rest"));
    }

    /**
     * 去除map中的空值
     * @param map
     */
    public static void removeNullValue(Map map){
        Set set = map.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
            Object obj = (Object) iterator.next();
            Object value =(Object)map.get(obj);
            remove(value, iterator);
        }
    }

    private static void remove(Object obj,Iterator iterator){
        if(obj instanceof String){
            String str = (String)obj;
            if(StringUtil.isNullOrBlank(str)){
                iterator.remove();
            }
        }else if(obj instanceof Collection){
            Collection col = (Collection)obj;
            if(col==null||col.isEmpty()){
                iterator.remove();
            }

        }else if(obj instanceof Map){
            Map temp = (Map)obj;
            if(temp==null||temp.isEmpty()){
                iterator.remove();
            }

        }else if(obj instanceof Object[]){
            Object[] array =(Object[])obj;
            if(array==null||array.length<=0){
                iterator.remove();
            }
        }else{
            if(obj==null){
                iterator.remove();
            }
        }
    }
}
