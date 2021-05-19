package com.dexlace.admin.service;

import com.dexlace.model.mo.FriendLinkMO;

import java.util.List;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/11
 */
public interface FriendLinkService {

    /**
     * 新增或修改友情链接
     */
    void saveOrUpdateFriendLink(FriendLinkMO friendLinkMO);

    /**
     * 获得友情链接列表
     */
   List<FriendLinkMO> queryFriendLinkList();

    /**
     * 删除友情链接
     */
   void deleteFriendLink(String linkId);


    /**
     * 首页获得友情链接列表
     */
   List<FriendLinkMO> queryPortalFriendLinkList();


}
