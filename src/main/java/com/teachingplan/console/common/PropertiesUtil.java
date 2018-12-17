package com.teachingplan.console.common;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by v_yanfzhang on 2018/2/26.
 */
public class PropertiesUtil {

    private static Logger logger = Logger.getLogger(PropertiesUtil.class);

    private static ResourceLoader loader = ResourceLoader.getInstance();
    private static Map<String, String> configMap = new HashMap<String, String>();
    private static final String DEFAULT_CONFIG_FILE = "/config.properties";

    private static Properties prop = null;

    /**
     * 读取 propName 中key的值
     * @param key
     * @param propName
     * @return
     */
    public static String getStringByKey(String key, String propName)
    {
        try
        {
            prop = loader.getPropFromProperties(propName);
        }
        catch (Exception e)
        {
            logger.error("getStringByKey from properties failed: key->"+key+ ",propName->" + propName);
            throw new RuntimeException(e);
        }
        key = key.trim();
        if (!configMap.containsKey(key))
        {
            if (prop.getProperty(key) != null)
            {
                configMap.put(key, prop.getProperty(key));
            }
        }
        return configMap.get(key);
    }

    /**
     * 读取默认配置文件的key的值 默认配置文件 config.properties
     * @param key
     * @return
     */
    public static String getStringByKey(String key)
    {
        return getStringByKey(key, DEFAULT_CONFIG_FILE);
    }
}
