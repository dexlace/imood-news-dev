package com.dexlace.files.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/4
 */
public interface UploaderService {

   String uploadFdfs(MultipartFile file, String fileExtName) throws Exception;

}
