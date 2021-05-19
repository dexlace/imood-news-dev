package com.dexlace.user.controller;

import com.dexlace.api.controller.BaseController;
import com.dexlace.api.controller.user.UserControllerApi;
import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.common.result.ResponseStatusEnum;
import com.dexlace.common.utils.JsonUtils;
import com.dexlace.model.bo.UpdateUserInfoBO;
import com.dexlace.model.entity.AppUser;
import com.dexlace.model.vo.AppUserVO;
import com.dexlace.model.vo.UserAccountInfoVO;
import com.dexlace.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/30
 */
@RestController
public class UserController extends BaseController implements UserControllerApi {

    final static Logger logger = LoggerFactory.getLogger(UserController.class);

    public static final String REDIS_WRITER_FANS_COUNTS = "redis_writer_fans_counts";
    public static final String REDIS_MY_FOLLOW_COUNTS = "redis_my_follow_counts";

    @Autowired
    private UserService userService;

    @Override
    public GraceIMOODJSONResult getAccountInfo(String userId) {

        // 0. 判断不能为空
        if (StringUtils.isBlank(userId)) {
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.UN_LOGIN);
        }

        // 1. 查询userId
        AppUser user = getUser(userId);

        // 2. 设置需要展示的信息，不用entity对象，entity有其他不能展示的信息
        UserAccountInfoVO accountVO = new UserAccountInfoVO();
        // 只要属性名一样就可以复制
        BeanUtils.copyProperties(user, accountVO);

        return GraceIMOODJSONResult.ok(accountVO);
    }


    @Override
    public GraceIMOODJSONResult getUserInfo(String userId) {
        // 0. 判断不能为空
        if (StringUtils.isBlank(userId)) {
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.UN_LOGIN);
        }
        // 1. 查询userId
        AppUser user = getUser(userId);

        // 2. 信息脱敏，设置不显示
        AppUserVO userVO = new AppUserVO();
        BeanUtils.copyProperties(user, userVO);
        Integer myFansCounts = getCountsFromRedis(REDIS_WRITER_FANS_COUNTS+":"+userId);
        Integer myFollowCounts = getCountsFromRedis(REDIS_MY_FOLLOW_COUNTS+":"+userId);


        // 3. 查询用户的粉丝数，关注数
        userVO.setMyFansCounts(myFansCounts);
        userVO.setMyFollowCounts(myFollowCounts);


        return GraceIMOODJSONResult.ok(userVO);
    }


    @Override
    public GraceIMOODJSONResult updateUserInfo(UpdateUserInfoBO updateUserInfoBO,
                                               BindingResult result) {

        // 判断BindingResult是否保存错误的验证信息，如果有，则直接return
        if (result.hasErrors()) {
            Map<String, String> errorMap = getErrors(result);
            return GraceIMOODJSONResult.errorMap(errorMap);
        }

        // 执行更新用户信息操作
        userService.updateUserInfo(updateUserInfoBO);
        return GraceIMOODJSONResult.ok();
    }


    private AppUser getUser(String userId) {
        // 1. 查询redis中是否包含用户信息，如果包含则查询redis返回，如果不包含则查询数据库
        String userJson = redis.get(REDIS_USER_INFO + ":" + userId);
        logger.info("userJson: " + userJson);

        AppUser user = null;
        if (StringUtils.isNotBlank(userJson)) {
            user = JsonUtils.jsonToPojo(userJson, AppUser.class);
        } else {
            user = userService.getUser(userId);
            // 2. 由于用户信息不怎么会变动，对于千万级别的网站，这类信息数据不会去查询数据库，完全可以把用户信息存入redis
            // 哪怕修改信息，也不会立马体现，这也是弱一致性，在这里有过期时间，比如1天以后，用户信息会更新到页面显示，或者缩短到1小时，都可以
            // 基本信息在新闻媒体类网站是属于数据一致性优先级比较低的，用户眼里看的主要以文章为主，至于文章是谁发的，一般来说不会过多关注
            redis.set(REDIS_USER_INFO + ":" + userId, JsonUtils.objectToJson(user));
        }
        return user;
    }

    @Override
    public GraceIMOODJSONResult queryByIds(String userIds) {
        if (StringUtils.isBlank(userIds)) {
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.USER_NOT_EXIST_ERROR);
        }

        List<AppUserVO> publisherList = new ArrayList<>();
        List<String> userIdList = JsonUtils.jsonToList(userIds, String.class);
        for (String uid : userIdList) {
            // 获得用户基本信息
            AppUserVO userVO = getBasicUserInfo(uid);
            // 添加到发布者list
            publisherList.add(userVO);
        }

        return GraceIMOODJSONResult.ok(publisherList);
    }

    /**
     * 获得用户基本信息
     *
     * @return
     */
    private AppUserVO getBasicUserInfo(String userId) {
        // 1. 根据userId查询用户的信息
        AppUser user = getUser(userId);
        // 2. 返回用户信息
        AppUserVO userVO = new AppUserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }




}
