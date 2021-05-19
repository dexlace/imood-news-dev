package com.dexlace.user.service;

import com.dexlace.model.bo.UpdateUserInfoBO;
import com.dexlace.model.entity.AppUser;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/30
 */
public interface UserService {
    /**
     * 判断用户是否存在
     */
    public AppUser queryMobileIsExist(String mobile);

    /**
     * 创建用户
     */
    public AppUser createUser(String mobile);

    /**
     * 根据用户主键获得用户信息
     */
    public AppUser getUser(String userId);



    /**
     * 更新用户信息
     */
    public void updateUserInfo(UpdateUserInfoBO updateUserInfoBO);

}
