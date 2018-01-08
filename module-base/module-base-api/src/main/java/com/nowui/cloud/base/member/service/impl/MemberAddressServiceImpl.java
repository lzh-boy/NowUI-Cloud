package com.nowui.cloud.base.member.service.impl;

import com.nowui.cloud.mybatisplus.BaseWrapper;
import com.nowui.cloud.service.impl.BaseServiceImpl;
import com.nowui.cloud.base.member.entity.MemberAddress;
import com.nowui.cloud.base.member.mapper.MemberAddressMapper;
import com.nowui.cloud.base.member.service.MemberAddressService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 会员地址业务实现
 *
 * @author marcus
 *
 * 2018-01-08
 */
@Service
public class MemberAddressServiceImpl extends BaseServiceImpl<MemberAddressMapper, MemberAddress> implements MemberAddressService {

    @Override
    public Integer adminCount(String appId, String memberAddressProvince, String memberAddressCity, String memberAddressArea) {
        Integer count = count(
                new BaseWrapper<MemberAddress>()
                        .eq(MemberAddress.APP_ID, appId)
                        .likeAllowEmpty(MemberAddress.MEMBER_ADDRESS_PROVINCE, memberAddressProvince)
                        .likeAllowEmpty(MemberAddress.MEMBER_ADDRESS_CITY, memberAddressCity)
                        .likeAllowEmpty(MemberAddress.MEMBER_ADDRESS_AREA, memberAddressArea)
                        .eq(MemberAddress.SYSTEM_STATUS, true)
        );
        return count;
    }

    @Override
    public List<MemberAddress> adminList(String appId, String memberAddressProvince, String memberAddressCity, String memberAddressArea, Integer pageIndex, Integer pageSize) {
        List<MemberAddress> memberAddressList = list(
                new BaseWrapper<MemberAddress>()
                        .eq(MemberAddress.APP_ID, appId)
                        .likeAllowEmpty(MemberAddress.MEMBER_ADDRESS_PROVINCE, memberAddressProvince)
                        .likeAllowEmpty(MemberAddress.MEMBER_ADDRESS_CITY, memberAddressCity)
                        .likeAllowEmpty(MemberAddress.MEMBER_ADDRESS_AREA, memberAddressArea)
                        .eq(MemberAddress.SYSTEM_STATUS, true)
                        .orderDesc(Arrays.asList(MemberAddress.SYSTEM_CREATE_TIME)),
                pageIndex,
                pageSize
        );

        return memberAddressList;
    }

}