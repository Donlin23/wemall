package me.donlin.wemall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Donlin
 * @Date: Created in 9:16 2018/7/11
 * @Version: 1.0
 * @Description:
 */
public interface IFileService {
    String upload(MultipartFile file, String path);
}
