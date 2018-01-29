package com.nowui.cloud.base.admin.controller.admin;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.nowui.cloud.base.admin.entity.Admin;
import com.nowui.cloud.base.admin.service.AdminService;
import com.nowui.cloud.base.user.entity.User;
import com.nowui.cloud.base.user.entity.UserAccount;
import com.nowui.cloud.base.user.entity.UserEmail;
import com.nowui.cloud.base.user.entity.UserIdcard;
import com.nowui.cloud.base.user.entity.UserMobile;
import com.nowui.cloud.base.user.entity.UserNickName;
import com.nowui.cloud.base.user.entity.enums.UserType;
import com.nowui.cloud.base.user.mq.UserMq;
import com.nowui.cloud.controller.BaseController;
import com.nowui.cloud.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 管理员管理端控制器
 *
 * @author marcus
 *
 * 2018-01-01
 */
@Api(value = "管理员", description = "管理员管理端接口管理")
@RestController
public class AdminAdminController extends BaseController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserMq userMq;
    
    @ApiOperation(value = "管理员列表")
    @RequestMapping(value = "/admin/admin/v1/list", method = {RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> listV1() {
        User userEntity = getEntry(User.class);

        validateRequest(
                userEntity,
                User.APP_ID,
                User.PAGE_INDEX,
                User.PAGE_SIZE
        );

        Integer resultTotal = adminService.countForAdmin(userEntity.getAppId());
        List<Admin> resultList = adminService.listForAdmin(userEntity.getAppId(), userEntity.getPageIndex(), userEntity.getPageSize());

        validateResponse(
                Admin.ADMIN_ID,
        		User.USER_ID,
                User.USER_TYPE,
                User.USER_ACCOUNT,
                User.USER_NICK_NAME,
                User.USER_MOBILE,
                User.USER_EMAIL,
                User.USER_AVATAR
        );

        return renderJson(resultTotal, resultList);
    }

    @ApiOperation(value = "管理员信息")
    @RequestMapping(value = "/admin/admin/v1/find", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> findV1() {
        Admin adminEntity = getEntry(Admin.class);

        validateRequest(
                adminEntity,
                Admin.APP_ID,
                Admin.ADMIN_ID
        );

        Admin result = adminService.find(adminEntity.getAdminId());

        validateResponse(
                Admin.ADMIN_ID,
                User.USER_ID,
                User.USER_ACCOUNT,
                User.USER_NICK_NAME,
                User.USER_MOBILE,
                User.USER_EMAIL,
                User.USER_AVATAR,
                Admin.SYSTEM_VERSION
        );

        return renderJson(result);
    }

    @ApiOperation(value = "新增管理员")
    @RequestMapping(value = "/admin/admin/v1/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> saveV1() {
        User userEntity = getEntry(User.class);

        validateRequest(
                userEntity,
                User.APP_ID
        );
        //验证用户账号
        UserAccount userAccountEntity = getEntry(UserAccount.class).keepTableFieldValue();
        validateRequest(
                userAccountEntity,
                UserAccount.USER_ACCOUNT
        );
        //验证用户昵称
        UserNickName userNickNameEntity = getEntry(UserNickName.class).keepTableFieldValue();
        validateRequest(
                userNickNameEntity,
                UserNickName.USER_NICK_NAME
        );
        //验证用户姓名
        UserIdcard userIdcardEntity = getEntry(UserIdcard.class).keepTableFieldValue();
        validateRequest(
                userIdcardEntity,
                UserIdcard.USER_NAME
        );
        //验证用户邮箱
        UserEmail userEmailEntity = getEntry(UserEmail.class).keepTableFieldValue();
        validateRequest(
                userEmailEntity,
                UserEmail.USER_EMAIL
        );
        //验证用户手机
        UserMobile userMobileEntity = getEntry(UserMobile.class).keepTableFieldValue();
        validateRequest(
                userMobileEntity,
                UserMobile.USER_MOBILE
        );
        String adminId = Util.getRandomUUID();
        String userId = Util.getRandomUUID();

        userEntity.setUserId(userId);
        userEntity.setUserType(UserType.ADMIN.getKey());
        userEntity.setObjectId(adminId);
        
        Admin admin = new Admin();
        admin.setUserId(userId);
        admin.setAppId(userEntity.getAppId());
        
        Boolean result = true;
        if (result) {
            userMq.sendSave(userEntity, userId, userEntity.getSystemRequestUserId());
            userMq.sendSaveAccount(userAccountEntity, userId, userEntity.getSystemRequestUserId());
            userMq.sendSaveNickName(userNickNameEntity, userId, userEntity.getSystemRequestUserId());
            userMq.sendSaveIdcard(userIdcardEntity, userId, userEntity.getSystemRequestUserId());
            userMq.sendSaveMobile(userMobileEntity, userId, userEntity.getSystemRequestUserId());
		}
        
        return renderJson(result);
    }

    @ApiOperation(value = "修改管理员")
    @RequestMapping(value = "/admin/admin/v1/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updateV1() {
        User userEntity = getEntry(User.class);

        validateRequest(
                userEntity,
                User.APP_ID,
                User.USER_ID,
                User.SYSTEM_VERSION
        );
        Admin admin = JSONObject.parseObject(userEntity.toJSONString(), Admin.class);
        validateRequest(
                admin,
                Admin.ADMIN_ID,
                Admin.APP_ID,
                Admin.SYSTEM_VERSION
        );
        
        //验证用户账号
        UserAccount userAccountEntity = getEntry(UserAccount.class).keepTableFieldValue();
        validateRequest(
                userAccountEntity,
                UserAccount.USER_ACCOUNT
        );
        //验证用户昵称
        UserNickName userNickNameEntity = getEntry(UserNickName.class).keepTableFieldValue();
        validateRequest(
                userNickNameEntity,
                UserNickName.USER_NICK_NAME
        );
        //验证用户姓名
        UserIdcard userIdcardEntity = getEntry(UserIdcard.class).keepTableFieldValue();
        userIdcardEntity.setUserIdcardNumber("");
        validateRequest(
                userIdcardEntity,
                UserIdcard.USER_NAME
        );
        //验证用户邮箱
        UserEmail userEmailEntity = getEntry(UserEmail.class).keepTableFieldValue();
        validateRequest(
                userEmailEntity,
                UserEmail.USER_EMAIL
        );
        //验证用户手机
        UserMobile userMobileEntity = getEntry(UserMobile.class).keepTableFieldValue();
        validateRequest(
                userMobileEntity,
                UserMobile.USER_MOBILE
        );
        
        Boolean result = adminService.update(admin, admin.getAdminId(), admin.getSystemRequestUserId(), admin.getSystemVersion());

        if (result) {
            userMq.sendUpdate(userEntity, userEntity.getUserId(), userEntity.getSystemRequestUserId(), userEntity.getSystemVersion());
            userMq.sendUpdateAccount(userAccountEntity, userEntity.getUserId(), userEntity.getSystemRequestUserId());
            userMq.sendUpdateNickName(userNickNameEntity, userEntity.getUserId(), userEntity.getSystemRequestUserId());
            userMq.sendUpdateIdcard(userIdcardEntity, userEntity.getUserId(), userEntity.getSystemRequestUserId());
            userMq.sendUpdateMobile(userMobileEntity, userEntity.getUserId(), userEntity.getSystemRequestUserId());
        }
        
        return renderJson(result);
    }

    @ApiOperation(value = "删除管理员")
    @RequestMapping(value = "/admin/admin/v1/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> deleteV1() {
        Admin adminEntity = getEntry(Admin.class);

        validateRequest(
                adminEntity,
                Admin.ADMIN_ID,
                Admin.APP_ID,
                Admin.SYSTEM_VERSION
        );

        Boolean result = adminService.delete(adminEntity.getAdminId(), adminEntity.getSystemRequestUserId(), adminEntity.getSystemVersion());

        if (result) {
            //adminMq.sendDeleteUser(body.getAdminId(), body.getSystemRequestUserId());
        }
        return renderJson(result);
    }

}