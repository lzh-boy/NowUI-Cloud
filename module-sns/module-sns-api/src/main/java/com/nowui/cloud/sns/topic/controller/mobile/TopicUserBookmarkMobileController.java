package com.nowui.cloud.sns.topic.controller.mobile;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.nowui.cloud.controller.BaseController;
import com.nowui.cloud.exception.BusinessException;
import com.nowui.cloud.member.member.entity.Member;
import com.nowui.cloud.member.member.rpc.MemberRpc;
import com.nowui.cloud.member.member.view.MemberView;
import com.nowui.cloud.sns.topic.entity.Topic;
import com.nowui.cloud.sns.topic.entity.TopicUserBookmark;
import com.nowui.cloud.sns.topic.entity.TopicUserUnbookmark;
import com.nowui.cloud.sns.topic.router.TopicUserBookmarkRouter;
import com.nowui.cloud.sns.topic.service.TopicUserBookmarkService;
import com.nowui.cloud.sns.topic.service.TopicUserUnbookmarkService;
import com.nowui.cloud.sns.topic.view.TopicUserBookmarkView;
import com.nowui.cloud.sns.topic.view.TopicUserUnbookmarkView;
import com.nowui.cloud.sns.topic.view.TopicUserUnlikeView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 话题收藏移动端控制器
 *
 * @author xupengfei
 *
 * 2018-01-08
 */
@Api(value = "话题收藏", description = "话题收藏移动端接口管理")
@RestController
public class TopicUserBookmarkMobileController extends BaseController {
	
	@Autowired
    private TopicUserBookmarkService topicUserBookmarkService;

	@Autowired
	private TopicUserUnbookmarkService topicUserUnbookmarkService;
	
	@Autowired
	private MemberRpc memberRpc;
	
	@ApiOperation(value = "新增话题收藏")
    @RequestMapping(value = "/topic/user/bookmark/mobile/v1/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> saveV1() {
		TopicUserBookmark body = getEntry(TopicUserBookmark.class);
        validateRequest(
                body,
                TopicUserBookmark.APP_ID,
                TopicUserBookmark.TOPIC_ID
        );
        
        String appId = body.getAppId();
        String topicId = body.getTopicId();
        String theBookMarkUserId = body.getSystemRequestUserId();
        MemberView member = memberRpc.findByUserIdV1(theBookMarkUserId);
        String theBookMarkMemberId = member.getMemberId();
        String requestUserId = body.getSystemRequestUserId();
        
        TopicUserBookmarkView bookmark = topicUserBookmarkService.findByTopicIdAndMemberId(topicId, theBookMarkMemberId);
        
        if (bookmark != null) {
			throw new BusinessException("已经收藏过了");
		}
        // 如果没有收藏过,新增
        TopicUserBookmark result = topicUserBookmarkService.save(appId, topicId, theBookMarkMemberId, requestUserId);
        
        boolean success = false;
        if (result != null) {
        	//去取消收藏表删除记录
            TopicUserUnbookmark unbookmark = topicUserUnbookmarkService.deleteByTopicIdAndMemberId(topicId, theBookMarkMemberId, appId, requestUserId);
            
            if (unbookmark != null) {
            	//TODO 删除取消收藏记录(MongoDB)
                TopicUserUnbookmarkView unbookmarkView = JSON.parseObject(unbookmark.toJSONString(), TopicUserUnbookmarkView.class);
                topicUserUnbookmarkService.delete(unbookmarkView);
			}
            
            //TODO 新增点赞收藏记录(MOngoDB)
            TopicUserBookmarkView bookmarkView = JSON.parseObject(result.toJSONString(), TopicUserBookmarkView.class);
            topicUserBookmarkService.save(bookmarkView);
            
            //sendMessage(result, TopicUserBookmarkRouter.TOPIC_USER_BOOKMARK_V1_SAVE, appId, requestUserId);
            
            success = true;
        }

        return renderJson(success);
    }
}