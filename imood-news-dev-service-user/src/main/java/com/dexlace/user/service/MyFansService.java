package com.dexlace.user.service;

import com.dexlace.common.enums.Sex;
import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.common.utils.PagedGridResult;
import com.dexlace.model.vo.RegionRatioVO;

import java.util.List;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/14
 */
public interface MyFansService {
    /**
     * 查询当前用户是否关注作家
     */
    public boolean isMeFollowThisWriter(String writerId, String fanId);


    public void follow(String writerId, String fanId);

    /**
     * 取消关注
     */
    public void unfollow(String writerId, String fanId);


    /**
     * 查询我的粉丝列表
     */
    public PagedGridResult queryMyFansList(String writerId, Integer page, Integer pageSize);


    /**
     * 查询男粉丝或者女粉丝数量
     */
    public Integer queryFansCounts(String writerId, Sex sex);



    /**
     * 查询每个地域的粉丝数量
     */
    public List<RegionRatioVO> queryRatioByRegion(String writerId);


}
