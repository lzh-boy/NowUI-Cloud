package com.nowui.cloud.base.member.service.impl;

import com.nowui.cloud.mybatisplus.BaseWrapper;
import com.nowui.cloud.service.impl.BaseServiceImpl;
import com.nowui.cloud.base.member.entity.Member;
import com.nowui.cloud.base.member.mapper.MemberMapper;
import com.nowui.cloud.base.member.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 会员业务实现
 *
 * @author marcus
 *
 * 2018-01-08
 */
@Service
public class MemberServiceImpl extends BaseServiceImpl<MemberMapper, Member> implements MemberService {

    @Override
    public Integer adminCount(String appId, Boolean memberIsTop, Boolean memberIsRecommed) {
        Integer count = count(
                new BaseWrapper<Member>()
                        .eq(Member.APP_ID, appId)
                        .eqAllowEmpty(Member.MEMBER_IS_TOP, memberIsTop)
                        .eqAllowEmpty(Member.MEMBER_IS_RECOMMED, memberIsRecommed)
                        .eq(Member.SYSTEM_STATUS, true)
        );
        return count;
    }

    @Override
    public List<Member> adminList(String appId, Boolean memberIsTop, Boolean memberIsRecommed, Integer pageIndex, Integer pageSize) {
        List<Member> memberList = list(
                new BaseWrapper<Member>()
                        .eq(Member.APP_ID, appId)
                        .eqAllowEmpty(Member.MEMBER_IS_TOP, memberIsTop)
                        .eqAllowEmpty(Member.MEMBER_IS_RECOMMED, memberIsRecommed)
                        .eq(Member.SYSTEM_STATUS, true)
                        .orderDesc(Arrays.asList(Member.SYSTEM_CREATE_TIME)),
                pageIndex,
                pageSize
        );

        return memberList;
    }

}