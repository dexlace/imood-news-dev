package com.dexlace.user.service.impl;

import com.dexlace.api.service.BaseService;
import com.dexlace.common.enums.Sex;
import com.dexlace.common.utils.PagedGridResult;
import com.dexlace.common.utils.RedisOperator;
import com.dexlace.model.entity.AppUser;
import com.dexlace.model.entity.Fans;
import com.dexlace.model.eo.FansEO;
import com.dexlace.model.vo.RegionRatioVO;
import com.dexlace.user.mapper.FansMapper;
import com.dexlace.user.service.MyFansService;
import com.dexlace.user.service.UserService;
import com.github.pagehelper.PageHelper;
import org.elasticsearch.index.query.QueryBuilders;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.*;
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

    @Autowired
    private ElasticsearchTemplate esTemplate;



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


        // ???????????????????????????????????????????????????????????????
        Fans fan = new Fans();
        fan.setId(fanPkId);
        fan.setFanId(fanId);
        fan.setWriterId(writerId);
        fan.setFace(fanInfo.getFace());
        fan.setFanNickname(fanInfo.getNickname());
        fan.setProvince(fanInfo.getProvince());
        fan.setSex(fanInfo.getSex());


        fansMapper.insert(fan);


        // redis ?????????????????????
        redis.increment(REDIS_WRITER_FANS_COUNTS + ":" + writerId, 1);
        // redis ?????????????????????
        redis.increment(REDIS_MY_FOLLOW_COUNTS + ":" + fanId, 1);

       // ?????????????????????es???
        FansEO fansEO = new FansEO();
        BeanUtils.copyProperties(fan, fansEO);
        IndexQuery iq = new IndexQueryBuilder().withObject(fansEO).build();
        esTemplate.index(iq);
    }


    @Transactional
    @Override
    public void unfollow(String writerId, String fanId) {
        // ?????????????????????????????????
        Fans fan = new Fans();
        fan.setFanId(fanId);
        fan.setWriterId(writerId);
        fansMapper.delete(fan);

        // redis ?????????????????????
        redis.decrement(REDIS_WRITER_FANS_COUNTS + ":" + writerId, 1);
        // redis ?????????????????????
        redis.decrement(REDIS_MY_FOLLOW_COUNTS + ":" + fanId, 1);

        // ??????es?????????????????????DeleteQuery?????????????????????
        DeleteQuery dq = new DeleteQuery();
        dq.setQuery(QueryBuilders.termQuery("writerId", writerId));
        dq.setQuery(QueryBuilders.termQuery("fanId", fanId));
        esTemplate.delete(dq, FansEO.class);

    }


    @Override
    public PagedGridResult queryMyFansList(String writerId, Integer page, Integer pageSize) {
        Fans fan = new Fans();
        fan.setWriterId(writerId);

        /**
         * page: ?????????
         * pageSize: ??????????????????
         */
        PageHelper.startPage(page, pageSize);
        List<Fans> list = fansMapper.select(fan);
        return setterPagedGrid(list, page);
    }

    @Override
    public PagedGridResult queryMyFansESList(String writerId,
                                             Integer page,
                                             Integer pageSize) {
        page--;
        Pageable pageable = PageRequest.of(page, pageSize);

        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery("writerId", writerId))
                .withPageable(pageable)
                .build();
        AggregatedPage<FansEO> pagedFans = esTemplate.queryForPage(query, FansEO.class);

        PagedGridResult gridResult = new PagedGridResult();
        gridResult.setRows(pagedFans.getContent());
        gridResult.setPage(page + 1);
        gridResult.setTotal(pagedFans.getTotalPages());
        gridResult.setRecords(pagedFans.getTotalElements());

        return gridResult;
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

    public static final String[] regions = {"??????", "??????", "??????", "??????",
            "??????", "??????", "??????", "??????", "?????????", "??????", "??????", "??????", "??????", "??????", "??????",
            "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????",
            "?????????", "??????", "??????", "??????", "??????",
            "??????", "??????"};

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
