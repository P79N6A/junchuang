package com.teachingplan.console.dao.video;

import com.teachingplan.console.beans.file.FileDownload;
import com.teachingplan.console.beans.video.Video;
import org.apache.ibatis.annotations.Param;

/**
 * Created by v_yanfzhang on 2018/2/9.
 */
public interface VideoDao {

    Video getVideoById(int fileDownloadId);

    void modVideo(Video video);

    void delVideo(int id);
}
