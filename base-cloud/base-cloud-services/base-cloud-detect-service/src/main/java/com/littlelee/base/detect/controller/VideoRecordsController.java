package com.littlelee.base.detect.controller;

import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.detect.mapper.VideoRecordsMapper;
import com.littlelee.base.detect.model.po.VideoRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/videoRecords")
public class VideoRecordsController {

    @Autowired
    private VideoRecordsMapper videoRecordsMapper;

    @PostMapping
    public ApiResult<?> save(@RequestBody VideoRecords videoRecords) {
        String ffmpeg = "ffmpeg -i ./runs/video/video_output.avi -vcodec libx264 ./runs/video/output.mp4 -y";
        System.out.println(videoRecords);
        videoRecordsMapper.insert(videoRecords);
        return ApiResult.success("记录成功");
    }

}
