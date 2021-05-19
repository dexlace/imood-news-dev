package com.dexlace.user.service.impl;

import com.dexlace.api.service.BaseService;
import com.dexlace.common.enums.UserStatus;
import com.dexlace.common.utils.PagedGridResult;
import com.dexlace.model.entity.AppUser;
import com.dexlace.user.mapper.AppUserMapper;
import com.dexlace.user.service.AppUserMngService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/11
 */
@Service
public class AppUserMngServiceImpl extends BaseService implements AppUserMngService {

    @Autowired
    public AppUserMapper appUserMapper;

    @Override
    public PagedGridResult queryAllUserList(String nickname, Integer status,
                                            Date startDate, Date endDate,
                                            Integer page, Integer pageSize) {

        Example userExample = new Example(AppUser.class);
        userExample.orderBy("createdTime").desc();
        Example.Criteria criteria = userExample.createCriteria();

        if (StringUtils.isNotBlank(nickname)) {
            criteria.andLike("nickname", "%" + nickname + "%");
        }

        if (UserStatus.isUserStatusValid(status)) {
            criteria.andEqualTo("activeStatus", status);
        }

        if (startDate != null) {
            criteria.andGreaterThanOrEqualTo("createdTime", startDate);
        }

        if (endDate != null) {
            criteria.andLessThanOrEqualTo("createdTime", endDate);
        }


        PageHelper.startPage(page, pageSize);
        List<AppUser> list = appUserMapper.selectByExample(userExample);
        return setterPagedGrid(list, page);
    }

    @Override
    public void freezeUserOrNot(String userId, Integer status) {
        AppUser temp = new AppUser();
        temp.setId(userId);
        temp.setActiveStatus(status);
        appUserMapper.updateByPrimaryKeySelective(temp);
    }


}

