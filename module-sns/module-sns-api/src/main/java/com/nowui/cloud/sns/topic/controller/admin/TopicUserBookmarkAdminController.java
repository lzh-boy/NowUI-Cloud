package com.nowui.cloud.sns.topic.controller.admin;
import com.nowui.cloud.controller.BaseController;
import com.nowui.cloud.util.Util;
import com.nowui.cloud.sns.topic.entity.TopicTip;
import com.nowui.cloud.sns.topic.entity.TopicUserBookmark;
import com.nowui.cloud.sns.topic.router.TopicUserBookmarkRouter;
import com.nowui.cloud.sns.topic.service.TopicUserBookmarkService;
import com.nowui.cloud.sns.topic.view.TopicTipView;
import com.nowui.cloud.sns.topic.view.TopicUserBookmarkView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 话题收藏管理端控制器
 *
 * @author xupengfei
 *
 * 2018-01-08
 */
@Api(value = "话题收藏", description = "话题收藏管理端接口管理")
@RestController
public class TopicUserBookmarkAdminController extends BaseController {

    @Autowired
    private TopicUserBookmarkService topicUserBookmarkService;

    @ApiOperation(value = "话题收藏列表")
    @RequestMapping(value = "/topic/user/bookmark/admin/v1/list", method = {RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> listV1(@RequestBody TopicUserBookmark body) {
        validateRequest(
                body,
                TopicUserBookmark.APP_ID,
                TopicUserBookmark.TOPIC_ID,
                TopicUserBookmark.MEMBER_ID,
                TopicUserBookmark.PAGE_INDEX,
                TopicUserBookmark.PAGE_SIZE
        );

        Integer resultTotal = topicUserBookmarkService.countForAdmin(body.getAppId() , body.getTopicId(), body.getMemberId());
        List<TopicUserBookmark> resultList = topicUserBookmarkService.listForAdmin(body.getAppId(), body.getTopicId(), body.getMemberId(), body.getPageIndex(), body.getPageSize());

        validateResponse(
                TopicUserBookmark.TOPIC_USER_BOOKMARK_ID,
                TopicUserBookmark.TOPIC_ID,
                TopicUserBookmark.MEMBER_ID
        );

        return renderJson(resultTotal, resultList);
    }

    @ApiOperation(value = "话题收藏信息")
    @RequestMapping(value = "/topic/user/bookmark/admin/v1/find", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> findV1(@RequestBody TopicUserBookmark body) {
        validateRequest(
                body,
                TopicUserBookmark.APP_ID,
                TopicUserBookmark.TOPIC_USER_BOOKMARK_ID
        );

        TopicUserBookmarkView result = topicUserBookmarkService.find(body.getTopicUserBookmarkId());

        validateResponse(
                TopicUserBookmark.TOPIC_USER_BOOKMARK_ID,
                TopicUserBookmark.TOPIC_ID,
                TopicUserBookmark.MEMBER_ID
        );

        return renderJson(result);
    }

    @ApiOperation(value = "新增话题收藏")
    @RequestMapping(value = "/topic/user/bookmark/admin/v1/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> saveV1(@RequestBody TopicUserBookmark body) {
        validateRequest(
                body,
                TopicUserBookmark.APP_ID,
                TopicUserBookmark.TOPIC_ID,
                TopicUserBookmark.MEMBER_ID
        );

//        Boolean result = topicUserBookmarkService.save(body, Util.getRandomUUID(), body.getAppId(), TopicUserBookmarkRouter.TOPIC_USER_BOOKMARK_V1_SAVE, body.getSystemRequestUserId());

//        return renderJson(result);
        return renderJson(null);
    }

    @ApiOperation(value = "修改话题收藏")
    @RequestMapping(value = "/topic/user/bookmark/admin/v1/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updateV1(@RequestBody TopicUserBookmark body) {
        validateRequest(
                body,
                TopicUserBookmark.TOPIC_USER_BOOKMARK_ID,
                TopicUserBookmark.APP_ID,
                TopicUserBookmark.TOPIC_ID,
                TopicUserBookmark.MEMBER_ID,
                TopicUserBookmark.SYSTEM_VERSION
        );

//        Boolean result = topicUserBookmarkService.update(body, body.getTopicUserBookmarkId(), body.getAppId(), TopicUserBookmarkRouter.TOPIC_USER_BOOKMARK_V1_UPDATE, body.getSystemRequestUserId(), body.getSystemVersion());

//        return renderJson(result);
        return renderJson(null);
    }

    @ApiOperation(value = "删除话题收藏")
    @RequestMapping(value = "/topic/user/bookmark/admin/v1/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> deleteV1(@RequestBody TopicUserBookmark body) {
        validateRequest(
                body,
                TopicUserBookmark.TOPIC_USER_BOOKMARK_ID,
                TopicUserBookmark.APP_ID,
                TopicUserBookmark.SYSTEM_VERSION
        );

//        Boolean result = topicUserBookmarkService.delete(body.getTopicUserBookmarkId(), body.getAppId(), TopicUserBookmarkRouter.TOPIC_USER_BOOKMARK_V1_DELETE, body.getSystemRequestUserId(), body.getSystemVersion());

//        return renderJson(result);
        return renderJson(null);
    }
    
    @ApiOperation(value = "动态收藏数据同步")
    @RequestMapping(value = "/topic/user/bookmark/admin/v1/synchronize", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> replaceV1(@RequestBody TopicUserBookmark body) {
    	List<TopicUserBookmark> topicUserBookmarks = topicUserBookmarkService.listByMysql();
    	
    	for (TopicUserBookmark topicUserBookmark : topicUserBookmarks) {
			TopicUserBookmarkView userBookmarkView = new TopicUserBookmarkView();
			userBookmarkView.putAll(topicUserBookmark);
			topicUserBookmarkService.update(userBookmarkView);
		}

        return renderJson(true);
    }

}