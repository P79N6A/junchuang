package com.teachingplan.console.service.impl.video;

import com.teachingplan.console.beans.InsertArgs;
import com.teachingplan.console.beans.file.FileDownload;
import com.teachingplan.console.beans.video.Video;
import com.teachingplan.console.common.CommonContent;
import com.teachingplan.console.common.StringUtil;
import com.teachingplan.console.dao.file.FileDownloadDao;
import com.teachingplan.console.dao.video.VideoDao;
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
@Service("videoService")
public class VideoService extends BaseService {
    @Autowired
    private VideoDao videoDao;

    private static final Logger logger = Logger.getLogger(VideoService.class);


    public boolean addVideo(Video video) {
        Map<String, Object> map = new HashMap<>();

        map.put("name",video.getName());
        map.put("description",video.getDescription());
        map.put("file_name",video.getFileName());
        map.put("file_path",video.getFilePath());
        InsertArgs insertArgs = new InsertArgs("video_t",map);
        super.addRecord(insertArgs);
        return true;
    }

    public boolean modVideo(Video video) {

        Video bean = videoDao.getVideoById(video.getId());

        if(null == bean) {
            logger.error("classes is not exist");
            throw new ParameterException(CommonContent.ILLEGAL_REQUEST);
        }

        bean.setName(video.getName());
        bean.setDescription(video.getDescription());
        bean.setFileName(video.getFileName());
        bean.setFilePath(video.getFilePath());
        videoDao.modVideo(bean);
        return true;
    }

    public void delVideo(int id) {

        Video bean = videoDao.getVideoById(id);

        if(null == bean) {
            logger.error("video is not exist");
            throw new ParameterException(CommonContent.ILLEGAL_REQUEST);
        }
        videoDao.delVideo(id);
    }
}
