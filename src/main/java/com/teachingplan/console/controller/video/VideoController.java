package com.teachingplan.console.controller.video;

import com.teachingplan.console.beans.PagedSearchResult;
import com.teachingplan.console.beans.category.Category;
import com.teachingplan.console.beans.file.FileDownload;
import com.teachingplan.console.beans.user.Account;
import com.teachingplan.console.beans.user.School;
import com.teachingplan.console.beans.user.User;
import com.teachingplan.console.beans.video.Video;
import com.teachingplan.console.service.impl.category.CategoryService;
import com.teachingplan.console.service.impl.file.FileDownloadService;
import com.teachingplan.console.service.impl.teachPlan.TeachPlanService;
import com.teachingplan.console.service.impl.user.UserService;
import com.teachingplan.console.service.impl.video.VideoService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by v_yanfzhang on 2018/2/9.
 */
@Controller
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;


    private static final Logger logger = Logger.getLogger(VideoController.class);

    @GetMapping("/videoList")
    public String videoList(Model model) {

        return "video/videoList";
    }

    @GetMapping("/searchData")
    @ResponseBody
    public Object searchData(Model model, String name, @RequestParam int rows, @RequestParam int page){

        Map<String,String> params = new HashMap<String,String>();
        String tableName = "video_v";

        params.put("name",name);
        Map<String,String> equalsParams = new HashMap<>();

        PagedSearchResult<Video> searchResult = videoService.queryListWithEqualsCondition(tableName,page,rows,params,equalsParams,Video.class);
        return searchResult;
    }

    @PostMapping("/addVideo")
    @ResponseBody
    public Object addFileDownload(Video video, Model model) {

        String message = "faild";
        Map<String, Object> map = new HashMap<>();
        boolean isSuccss = videoService.addVideo(video);
        if (isSuccss) {
            message="success";
        }
        map.put("message",message);
        return map;
    }

    @PostMapping("/modVideo")
    @ResponseBody
    public Object modVideo(Video video, Model model) {

        String message = "faild";
        Map<String, Object> map = new HashMap<>();
        boolean isSuccss = videoService.modVideo(video);
        if (isSuccss) {
            message="success";
        }
        map.put("message",message);
        return map;
    }

    @GetMapping("/delVideo")
    @ResponseBody
    public Object delVideo(Model model, int id){

        Map<String, String> map = new HashMap<>();
        videoService.delVideo(id);
        map.put("msg","success");
        return map;
    }
}
