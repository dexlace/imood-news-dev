package com.dexlace.admin.service;

import com.dexlace.common.utils.PagedGridResult;
import com.dexlace.model.bo.NewAdminBO;
import com.dexlace.model.entity.AdminUser;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/4
 */
public interface AdminUserService {

    /**
     * 获得管理员用户信息
     */
   AdminUser queryAdminByUsername(String username);
    /**
     * 新增管理员
     */
    void createAdminUser(NewAdminBO newAdminBO);


    /**
     * 查询管理员列表
     */
    PagedGridResult queryAdminList(Integer page, Integer pageSize);


}

