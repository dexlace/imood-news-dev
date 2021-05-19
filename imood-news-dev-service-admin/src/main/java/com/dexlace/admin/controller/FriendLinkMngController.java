package com.dexlace.admin.controller;

import com.dexlace.admin.service.FriendLinkService;
import com.dexlace.api.controller.BaseController;
import com.dexlace.api.controller.admin.FriendLinkMngControllerApi;
import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.model.bo.SaveFriendLinkBO;
import com.dexlace.model.mo.FriendLinkMO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/11
 */
@RestController
public class FriendLinkMngController extends BaseController implements FriendLinkMngControllerApi {

    final static Logger logger = LoggerFactory.getLogger(FriendLinkMngController.class);

    @Autowired
    private FriendLinkService friendLinkService;


    @Override
    public GraceIMOODJSONResult saveOrUpdateFriendLink(SaveFriendLinkBO saveFriendLinkBO,
                                                       BindingResult result) {
        // 判断BindingResult是否保存错误的验证信息，如果有，则直接return
        if (result.hasErrors()) {
            Map<String, String> errorMap = getErrors(result);
            return GraceIMOODJSONResult.errorMap(errorMap);
        }



        // 保存到MongoDB
        FriendLinkMO friendLinkMO = new FriendLinkMO();
        BeanUtils.copyProperties(saveFriendLinkBO, friendLinkMO);
        friendLinkMO.setCreateTime(new Date());
        friendLinkMO.setUpdateTime(new Date());

        friendLinkService.saveOrUpdateFriendLink(friendLinkMO);

        return GraceIMOODJSONResult.ok();
    }


    @Override
    public GraceIMOODJSONResult getFriendLinkList() {
        List<FriendLinkMO> list = friendLinkService.queryFriendLinkList();
        return GraceIMOODJSONResult.ok(list);
    }

    @Override
    public GraceIMOODJSONResult delete(String linkId) {
        friendLinkService.deleteFriendLink(linkId);
        return GraceIMOODJSONResult.ok();
    }

    @Override
    public GraceIMOODJSONResult getPortalFriendLinkList() {
        List<FriendLinkMO> list = friendLinkService.queryPortalFriendLinkList();
        return GraceIMOODJSONResult.ok(list);
    }



}

