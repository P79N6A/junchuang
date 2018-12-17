package com.teachingplan.console.common;

import org.apache.commons.jcs.utils.props.PropertyLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by v_yanfzhang on 2018/2/26.
 */
public class ResourceLoader {

    private static ResourceLoader loader = new ResourceLoader();
    private static Map<String, Properties> loaderMap = new HashMap<String, Properties>();

    private ResourceLoader() {
    }

    public static ResourceLoader getInstance() {
        return loader;
    }

    /**
     * 根据文件名读取配置文件
     * @param propFile
     * @return
     * @throws Exception
     */
    public Properties getPropFromProperties(String propFile) throws Exception {

        Properties prop = loaderMap.get(propFile);
        if (prop != null)
        {
            return prop;
        }
        prop = PropertyLoader.loadProperties(propFile);

        loaderMap.put(propFile, prop);
        return prop;
    }
}
