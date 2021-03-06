package com.nowui.cloud.base.user.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.nowui.cloud.entity.BaseEntity;

/**
 * 用户微信
 *
 * @author marcus
 *
 * 2018-01-08
 */
@Component
@TableName(value = "user_wechat_info")
public class UserWechat extends BaseEntity {

    /**
     * 用户微信编号
     */
    @Id
    @TableId
    @NotNull(message = "用户微信编号不能为空")
    @Length(max = 32, message = "用户微信编号长度超出限制")
    private String userWechatId;
    public static final String USER_WECHAT_ID = "userWechatId";

    /**
     * 应用编号
     */
    @TableField
    @NotNull(message = "应用编号不能为空")
    @Length(max = 32, message = "应用编号长度超出限制")
    private String appId;
    public static final String APP_ID = "appId";

    /**
     * 用户编号
     */
    @TableField
    @NotNull(message = "用户编号不能为空")
    @Length(max = 32, message = "用户编号长度超出限制")
    private String userId;
    public static final String USER_ID = "userId";

    /**
     * 微信用户唯一标识
     */
    @TableField
    @NotNull(message = "微信用户唯一标识不能为空")
    @Length(max = 50, message = "微信用户唯一标识长度超出限制")
    private String wechatOpenId;
    public static final String WECHAT_OPEN_ID = "wechatOpenId";

    /**
     * 微信多应用用户共享唯一标识
     */
    @TableField
    @NotNull(message = "微信多应用用户共享唯一标识不能为空")
    @Length(max = 50, message = "微信多应用用户共享唯一标识长度超出限制")
    private String wechatUnionId;
    public static final String WECHAT_UNION_ID = "wechatUnionId";

    /**
     * 微信昵称
     */
    @TableField
    @NotNull(message = "微信昵称不能为空")
    @Length(max = 100, message = "微信昵称长度超出限制")
    private String wechatNickName;
    public static final String WECHAT_NICK_NAME = "wechatNickName";

    /**
     * 性别
     */
    @TableField
    @NotNull(message = "性别不能为空")
    @Length(max = 1, message = "性别长度超出限制")
    private String wechatSex;
    public static final String WECHAT_SEX = "wechatSex";

    /**
     * 国家
     */
    @TableField
    @NotNull(message = "国家不能为空")
    @Length(max = 50, message = "国家长度超出限制")
    private String wechatCountry;
    public static final String WECHAT_COUNTRY = "wechatCountry";

    /**
     * 省
     */
    @TableField
    @NotNull(message = "省不能为空")
    @Length(max = 50, message = "省长度超出限制")
    private String wechatProvince;
    public static final String WECHAT_PROVINCE = "wechatProvince";

    /**
     * 市
     */
    @TableField
    @NotNull(message = "市不能为空")
    @Length(max = 50, message = "市长度超出限制")
    private String wechatCity;
    public static final String WECHAT_CITY = "wechatCity";

    /**
     * 微信头像本地文件编号
     */
    @TableField
    @NotNull(message = "微信头像本地文件编号不能为空")
    @Length(max = 32, message = "微信头像本地文件编号长度超出限制")
    private String wechatHeadImgFileId;
    public static final String WECHAT_HEAD_IMG_FILE_ID = "wechatHeadImgFileId";
    
    /**
     * 微信头像地址
     */
    @TableField
    @NotNull(message = "微信头像地址不能为空")
    @Length(max = 200, message = "微信头像长度超出限制")
    private String wechatHeadImgUrl;
    public static final String WECHAT_HEAD_IMG_URL = "wechatHeadImgUrl";

    /**
     * 微信用户特权
     */
    @TableField
    @NotNull(message = "微信用户特权不能为空")
    @Length(max = 200, message = "微信用户特权长度超出限制")
    private String wehchatPrivilege;
    public static final String WEHCHAT_PRIVILEGE = "wehchatPrivilege";


    public String getUserWechatId() {
        return getString(USER_WECHAT_ID);
    }
    
    public void setUserWechatId(String userWechatId) {
        put(USER_WECHAT_ID, userWechatId);
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

    public String getWechatOpenId() {
        return getString(WECHAT_OPEN_ID);
    }
    
    public void setWechatOpenId(String wechatOpenId) {
        put(WECHAT_OPEN_ID, wechatOpenId);
    }

    public String getWechatUnionId() {
        return getString(WECHAT_UNION_ID);
    }
    
    public void setWechatUnionId(String wechatUnionId) {
        put(WECHAT_UNION_ID, wechatUnionId);
    }

    public String getWechatNickName() {
        return getString(WECHAT_NICK_NAME);
    }
    
    public void setWechatNickName(String wechatNickName) {
        put(WECHAT_NICK_NAME, wechatNickName);
    }

    public String getWechatSex() {
        return getString(WECHAT_SEX);
    }
    
    public void setWechatSex(String wechatSex) {
        put(WECHAT_SEX, wechatSex);
    }

    public String getWechatCountry() {
        return getString(WECHAT_COUNTRY);
    }
    
    public void setWechatCountry(String wechatCountry) {
        put(WECHAT_COUNTRY, wechatCountry);
    }

    public String getWechatProvince() {
        return getString(WECHAT_PROVINCE);
    }
    
    public void setWechatProvince(String wechatProvince) {
        put(WECHAT_PROVINCE, wechatProvince);
    }

    public String getWechatCity() {
        return getString(WECHAT_CITY);
    }
    
    public void setWechatCity(String wechatCity) {
        put(WECHAT_CITY, wechatCity);
    }

    public String getWechatHeadImgFileId() {
        return getString(WECHAT_HEAD_IMG_FILE_ID);
    }
    
    public void setWechatHeadImgFileId(String wechatHeadImgFileId) {
        put(WECHAT_HEAD_IMG_FILE_ID, wechatHeadImgFileId);
    }
    
    public String getWechatHeadImgUrl() {
        return getString(WECHAT_HEAD_IMG_URL);
    }
    
    public void setWechatHeadImgUrl(String wechatHeadImgUrl) {
        put(WECHAT_HEAD_IMG_URL, wechatHeadImgUrl);
    }

    public String getWehchatPrivilege() {
        return getString(WEHCHAT_PRIVILEGE);
    }
    
    public void setWehchatPrivilege(String wehchatPrivilege) {
        put(WEHCHAT_PRIVILEGE, wehchatPrivilege);
    }


}