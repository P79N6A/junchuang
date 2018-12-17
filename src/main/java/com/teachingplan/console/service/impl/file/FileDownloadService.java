package com.teachingplan.console.service.impl.file;

import com.teachingplan.console.beans.InsertArgs;
import com.teachingplan.console.beans.file.FileDownload;
import com.teachingplan.console.common.CommonContent;
import com.teachingplan.console.common.StringUtil;
import com.teachingplan.console.dao.file.FileDownloadDao;
import com.teachingplan.console.exception.ParameterException;
import com.teachingplan.console.service.impl.BaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by v_yanfzhang on 2018/2/9.
 */
@Service("fileDownloadService")
public class FileDownloadService  extends BaseService {
    @Autowired
    private FileDownloadDao fileDownloadDao;
    private static final Logger logger = Logger.getLogger(FileDownloadService.class);


    public boolean addFileDownload(FileDownload fileDownload) {
        Map<String, Object> map = new HashMap<>();

        map.put("name",fileDownload.getName());
        map.put("description",fileDownload.getDescription());
        map.put("type",fileDownload.getType());
        map.put("sub_type_id",fileDownload.getSubTypeId());
        map.put("file_name",fileDownload.getFileName());
        map.put("file_path",fileDownload.getFilePath());
        InsertArgs insertArgs = new InsertArgs("file_download_t",map);
        super.addRecord(insertArgs);
        return true;
    }

    public boolean checkIsAllot(int id, String fileDownloads) {

        if (!StringUtil.isNullOrEmpty(fileDownloads)) {
            List<String> fileDownloadListList = Arrays.asList(fileDownloads.split(","));
            for (int i=0; i<fileDownloadListList.size(); i++) {
                int count = fileDownloadDao.getFileDownloadCountOfUser(id, fileDownloadListList.get(i));
                if (count > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean allotFileDownload(String fileDownloads, String type, int accountId) {
        if (!StringUtil.isNullOrEmpty(fileDownloads)) {
            List<String> fileDownloadList = Arrays.asList(fileDownloads.split(","));
            for (int i=0; i<fileDownloadList.size(); i++) {
                Map<String,Object> insertMap = new HashMap<>();
                insertMap.put("account_id",accountId);
                insertMap.put("file_download_id",fileDownloadList.get(i));
                InsertArgs InsertArgs = new InsertArgs("user_file_download_t",insertMap);
                super.addRecord(InsertArgs);
            }
        }
        return true;
    }

    public boolean modFileDownload(FileDownload fileDownload) {

        FileDownload bean = fileDownloadDao.getFileDownloadById(fileDownload.getId());

        if(null == bean) {
            logger.error("classes is not exist");
            throw new ParameterException(CommonContent.ILLEGAL_REQUEST);
        }

        bean.setName(fileDownload.getName());
        bean.setType(fileDownload.getType());
        bean.setSubTypeId(fileDownload.getSubTypeId());
        bean.setDescription(fileDownload.getDescription());
        bean.setFileName(fileDownload.getFileName());
        bean.setFilePath(fileDownload.getFilePath());
        fileDownloadDao.modFileDownload(bean);
        return true;
    }

    public void delFileDownload(int id) {

        FileDownload bean = fileDownloadDao.getFileDownloadById(id);

        if(null == bean) {
            logger.error("classes is not exist");
            throw new ParameterException(CommonContent.ILLEGAL_REQUEST);
        }
        // todo 检查是否有学校或教师正在使用教案，若正在使用，不允许删除

        fileDownloadDao.delFileDownload(id);
    }
}
