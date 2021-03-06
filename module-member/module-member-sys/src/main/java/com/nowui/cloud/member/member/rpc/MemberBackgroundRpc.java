package com.nowui.cloud.member.member.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * 会员背景服务调用
 *
 * @author marcus
 *
 * 2018-01-14
 */
@Component(value = "memberBackgroundRpc")
@FeignClient(name = "module-member")
public interface MemberBackgroundRpc {

}