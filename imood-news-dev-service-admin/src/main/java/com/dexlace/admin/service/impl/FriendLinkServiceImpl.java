package com.dexlace.admin.service.impl;

import com.dexlace.admin.repository.FriendLinkRepository;
import com.dexlace.admin.service.FriendLinkService;
import com.dexlace.common.enums.YesOrNo;
import com.dexlace.model.mo.FriendLinkMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/11
 */
@Service
public class FriendLinkServiceImpl implements FriendLinkService {

    @Autowired
    private FriendLinkRepository friendLinkRepository;

    @Override
    public void saveOrUpdateFriendLink(FriendLinkMO friendLinkMO) {
        friendLinkRepository.save(friendLinkMO);
    }

    @Override
    public List<FriendLinkMO> queryFriendLinkList() {
        return friendLinkRepository.findAll();
    }


    @Override
    public void deleteFriendLink(String linkId) {
        friendLinkRepository.deleteById(linkId);
    }


    @Override
    public List<FriendLinkMO> queryPortalFriendLinkList() {
        return friendLinkRepository.getAllByIsDelete(YesOrNo.NO.type);
    }





}
