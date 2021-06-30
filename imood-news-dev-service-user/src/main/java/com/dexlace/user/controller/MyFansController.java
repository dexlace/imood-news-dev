package com.dexlace.user.controller;

import com.dexlace.api.controller.BaseController;
import com.dexlace.api.controller.user.MyFansControllerApi;
import com.dexlace.common.enums.Sex;
import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.common.utils.PagedGridResult;
import com.dexlace.model.vo.FansCountsVO;
import com.dexlace.model.vo.RegionRatioVO;
import com.dexlace.user.service.MyFansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/14
 */
@RestController
public class MyFansController extends BaseController implements MyFansControllerApi {

    @Autowired
    public MyFansService myFansService;

    @Override
    public GraceIMOODJSONResult isMeFollowThisWriter(String writerId, String fanId) {

        boolean result = myFansService.isMeFollowThisWriter(writerId,fanId);
        return GraceIMOODJSONResult.ok(result);
    }


    @Override
    public GraceIMOODJSONResult follow(String writerId, String fanId) {

        myFansService.follow(writerId,fanId);

        return GraceIMOODJSONResult.ok();
    }


    @Override
    public GraceIMOODJSONResult unfollow(String writerId, String fanId) {
        myFansService.unfollow(writerId, fanId);
        return GraceIMOODJSONResult.ok();
    }

    @Override
    public GraceIMOODJSONResult queryAll(String writerId, Integer page, Integer pageSize) {

        if (page == null) {
            page = COMMON_START_PAGE;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

//        PagedGridResult gridResult = myFansService.queryMyFansList(writerId, page, pageSize);
        PagedGridResult gridResult = myFansService.queryMyFansESList(writerId, page, pageSize);
        return GraceIMOODJSONResult.ok(gridResult);
    }


    @Override
    public GraceIMOODJSONResult queryRatio(String writerId) {

        // 查询男的多少人
        int manCounts = myFansService.queryFansCounts(writerId, Sex.man);
        // 查询女的多少人
        int womanCounts = myFansService.queryFansCounts(writerId, Sex.woman);

        FansCountsVO fansCountsVO = new FansCountsVO();
        fansCountsVO.setManCounts(manCounts);
        fansCountsVO.setWomanCounts(womanCounts);

        return GraceIMOODJSONResult.ok(fansCountsVO);
    }



    @Override
    public GraceIMOODJSONResult queryRatioByRegion(String writerId) {
        List<RegionRatioVO> regionRatioVOList = myFansService.queryRatioByRegion(writerId);
        return GraceIMOODJSONResult.ok(regionRatioVOList);
    }



}
