package com.nowui.cloud.cms.navigation.controller.admin;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nowui.cloud.cms.navigation.entity.Navigation;
import com.nowui.cloud.cms.navigation.service.NavigationService;
import com.nowui.cloud.cms.toolbar.entity.Toolbar;
import com.nowui.cloud.controller.BaseController;
import com.nowui.cloud.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 导航栏管理端控制器
 *
 * @author marcus
 *
 * 2018-01-02
 */
@Api(value = "导航栏", description = "导航栏管理端接口管理")
@RestController
public class NavigationAdminController extends BaseController {

    @Autowired
    private NavigationService navigationService;
    
    @ApiOperation(value = "导航栏列表")
    @RequestMapping(value = "/navigation/admin/v1/list", method = {RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> listV1(@RequestBody Navigation body) {
        validateRequest(
                body,
                Navigation.APP_ID,
                Navigation.NAVIGATION_CATEGORY_CODE,
                Navigation.NAVIGATION_CODE,
                Navigation.NAVIGATION_NAME,
                Navigation.PAGE_INDEX,
                Navigation.PAGE_SIZE
        );

        Integer resultTotal = navigationService.countForAdmin(body.getAppId() , body.getNavigationCategoryCode(), body.getNavigationCode(), body.getNavigationName());
        List<Navigation> resultList = navigationService.listForAdmin(body.getAppId(), body.getNavigationCategoryCode(), body.getNavigationCode(), body.getNavigationName(), body.getM(), body.getN());
        
        String fileIds = Util.beanToFieldString(resultList, Navigation.NAVIGATION_IMAGE);
//        List<File> fileList = fileRpc.findsV1(fileIds);
        
//        resultList = Util.beanAddField(resultList, Navigation.NAVIGATION_IMAGE, fileList, File.FILE_PATH);
        
        validateResponse(
                Navigation.NAVIGATION_ID,
                Navigation.NAVIGATION_CATEGORY_CODE,
                Navigation.NAVIGATION_CODE,
                Navigation.NAVIGATION_NAME,
//                File.FILE_PATH,
                Navigation.NAVIGATION_URL,
                Navigation.NAVIGATION_POSITION,
                Navigation.NAVIGATION_SORT
        );

        return renderJson(resultTotal, resultList);
    }

    @ApiOperation(value = "导航栏信息")
    @RequestMapping(value = "/navigation/admin/v1/find", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> findV1(@RequestBody Navigation body) {
        validateRequest(
                body,
                Navigation.APP_ID,
                Navigation.NAVIGATION_ID
        );

        Navigation result = navigationService.find(body.getNavigationId());

//        File file = fileRpc.findV1(result.getNavigationImage());
//        file.keep(File.FILE_ID, File.FILE_PATH);
//        result.put(Navigation.NAVIGATION_IMAGE, file);
        
        validateResponse(
                Navigation.NAVIGATION_ID,
                Navigation.NAVIGATION_CATEGORY_CODE,
                Navigation.NAVIGATION_CODE,
                Navigation.NAVIGATION_NAME,
                Navigation.NAVIGATION_IMAGE,
                Navigation.NAVIGATION_URL,
                Navigation.NAVIGATION_POSITION,
                Navigation.NAVIGATION_SORT,
                Navigation.SYSTEM_VERSION
        );
        
        return renderJson(result);
    }

    @ApiOperation(value = "新增导航栏")
    @RequestMapping(value = "/navigation/admin/v1/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> saveV1(@RequestBody Navigation body) {
        validateRequest(
                body,
                Navigation.APP_ID,
                Navigation.NAVIGATION_CATEGORY_CODE,
                Navigation.NAVIGATION_CODE,
                Navigation.NAVIGATION_NAME,
                Navigation.NAVIGATION_IMAGE,
                Navigation.NAVIGATION_URL,
                Navigation.NAVIGATION_POSITION,
                Navigation.NAVIGATION_SORT
        );

        Boolean result = navigationService.save(body, Util.getRandomUUID(), body.getSystemRequestUserId());

        return renderJson(result);
    }

    @ApiOperation(value = "修改导航栏")
    @RequestMapping(value = "/navigation/admin/v1/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updateV1(@RequestBody Navigation body) {
        validateRequest(
                body,
                Navigation.NAVIGATION_ID,
                Navigation.APP_ID,
                Navigation.NAVIGATION_CATEGORY_CODE,
                Navigation.NAVIGATION_CODE,
                Navigation.NAVIGATION_NAME,
                Navigation.NAVIGATION_IMAGE,
                Navigation.NAVIGATION_URL,
                Navigation.NAVIGATION_POSITION,
                Navigation.NAVIGATION_SORT,
                Navigation.SYSTEM_VERSION
        );

        Boolean result = navigationService.update(body, body.getNavigationId(), body.getSystemRequestUserId(), body.getSystemVersion());

        return renderJson(result);
    }

    @ApiOperation(value = "删除导航栏")
    @RequestMapping(value = "/navigation/admin/v1/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> deleteV1(@RequestBody Navigation body) {
        validateRequest(
                body,
                Navigation.NAVIGATION_ID,
                Navigation.APP_ID,
                Navigation.SYSTEM_VERSION
        );

        Boolean result = navigationService.delete(body.getNavigationId(), body.getSystemRequestUserId(), body.getSystemVersion());

        return renderJson(result);
    }
    
    @ApiOperation(value = "导航栏重建缓存")
    @RequestMapping(value = "/navigation/admin/v1/replace", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> replaceV1(@RequestBody Navigation body) {
        validateRequest(body, Navigation.NAVIGATION_ID);

        navigationService.replace(body.getNavigationId());

        return renderJson(true);
    }

}