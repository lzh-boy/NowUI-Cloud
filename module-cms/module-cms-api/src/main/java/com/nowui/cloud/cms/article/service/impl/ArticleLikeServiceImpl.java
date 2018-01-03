package com.nowui.cloud.cms.article.service.impl;

import com.nowui.cloud.mybatisplus.BaseWrapper;
import com.nowui.cloud.service.impl.BaseServiceImpl;
import com.nowui.cloud.cms.article.entity.ArticleLike;
import com.nowui.cloud.cms.article.mapper.ArticleLikeMapper;
import com.nowui.cloud.cms.article.service.ArticleLikeService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 文章点赞业务实现
 *
 * @author xupengfei
 *
 * 2018-01-03
 */
@Service
public class ArticleLikeServiceImpl extends BaseServiceImpl<ArticleLikeMapper, ArticleLike> implements ArticleLikeService {

    @Override
    public Integer adminCount(String appId, String articleId, String userId) {
        Integer count = count(
                new BaseWrapper<ArticleLike>()
                        .eq(ArticleLike.APP_ID, appId)
                        .likeAllowEmpty(ArticleLike.ARTICLE_ID, articleId)
                        .likeAllowEmpty(ArticleLike.USER_ID, userId)
                        .eq(ArticleLike.SYSTEM_STATUS, true)
        );
        return count;
    }

    @Override
    public List<ArticleLike> adminList(String appId, String articleId, String userId, Integer m, Integer n) {
        List<ArticleLike> articleLikeList = list(
                new BaseWrapper<ArticleLike>()
                        .eq(ArticleLike.APP_ID, appId)
                        .likeAllowEmpty(ArticleLike.ARTICLE_ID, articleId)
                        .likeAllowEmpty(ArticleLike.USER_ID, userId)
                        .eq(ArticleLike.SYSTEM_STATUS, true)
                        .orderDesc(Arrays.asList(ArticleLike.SYSTEM_CREATE_TIME)),
                m,
                n
        );

        return articleLikeList;
    }

}