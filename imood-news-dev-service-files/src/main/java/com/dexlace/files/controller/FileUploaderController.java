package com.dexlace.files.controller;

import com.dexlace.api.controller.files.FileUploaderControllerApi;
import com.dexlace.common.exception.GraceException;
import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.common.result.ResponseStatusEnum;
import com.dexlace.common.utils.FileUtils;
import com.dexlace.files.resource.FileResource;
import com.dexlace.files.service.UploaderService;
import com.dexlace.model.bo.NewAdminBO;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.model.Filters;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/4
 */
@RestController
public class FileUploaderController implements FileUploaderControllerApi {

    final static Logger logger = LoggerFactory.getLogger(FileUploaderController.class);

    @Autowired
    private UploaderService uploaderService;

    @Autowired
    private FileResource fileResource;

    @Autowired
    private GridFSBucket gridFSBucket;


    public static final String TEMP_PATH="D:\\imood-face\\tempFace";

    public GraceIMOODJSONResult uploadFace(String userId, MultipartFile file) throws Exception {

        String path = "";
        // 开始文件上传，判断不能为空
        if (file != null) {
            // 获得文件上传的文件名称
            String fileName = file.getOriginalFilename();
            // 判断文件名后缀符合要求
            if (StringUtils.isNotBlank(fileName)) {
                // 文件重命名  imooc-face.png -> ["imooc-face", "png"]
                String fileNameArr[] = fileName.split("\\.");

                // 获取文件的后缀名
                String suffix = fileNameArr[fileNameArr.length - 1];
                if (!suffix.equalsIgnoreCase("png") &&
                        !suffix.equalsIgnoreCase("jpg") &&
                        !suffix.equalsIgnoreCase("jpeg")) {
                    return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.FILE_FORMATTER_FAILD);
                }
                path = uploaderService.uploadFdfs(file, suffix);
            }
        } else {
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_NULL_ERROR);
        }


        String finalUserFaceUrl = "";
        if (StringUtils.isNotBlank(path)) {
            finalUserFaceUrl = fileResource.getHost() + path;
        } else {
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }

        logger.info("finalUserFaceUrl = " + finalUserFaceUrl);
        return GraceIMOODJSONResult.ok(finalUserFaceUrl);
    }

    @Override
    public GraceIMOODJSONResult uploadSomeFiles(String userId, MultipartFile[] files) throws Exception {
        // 声明list，用于存放多个图片的地址路径，返回到前端
        List<String> imageUrlList = new ArrayList<>();
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                String path = "";
                if (file != null) {
                    // 获得文件上传的名称
                    String fileName = file.getOriginalFilename();

                    // 判断文件名不能为空
                    if (StringUtils.isNotBlank(fileName)) {
                        String fileNameArr[] = fileName.split("\\.");
                        // 获得后缀
                        String suffix = fileNameArr[fileNameArr.length - 1];
                        // 判断后缀符合我们的预定义规范
                        if (!suffix.equalsIgnoreCase("png") &&
                                !suffix.equalsIgnoreCase("jpg") &&
                                !suffix.equalsIgnoreCase("jpeg")
                        ) {
                            continue;
                        }

                        // 执行上传
                        path = uploaderService.uploadFdfs(file, suffix);


                    } else {
                        continue;
                    }
                } else {
                    continue;
                }

                String finalPath = "";
                if (StringUtils.isNotBlank(path)) {
                    finalPath = fileResource.getHost() + path;
//                    finalPath = fileResource.getOssHost() + path;
                    // FIXME: 放入到imagelist之前，需要对图片做一次审核
                    imageUrlList.add(finalPath);
                } else {
                    continue;
                }
            }
        }

        return GraceIMOODJSONResult.ok(imageUrlList);
    }

    @Override
    public GraceIMOODJSONResult uploadToGirdFS(NewAdminBO newAdminBO) throws Exception {
            // base64字符串
            String file64 = newAdminBO.getImg64();
            // 将字符串转换为byte数组
            byte[] bytes = new BASE64Decoder().decodeBuffer(file64.trim());
            // 转化为输入流
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

            // 上传
            ObjectId fileId = gridFSBucket.uploadFromStream(newAdminBO.getUsername() + ".png", inputStream);

            logger.info("上传完成。 文件ID：{}",fileId);

            // 文件在mongodb中的id
            String fileIDStr = fileId.toString();

            logger.info("fileIDStr={}",fileIDStr);

            return GraceIMOODJSONResult.ok(fileIDStr);


    }

    @Override
    public void readInGridFS(String faceId,
                             HttpServletRequest request,
                             HttpServletResponse response) throws Exception {

        if (StringUtils.isBlank(faceId) || faceId.equalsIgnoreCase("null")) {
            GraceException.display(ResponseStatusEnum.FILE_NOT_EXIST_ERROR);
        }

        // 获取内容
        File file = readGridFSByFaceId(faceId);


        // 读取一个文件，并显示在浏览器
        FileUtils.downloadFileByStream(response, file);
    }


    /**
     * 从GridFS中读取文件内容
     * @param faceId
     * @return
     */
    private File readGridFSByFaceId(String faceId) throws Exception {
        // 获取内容
        GridFSFindIterable gridFSFindIterable = null;
        try {
            // 查找并获得gridFS的迭代器
            gridFSFindIterable = gridFSBucket.find(Filters.eq("_id", new ObjectId(faceId)));
        } catch (IllegalArgumentException e) {
            GraceException.display(ResponseStatusEnum.FILE_NOT_EXIST_ERROR);
        }
        // 获得第一个gridFSFile值
        GridFSFile gridFSFile = gridFSFindIterable.first();

        if (gridFSFile == null) {
            GraceException.display(ResponseStatusEnum.FILE_NOT_EXIST_ERROR);
        }

        System.out.println("filename: " + gridFSFile.getFilename());
        String fileName = gridFSFile.getFilename();

        // 获取文件流，定义存放位置和名称
//        String tempPath = "/workspaces/temp_face";
        File fileTemp = new File(TEMP_PATH);
        if (!fileTemp.exists()) {
            fileTemp.mkdirs();
        }

        File file = new File(TEMP_PATH + "\\" + fileName);

        // 创建输出流
        OutputStream os = new FileOutputStream(file);
        // 执行下载，下载到本地磁盘，管理员头像不多，下载到服务器不会占用大量硬盘空间
        gridFSBucket.downloadToStream(new ObjectId(faceId), os);

        return file;
    }

    @Override
    public GraceIMOODJSONResult readFace64InGridFS(String faceId,
                                              HttpServletRequest request,
                                              HttpServletResponse response) throws Exception {
        // 1. 判断faceId不能为空
        if (StringUtils.isBlank(faceId)) {
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.FILE_NOT_EXIST_ERROR);
        }

        // 2. 获得文件
        File file = readGridFSByFaceId(faceId);

        // 3. 把文件转换为base64并返回给调用方
        String base64 = FileUtils.fileToBase64(file);

        return GraceIMOODJSONResult.ok(base64);
    }

}

