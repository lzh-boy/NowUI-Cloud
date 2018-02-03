package com.nowui.cloud.base.user.view;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import com.nowui.cloud.view.BaseView;

/**
 * 用户头像视图
 *
 * @author marcus
 *
 * 2018-02-04
 */
@Component
@Document(collection = "user_avatar_info")
public class UserAvatarView extends BaseView {

    /**
     * 用户头像编号
     */
    @Field
    @NotNull(message = "用户头像编号不能为空")
    private String userAvatarId;
    public static final String USER_AVATAR_ID = "userAvatarId";

    /**
     * 应用编号
     */
    @Field
    @NotNull(message = "应用编号不能为空")
    private String appId;
    public static final String APP_ID = "appId";

    /**
     * 用户编号
     */
    @Field
    @NotNull(message = "用户编号不能为空")
    private String userId;
    public static final String USER_ID = "userId";

    /**
     * 头像文件编号
     */
    @Field
    @NotNull(message = "头像文件编号不能为空")
    private String userAvatar;
    public static final String USER_AVATAR = "userAvatar";


    public String getUserAvatarId() {
        return getString(USER_AVATAR_ID);
    }

    public void setUserAvatarId(String userAvatarId) {
        put(USER_AVATAR_ID, userAvatarId);
    }

    public String getAppId() {
        return getString(APP_ID);
    }

    public void setAppId(String appId) {
        put(APP_ID, appId);
    }

    public String getUserId() {
        return getString(USER_ID);
    }

    public void setUserId(String userId) {
        put(USER_ID, userId);
    }

    public String getUserAvatar() {
        return getString(USER_AVATAR);
    }

    public void setUserAvatar(String userAvatar) {
        put(USER_AVATAR, userAvatar);
    }


}