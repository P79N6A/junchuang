package com.teachingplan.console.controller.file;

import com.teachingplan.console.common.FileUtil;
import com.teachingplan.console.common.StringUtil;
import com.teachingplan.console.exception.BusinessException;
import com.teachingplan.console.service.impl.file.PPT2HtmlService;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by v_yanfzhang on 2018/2/2.
 */
@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private PPT2HtmlService ppt2HtmlService;

    private static final Logger logger = Logger.getLogger(FileController.class);

    private static final long ONE_M = 1024 * 1024;

    //处理文件上传
    @PostMapping(value = "/uploadFile")
    public
    @ResponseBody
    Object uploadImg(@RequestParam("file") MultipartFile file, String type,
                     HttpServletRequest request) {

        Map<String, String> params = new HashMap<String, String>();

        String oldFileName = file.getOriginalFilename();

        if (StringUtil.isNullOrBlank(oldFileName)) {
            params.put("message", "fileError");
            return params;
        }

        double size = file.getSize() / ONE_M;
        if (size > 10.0) {
            logger.error("文件大小超过限制");
            throw new BusinessException("非法请求");
        }

        // 后缀
        String suffix = oldFileName.substring(oldFileName.lastIndexOf("."), oldFileName.length());
        String fileName = UUID.randomUUID().toString() + suffix;
        String proClassPath = System.getProperty("user.dir");
        String filePath = "/file/" + type + "/";
        String path = proClassPath + filePath;
        try {
            FileUtil.uploadFile(file.getBytes(), path, fileName);
        } catch (Exception e) {
            logger.error("上传文件失败：fileName：" + oldFileName);
            params.put("message", "error");
            return params;
        }
        // ppt转为html文件存储
        if ("ppt".equals(type)) {
            if (".pptx".equals(suffix)) {
                ppt2HtmlService.pptx2Html(oldFileName.substring(0,oldFileName.lastIndexOf(".") -1),path + fileName,"png");
            } else {
                ppt2HtmlService.ppt2Html(oldFileName.substring(0,oldFileName.lastIndexOf(".") -1),path + fileName,"png");
            }
        }
        //返回json
        params.put("message", "success");
        params.put("filePath", filePath + fileName);
        return params;
    }

    @RequestMapping("{type}/{filePath:.+}")
    public void getIcon(@PathVariable("type") String type, @PathVariable("filePath") String filePath,
                        HttpServletRequest request,
                        HttpServletResponse response) {


        String proClassPath = System.getProperty("user.dir");
        String fileName = proClassPath + "/file/" + type + "/" + filePath;

        File file = new File(fileName);

        //判断文件是否存在如果不存在就返回默认图标
        if (!(file.exists() && file.canRead())) {
            file = new File(request.getSession().getServletContext().getRealPath("/")
                    + "resource/icons/auth/root.png");
        }

        FileInputStream inputStream = null;
        OutputStream stream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(file);
            data = new byte[(int) file.length()];
            int length = inputStream.read(data);

            if(type.equals("video")) {
                response.setContentType("application/video");
            } else if (type.equals("package")){
                response.setContentType("application/octet-stream");
            }else {

                response.setContentType("image/png");
            }

            stream = response.getOutputStream();
            stream.write(data);
            stream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
                if (null != stream) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "downloadFile")
    public ResponseEntity<byte[]> download(HttpServletRequest request,
                                           @RequestParam(value = "filePath") String filePath, String fileName, Model model) throws Exception {

        String proClassPath = System.getProperty("user.dir");

        File file = new File(proClassPath + filePath);
        HttpHeaders headers = new HttpHeaders();
        // 下载显示的文件名，解决中文名称乱码问题
        String downloadFielName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
        // 通知浏览器以attachment（下载方式）打开图片
        headers.setContentDispositionFormData("attachment", downloadFielName);
        // application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }
}
