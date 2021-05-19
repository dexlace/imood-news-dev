package com.dexlace.api.controller.files;

import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.model.bo.NewAdminBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/4
 */
@Api(value = "文件上传controller", tags = {"文件上传controller"})
@RequestMapping("fs")
public interface FileUploaderControllerApi {


    /**
     * 上传单文件 主要是face图
     * @param userId
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadFace")
    GraceIMOODJSONResult uploadFace(
            @RequestParam String userId,
            MultipartFile file) throws Exception;



    /**
     * 上传多文件
     */
    @PostMapping("/uploadSomeFiles")
    GraceIMOODJSONResult uploadSomeFiles(
            @RequestParam String userId,
            MultipartFile[] files) throws Exception;



    /**
     * 文件上传到mongodb的gridfs中
     * @param newAdminBO
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadToGridFS")
    GraceIMOODJSONResult uploadToGirdFS(
            @RequestBody NewAdminBO newAdminBO) throws Exception;


    /**
     * 从gridFS中读取文件
     * @param faceId
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("readInGridFS")
     void readInGridFS(
            String faceId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception;


    /**
     * 根据faceId获得admin的base64头像信息
     * @param faceId
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("readFace64InGridFS")
    GraceIMOODJSONResult readFace64InGridFS(
            @RequestParam String faceId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception;


}

