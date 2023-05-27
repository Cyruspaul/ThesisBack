package com.cyrus.controller;

import com.cyrus.service.OssService;
import com.cyrus.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/oss/file")
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像的方法
    @PostMapping("send")
    public R uploadOssFile(@RequestBody MultipartFile file) {
        //方法返回url路径
        System.out.println(file);
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url", url);
    }

    //上传成绩单的方法
    @PostMapping("scoreList")
    public R uploadScoreList(MultipartFile file) {
        //方法返回url路径
        String url = ossService.uploadFileScoreList(file);
        return R.ok().data("url", url);
    }
}
