package com.nowui.cloud.sns.forum.view;

import com.alibaba.fastjson.JSONObject;
import com.nowui.cloud.annotation.KeyId;
import com.nowui.cloud.view.BaseView;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

/**
 * 论坛用户关注视图
 *
 * @author xupengfei
 *
 * 2018-02-04
 */
@Component
@Document(collection = "forum_user_follow_info")
public class ForumUserFollowView extends BaseView {

    /**
     * 论坛用户关注编号
     */
    @KeyId
    @Field
    @NotNull(message = "论坛用户关注编号不能为空")
    private String forumUserFollowId;
    public static final String FORUM_USER_FOLLOW_ID = "forumUserFollowId";

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
    @NotNull(message = "会员编号不能为空")
    private String memberId;
    public static final String MEMBER_ID = "memberId";
    
    /**
     * 版主的头像路径和昵称,签名
     */
    @Field
    @NotNull(message = "版主的头像路径和昵称,签名不能为空")
    private JSONObject userInfo;
    public static final String USER_INFO = "userInfo";

    /**
     * 论坛版主(id)
     */
    @Field
    @NotNull(message = "论坛版主(id)不能为空")
    private String forumModerator;
    public static final String FORUM_MODERATOR = "forumModerator";
    
    /**
     * 关注用户的头像
     */
    @Field
    @NotNull(message = "版主的头像")
    private String userAvatar;
    public static final String USER_AVATAR = "userAvatar";
    
    /**
     * 关注用户的昵称
     */
    @Field
    @NotNull(message = "版主的昵称")
    private String userNickName;
    public static final String USER_NICKNAME = "userNickName";
    
    /**
     * 关注用户的会员签名
     */
    @Field
    @NotNull(message = "版主的会员签名")
    private String memberSignature;
    public static final String MEMBER_SIGNATURE = "memberSignature";
    
    /**
     * 论坛编号
     */
    @Field
    @NotNull(message = "论坛编号不能为空")
    private String forumId;
    public static final String FORUM_ID = "forumId";
    
    /**
     * 是否置顶
     */
    @Field
    @NotNull(message = "是否置顶不能为空")
    private Boolean forumUserFollowIsTop;
    public static final String FORUM_USER_FOLLOW_IS_TOP = "forumUserFollowIsTop";


    public String getForumUserFollowId() {
        return getString(FORUM_USER_FOLLOW_ID);
    }

    public void setForumUserFollowId(String forumUserFollowId) {
        put(FORUM_USER_FOLLOW_ID, forumUserFollowId);
    }

    public String getAppId() {
        return getString(APP_ID);
    }

    public void setAppId(String appId) {
        put(APP_ID, appId);
    }

    public String getMemberId() {
        return getString(MEMBER_ID);
    }

    public void setMemberId(String memberId) {
        put(MEMBER_ID, memberId);
    }

    public JSONObject getUserInfo() {
        return getJSONObject(USER_INFO);
	}

	public void setUserInfo(JSONObject userInfo) {
        put(USER_INFO, userInfo);
	}
	
	public String getUserAvatar() {
		return getString(USER_AVATAR);
	}

	public void setUserAvatar(String userAvatar) {
		put(USER_AVATAR, userAvatar);
	}

	public String getUserNickName() {
		return getString(USER_NICKNAME);
	}

	public void setUserNickName(String userNickName) {
		put(USER_NICKNAME, userNickName);
	}

	public String getMemberSignature() {
		return getString(MEMBER_SIGNATURE);
	}

	public void setMemberSignature(String memberSignature) {
		put(MEMBER_SIGNATURE, memberSignature);
	}

	public String getForumId() {
        return getString(FORUM_ID);
    }

    public void setForumId(String forumId) {
        put(FORUM_ID, forumId);
    }
    
    public String getForumModerator() {
        return getString(FORUM_MODERATOR);
    }
    
    public void setForumModerator(String forumModerator) {
        put(FORUM_MODERATOR, forumModerator);
    }
    
    public Boolean getForumUserFollowIsTop() {
        return getBoolean(FORUM_USER_FOLLOW_IS_TOP);
    }

    public void setForumUserFollowIsTop(Boolean forumUserFollowIsTop) {
        put(FORUM_USER_FOLLOW_IS_TOP, forumUserFollowIsTop);
    }


}