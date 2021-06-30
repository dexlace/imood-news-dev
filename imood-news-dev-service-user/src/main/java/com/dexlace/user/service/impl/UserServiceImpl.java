package com.dexlace.user.service.impl;

import com.dexlace.common.enums.Sex;
import com.dexlace.common.enums.UserStatus;
import com.dexlace.common.exception.GraceException;
import com.dexlace.common.result.ResponseStatusEnum;
import com.dexlace.common.utils.DateUtil;
import com.dexlace.common.utils.DesensitizationUtil;
import com.dexlace.common.utils.JsonUtils;
import com.dexlace.common.utils.RedisOperator;
import com.dexlace.model.bo.UpdateUserInfoBO;
import com.dexlace.model.entity.AppUser;
import com.dexlace.user.mapper.AppUserMapper;
import com.dexlace.user.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/30
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AppUserMapper appUserMapper;
    @Autowired
    private Sid sid;

    @Autowired
    private RedisOperator redis;

    public static final String REDIS_USER_INFO = "redis_user_info";



    @Override
    public AppUser queryMobileIsExist(String mobile) {
        Example userExample = new Example(AppUser.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        // 前一个是属性，是AppUser的属性，后一个是我们传进来的值
        userCriteria.andEqualTo("mobile", mobile);
        AppUser user = appUserMapper.selectOneByExample(userExample);
        return user;
    }

    private static final String USER_FACE0 = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";
//      private static final String USER_FACE1 = "http://122.152.205.72:88/group1/M00/00/05/CpoxxF6ZUySASMbOAABBAXhjY0Y649.png";
//    private static final String USER_FACE2 = "http://122.152.205.72:88/group1/M00/00/05/CpoxxF6ZUx6ANoEMAABTntpyjOo395.png";

    @Transactional
    @Override
    public AppUser createUser(String mobile) {

        /**
         * 互联网项目都要考虑可扩展性，
         * 如果未来业务发展，需要分库分表，
         * 那么数据库表主键id必须保证全局（全库）唯一，不得重复
         */
        String userId = sid.nextShort();

        AppUser user = new AppUser();
        user.setId(userId);
        user.setMobile(mobile);
        // 脱敏
        user.setNickname("用户" + DesensitizationUtil.commonDisplay(mobile));
        // 默认头像
        user.setFace(USER_FACE0);

        // 默认生日
        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        // 默认性别为 保密
        user.setSex(Sex.secret.type);
        // 默认用户状态为未激活
        user.setActiveStatus(UserStatus.INACTIVE.type);
        // 默认用户总收入
        user.setTotalIncome(0);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());

        appUserMapper.insert(user);

        return user;
    }

    @Override
    public AppUser getUser(String userId) {
        return appUserMapper.selectByPrimaryKey(userId);
    }


    @Transactional
    @Override
    public void updateUserInfo(UpdateUserInfoBO updateUserInfoBO) {

        String userId = updateUserInfoBO.getId();


        // 用户更新数据前，先把缓存数据删除，然后更新数据库，再同步到redis中去
        // 哪怕redis存入不成功，那么后续用户发起的请求还可以先查库存后存缓存，达到一致性
        // 双写一致，先删除redis，在更新，再设置redis
        // 删第一次
        redis.del(REDIS_USER_INFO + ":" + userId);




        AppUser userInfo = new AppUser();
        BeanUtils.copyProperties(updateUserInfoBO, userInfo);

        userInfo.setUpdatedTime(new Date());
        userInfo.setActiveStatus(UserStatus.ACTIVE.type);

        int result = appUserMapper.updateByPrimaryKeySelective(userInfo);
        if (result != 1) {
            GraceException.display(ResponseStatusEnum.USER_UPDATE_ERROR);
        }

        // 更新数据库后一个正常的操作是写入redis
        // 再次查询用户最新信息，随后存入redis
        AppUser user = getUser(userId);
//        redis.set(REDIS_USER_INFO + ":" + userId, JsonUtils.objectToJson(user), 7);
        redis.set(REDIS_USER_INFO + ":" + userId, JsonUtils.objectToJson(user));

        // 缓存双删策略
        try {
            Thread.sleep(200);
            // 删第二次
            redis.del(REDIS_USER_INFO + ":" + userId);
            redis.set(REDIS_USER_INFO + ":" + userId, JsonUtils.objectToJson(user), 7);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }



}
