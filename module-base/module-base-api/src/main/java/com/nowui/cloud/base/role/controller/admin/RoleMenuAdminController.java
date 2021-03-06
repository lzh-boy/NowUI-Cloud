package com.nowui.cloud.base.role.controller.admin;

import com.nowui.cloud.base.role.view.RoleMenuView;
import com.nowui.cloud.controller.BaseController;
import com.nowui.cloud.util.Util;
import com.nowui.cloud.base.role.entity.RoleMenu;
import com.nowui.cloud.base.role.service.RoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 角色菜单管理端控制器
 *
 * @author marcus
 * <p>
 * 2018-01-02
 */
@Api(value = "角色菜单", description = "角色菜单管理端接口管理")
@RestController
public class RoleMenuAdminController extends BaseController {

    @Autowired
    private RoleMenuService roleMenuService;

    @ApiOperation(value = "角色菜单列表")
    @RequestMapping(value = "/role/menu/admin/v1/list", method = {RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> listV1() {
        RoleMenu roleMenuEntity = getEntry(RoleMenu.class);

        validateRequest(
                roleMenuEntity,
                RoleMenu.APP_ID,
                RoleMenu.ROLE_ID,
                RoleMenu.MENU_ID,
                RoleMenu.PAGE_INDEX,
                RoleMenu.PAGE_SIZE
        );

        Integer resultTotal = roleMenuService.countForAdmin(roleMenuEntity.getAppId(), roleMenuEntity.getRoleId(), roleMenuEntity.getMenuId());
        List<RoleMenu> resultList = roleMenuService.listForAdmin(roleMenuEntity.getAppId(), roleMenuEntity.getRoleId(), roleMenuEntity.getMenuId(), roleMenuEntity.getPageIndex(), roleMenuEntity.getPageSize());

        validateResponse(
                RoleMenu.ROLE_MENU_ID,
                RoleMenu.ROLE_ID,
                RoleMenu.MENU_ID
        );

        return renderJson(resultTotal, resultList);
    }

    @ApiOperation(value = "角色菜单信息")
    @RequestMapping(value = "/role/menu/admin/v1/find", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> findV1() {
        RoleMenu roleMenuEntity = getEntry(RoleMenu.class);

        validateRequest(
                roleMenuEntity,
                RoleMenu.APP_ID,
                RoleMenu.ROLE_MENU_ID
        );

        RoleMenuView result = roleMenuService.find(roleMenuEntity.getRoleMenuId());

        validateResponse(
                RoleMenu.ROLE_MENU_ID,
                RoleMenu.ROLE_ID,
                RoleMenu.MENU_ID
        );

        return renderJson(result);
    }

    @ApiOperation(value = "新增角色菜单")
    @RequestMapping(value = "/role/menu/admin/v1/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> saveV1() {
        RoleMenu roleMenuEntity = getEntry(RoleMenu.class);

        validateRequest(
                roleMenuEntity,
                RoleMenu.APP_ID,
                RoleMenu.ROLE_ID,
                RoleMenu.MENU_ID
        );

        RoleMenu result = roleMenuService.save(roleMenuEntity, Util.getRandomUUID(), roleMenuEntity.getSystemCreateUserId());

        Boolean success = false;

        if (result != null) {
            success = true;
        }

        return renderJson(success);
    }

    @ApiOperation(value = "修改角色菜单")
    @RequestMapping(value = "/role/menu/admin/v1/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updateV1() {
        RoleMenu roleMenuEntity = getEntry(RoleMenu.class);

        validateRequest(
                roleMenuEntity,
                RoleMenu.ROLE_MENU_ID,
                RoleMenu.APP_ID,
                RoleMenu.ROLE_ID,
                RoleMenu.MENU_ID,
                RoleMenu.SYSTEM_VERSION
        );

        RoleMenu result = roleMenuService.update(roleMenuEntity, roleMenuEntity.getRoleMenuId(), roleMenuEntity.getSystemUpdateUserId(), roleMenuEntity.getSystemVersion());

        Boolean success = false;

        if (result != null) {
            success = true;
        }

        return renderJson(success);
    }

    @ApiOperation(value = "删除角色菜单")
    @RequestMapping(value = "/role/menu/admin/v1/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> deleteV1() {
        RoleMenu roleMenuEntity = getEntry(RoleMenu.class);

        validateRequest(
                roleMenuEntity,
                RoleMenu.ROLE_MENU_ID,
                RoleMenu.APP_ID,
                RoleMenu.SYSTEM_VERSION
        );

        RoleMenu result = roleMenuService.delete(roleMenuEntity.getRoleMenuId(), roleMenuEntity.getSystemUpdateUserId(), roleMenuEntity.getSystemVersion());

        Boolean success = false;

        if (result != null) {
            success = true;
        }

        return renderJson(success);
    }

    @ApiOperation(value = "角色数据同步")
    @RequestMapping(value = "/role/menu/admin/v1/synchronize", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> synchronizeV1() {
        List<RoleMenu> roleMenuList = roleMenuService.listByMysql();

        for(RoleMenu roleMenu : roleMenuList) {
            RoleMenuView roleMenuView = new RoleMenuView();
            roleMenuView.putAll(roleMenu);

            roleMenuService.update(roleMenuView);
        }

        return renderJson(true);
    }

}