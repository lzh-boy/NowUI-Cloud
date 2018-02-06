package com.nowui.cloud.base.user.view;

import javax.validation.constraints.NotNull;

import com.nowui.cloud.annotation.KeyId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import com.nowui.cloud.view.BaseView;

/**
 * 用户视图
 *
 * @author marcus
 *
 * 2018-02-04
 */
@Component
@Document(collection = "user_info")
public class UserView extends BaseView {

    /**
     * 用户编号
     */
    @KeyId
    @Field
    @NotNull(message = "用户编号不能为空")
    private String userId;
    public static final String USER_ID = "userId";

    /**
     * 应用编号
     */
    @Field
    @NotNull(message = "应用编号不能为空")
    private String appId;
    public static final String APP_ID = "appId";

    /**
     * 用户主体编号（会员、管理员、员工等编号）
     */
    @Field
    @NotNull(message = "用户主体编号不能为空")
    private String objectId;
    public static final String OBJECT_ID = "objectId";

    /**
     * 类型
     */
    @Field
    @NotNull(message = "类型不能为空")
    private String userType;
    public static final String USER_TYPE = "userType";


    public String getUserId() {
        return getString(USER_ID);
    }

    public void setUserId(String userId) {
        put(USER_ID, userId);
    }

    public String getAppId() {
        return getString(APP_ID);
    }

    public void setAppId(String appId) {
        put(APP_ID, appId);
    }

    public String getObjectId() {
        return getString(OBJECT_ID);
    }

    public void setObjectId(String objectId) {
        put(OBJECT_ID, objectId);
    }

    public String getUserType() {
        return getString(USER_TYPE);
    }

    public void setUserType(String userType) {
        put(USER_TYPE, userType);
    }


}