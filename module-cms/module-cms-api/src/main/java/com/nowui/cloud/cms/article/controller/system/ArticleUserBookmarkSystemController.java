package com.nowui.cloud.cms.article.controller.system;

import com.alibaba.fastjson.JSON;
import com.nowui.cloud.cms.article.entity.ArticleUserBookmark;
import com.nowui.cloud.cms.article.rpc.ArticleUserBookmarkRpc;
import com.nowui.cloud.cms.article.service.ArticleUserBookmarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章用户收藏系统端控制器
 *
 * @author marcus
 *
 * 2018-01-08
 */
@Api(value = "文章用户收藏", description = "文章用户收藏系统端接口管理")
@RestController
public class ArticleUserBookmarkSystemController implements ArticleUserBookmarkRpc {

}