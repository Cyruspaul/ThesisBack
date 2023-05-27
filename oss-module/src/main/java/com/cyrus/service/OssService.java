package com.cyrus.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface OssService {
    String uploadFileAvatar(MultipartFile file);

    String uploadFileScoreList(MultipartFile file);
}
