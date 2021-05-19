package com.dexlace.user.service;

import com.dexlace.common.utils.PagedGridResult;

import java.util.Date;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/11
 */
public interface AppUserMngService {

    /**
     * 查询管理员列表
     */
    public PagedGridResult queryAllUserList(String nickname, Integer status,
                                            Date startDate, Date endDate,
                                            Integer page, Integer pageSize);


    /**
     * 冻结用户账号，或解除封号操作
     */
    public void freezeUserOrNot(String userId, Integer status);

}


