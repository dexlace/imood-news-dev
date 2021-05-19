package com.dexlace.user.service.impl;

import com.dexlace.api.service.BaseService;
import com.dexlace.common.enums.Sex;
import com.dexlace.common.utils.PagedGridResult;
import com.dexlace.common.utils.RedisOperator;
import com.dexlace.model.entity.AppUser;
import com.dexlace.model.entity.Fans;
import com.dexlace.model.vo.RegionRatioVO;
import com.dexlace.user.mapper.FansMapper;
import com.dexlace.user.service.MyFansService;
import com.dexlace.user.service.UserService;
import com.github.pagehelper.PageHelper;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/14
 */
@Service
public class MyFansServiceImpl extends BaseService implements MyFansService {

    @Autowired
    private FansMapper fansMapper;
    @Autowired
    private UserService userService;

    @Autowired
    private Sid sid;

    @Autowired
    private RedisOperator redis;


    @Override
    public boolean isMeFollowThisWriter(String writerId, String fanId) {

        Fans fan = new Fans();
        fan.setFanId(fanId);
        fan.setWriterId(writerId);
        int count = fansMapper.selectCount(fan);

        return count > 0 ? true : false;
    }

    @Transactional
    @Override
    public void follow(String writerId, String fanId) {

        AppUser fanInfo = userService.getUser(fanId);

        String fanPkId = sid.nextShort();


        // 保存作家粉丝关联关系，字段冗余便于统计分析
        Fans fan = new Fans();
        fan.setId(fanPkId);
        fan.setFanId(fanId);
        fan.setWriterId(writerId);
        fan.setFace(fanInfo.getFace());
        fan.setFanNickname(fanInfo.getNickname());
        fan.setProvince(fanInfo.getProvince());
        fan.setSex(fanInfo.getSex());


        fansMapper.insert(fan);


        // redis 作家粉丝数累加
        redis.increment(REDIS_WRITER_FANS_COUNTS + ":" + writerId, 1);
        // redis 我的关注数累加
        redis.increment(REDIS_MY_FOLLOW_COUNTS + ":" + fanId, 1);


    }


    @Transactional
    @Override
    public void unfollow(String writerId, String fanId) {
        // 删除作家粉丝的关联关系
        Fans fan = new Fans();
        fan.setFanId(fanId);
        fan.setWriterId(writerId);
        fansMapper.delete(fan);

        // redis 作家粉丝数累减
        redis.decrement(REDIS_WRITER_FANS_COUNTS + ":" + writerId, 1);
        // redis 我的关注数累减
        redis.decrement(REDIS_MY_FOLLOW_COUNTS + ":" + fanId, 1);
    }


    @Override
    public PagedGridResult queryMyFansList(String writerId, Integer page, Integer pageSize) {
        Fans fan = new Fans();
        fan.setWriterId(writerId);

        /**
         * page: 第几页
         * pageSize: 每页显示条数
         */
        PageHelper.startPage(page, pageSize);
        List<Fans> list = fansMapper.select(fan);
        return setterPagedGrid(list, page);
    }


    @Override
    public Integer queryFansCounts(String writerId, Sex sex) {
        Fans fan = new Fans();
        fan.setWriterId(writerId);

        if (sex == Sex.man) {
            fan.setSex(Sex.man.type);
        } else if (sex == Sex.woman) {
            fan.setSex(Sex.woman.type);
        } else {
            return 0;
        }

        int count = fansMapper.selectCount(fan);
        return count;
    }

    public static final String[] regions = {"北京", "天津", "上海", "重庆",
            "河北", "山西", "辽宁", "吉林", "黑龙江", "江苏", "浙江", "安徽", "福建", "江西", "山东",
            "河南", "湖北", "湖南", "广东", "海南", "四川", "贵州", "云南", "陕西", "甘肃", "青海", "台湾",
            "内蒙古", "广西", "西藏", "宁夏", "新疆",
            "香港", "澳门"};

    @Override
    public List<RegionRatioVO> queryRatioByRegion(String writerId) {

        Fans fan = new Fans();
        fan.setWriterId(writerId);

        List<RegionRatioVO> regionRatioVOList = new ArrayList<>();
        for (String r : regions) {
            fan.setProvince(r);
            int count = fansMapper.selectCount(fan);

            RegionRatioVO regionRatioVO = new RegionRatioVO();
            regionRatioVO.setName(r);
            regionRatioVO.setValue(count);

            regionRatioVOList.add(regionRatioVO);
        }

        return regionRatioVOList;
    }

}
