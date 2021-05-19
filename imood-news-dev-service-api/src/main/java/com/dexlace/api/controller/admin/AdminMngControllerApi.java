package com.dexlace.api.controller.admin;

import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.model.bo.AdminLoginBO;
import com.dexlace.model.bo.NewAdminBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/4
 */
@Api(value = "管理员维护", tags = {"管理员维护controller"})
@RequestMapping("adminMng")
public interface AdminMngControllerApi {

    @PostMapping("/adminLogin")
    @ApiOperation(value = "管理员登录", notes = "管理员登录", httpMethod = "POST")
    GraceIMOODJSONResult adminLogin(@RequestBody AdminLoginBO adminLoginBO,
                                           HttpServletRequest request,
                                           HttpServletResponse response);


    @PostMapping("/adminIsExist")
    @ApiOperation(value = "查询管理人员是否存在", notes = "查询管理人员是否存在", httpMethod = "POST")
    GraceIMOODJSONResult adminIsExist(@RequestParam String username);



    @PostMapping("/addNewAdmin")
    @ApiOperation(value = "添加新的管理人员", notes = "添加新的管理人员", httpMethod = "POST")
    GraceIMOODJSONResult addNewAdmin(HttpServletRequest request,
                                       HttpServletResponse response,
                                       @RequestBody NewAdminBO newAdminBO);


    @PostMapping("/getAdminList")
    @ApiOperation(value = "查询管理人员列表", notes = "查询管理人员列表", httpMethod = "POST")
    GraceIMOODJSONResult getAdminList(@ApiParam(name = "page", value = "查询下一页的第几页", required = false)
                                        @RequestParam Integer page,
                                        @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
                                        @RequestParam Integer pageSize);


    @PostMapping("/adminLogout")
    @ApiOperation(value = "管理员注销", notes = "管理员注销", httpMethod = "POST")
    GraceIMOODJSONResult adminLogout(@RequestParam String adminId,
                                       HttpServletRequest request,
                                       HttpServletResponse response);


    @PostMapping("/adminFaceLogin")
    @ApiOperation(value = "管理员人脸登录", notes = "管理员人脸登录", httpMethod = "POST")
    GraceIMOODJSONResult adminFaceLogin(@RequestBody AdminLoginBO adminLoginBO,
                                          HttpServletRequest request,
                                          HttpServletResponse response);


}

