package com.dexlace.files.service.impl;

import com.dexlace.files.service.UploaderService;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/4
 */
@Service
public class UploaderServiceImpl implements UploaderService {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Override
    public String uploadFdfs(MultipartFile file, String fileExtName) throws Exception {

        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(),
                file.getSize(),
                fileExtName,
                null);

        String path = storePath.getFullPath();
        return path;
    }
}
