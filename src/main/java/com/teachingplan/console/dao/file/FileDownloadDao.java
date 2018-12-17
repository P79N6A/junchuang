package com.teachingplan.console.dao.file;

import com.teachingplan.console.beans.file.FileDownload;
import org.apache.ibatis.annotations.Param;

/**
 * Created by v_yanfzhang on 2018/2/9.
 */
public interface FileDownloadDao {

    int getFileDownloadCountOfUser(@Param("accountId") int accountId, @Param("fileDownloadId") String fileDownloadId);

    FileDownload getFileDownloadById(int fileDownloadId);

    void modFileDownload(FileDownload fileDownload);

    void delFileDownload(int id);
}
