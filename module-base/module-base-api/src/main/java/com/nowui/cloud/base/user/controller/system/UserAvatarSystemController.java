package com.nowui.cloud.base.user.controller.system;

import com.alibaba.fastjson.JSON;
import com.nowui.cloud.base.user.entity.UserAvatar;
import com.nowui.cloud.base.user.rpc.UserAvatarRpc;
import com.nowui.cloud.base.user.service.UserAvatarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户头像系统端控制器
 *
 * @author marcus
 *
 * 2018-01-08
 */
@Api(value = "用户头像", description = "用户头像系统端接口管理")
@RestController
public class UserAvatarSystemController implements UserAvatarRpc {

}