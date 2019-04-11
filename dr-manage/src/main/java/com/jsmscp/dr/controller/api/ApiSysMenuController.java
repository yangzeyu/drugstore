package com.jsmscp.dr.controller.api;

import com.jsmscp.dr.entity.SysMenuOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.entity.SysMenu;
import com.jsmscp.dr.service.SysMenuService;
import com.jsmscp.dr.util.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sysMenu")
public class ApiSysMenuController {

    private static final Logger LOG = LoggerFactory.getLogger(ApiSysMenuController.class);

    private SysMenuService sysMenuService;

    @Autowired
    public ApiSysMenuController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    /**
     * 列表显示菜单
     *
     * @param key
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Response list(@RequestParam(value = "key", required =  false) String key,
                         @RequestParam(value = "pageNo", required = false) Integer pageNo,
                         @RequestParam(value = "parentId", required = false) Integer parentId) {
        List<SysMenu> list = sysMenuService.list(key, pageNo, parentId);
        HashMap<String, Object> map = sysMenuService.findTotal(key, parentId);
        map.put("list", list);
        map.put("pageSize", Constant.DEFAULT_PAGE_SIZE);
        map.put("pageNo", pageNo);
        return Response.buildSuccess(map);
    }

    /**
     * 单个菜单查询
     *
     * @param menuId
     * @return
     */
    @RequestMapping(value = "findOne", method = RequestMethod.GET)
    public Response toEdit(@RequestParam(value = "menuId", required = false) Integer menuId) {
        SysMenu menu = sysMenuService.findById(menuId);
        return Response.buildSuccess(menu);
    }

    /**
     * 新增菜单
     *
     * @param name
     * @param icon
     * @param code
     * @param url
     * @param parentId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "addMenu", method = RequestMethod.POST)
    public Response addMenu(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "icon", required = false) String icon,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "url") String url,
            @RequestParam(value = "parentId", required = false) Integer parentId,
            @RequestParam(value = "num", required = false) Integer num) {
        Integer id = sysMenuService.addMenu(name, icon, code, url, parentId, num);
        Response response = Response.buildSuccess(id);
        return response;
    }

    /**
     * 修改菜单
     *
     * @param id
     * @param icon
     * @param url
     * @param num
     * @param parentId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "editMenu", method = RequestMethod.POST)
    public Response editMenu(@RequestParam(value = "id") Integer id,
                             @RequestParam(value = "name") String name,
                             @RequestParam(value = "icon", required = false) String icon,
                             @RequestParam(value = "code", required = false) String code,
                             @RequestParam(value = "url", required = false) String url,
                             @RequestParam(value = "num", required = false) Integer num,
                             @RequestParam(value = "parentId", required = false) Integer parentId) throws Exception {
        sysMenuService.updateMenu(id, name, code, icon, url, num, parentId);
        return Response.success();
    }

    /**
     * 删除菜单 (修改is_deleted状态)
     *
     * @param menuId
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Response deleteMenu(@RequestParam(value = "menuId") Integer menuId) {
        sysMenuService.updateIsDeleted(menuId, Constant.IS_DELETED_YES);
        return Response.success();
    }

    /**
     * 查询一级菜单
     *
     * @return 菜单信息
     */
    @RequestMapping(value = "findLevelOneMenu", method = RequestMethod.GET)
    public Response findLevelOneMenu() {
        List<SysMenu> levelOne = sysMenuService.findLevelOneMenu(Constant.IS_DELETED_NO, Constant.MENU_PARENT_ID);
        return Response.buildSuccess(levelOne);
    }

    /**
     * 列表显示功能
     *
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "{menuId}/operation/list", method = RequestMethod.GET)
    public Response allOperation(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                         @PathVariable(value = "menuId") Integer menuId) {
        List<SysMenuOperation> operations = sysMenuService.findAllOperations(menuId, pageNo);
        Map<String, Object> map = sysMenuService.findOperationTotal(menuId);
        map.put("list", operations);
        map.put("pageSize", Constant.DEFAULT_PAGE_SIZE);
        map.put("pageNo", pageNo);
        return Response.buildSuccess(map);
    }

    /**
     * 单个功能查询
     *
     * @param operationId
     * @return
     */
    @RequestMapping(value = "{menuId}/operation/findOne", method = RequestMethod.GET)
    public Response toOperationEdit(Integer operationId) {
        SysMenuOperation operation = sysMenuService.findByOperationId(operationId);
        return Response.buildSuccess(operation);
    }

    /**
     * 删除功能
     * @param operationId
     * @return
     */
    @RequestMapping(value = "{menuId}/operation/delete", method = RequestMethod.POST)
    public Response deleteOperation(Integer operationId) {
        sysMenuService.deleteOperation(operationId);
        return Response.success();
    }

    /**
     * 添加功能
     * @param menuId
     * @param name
     * @param code
     * @return
     */
    @RequestMapping(value = "{menuId}/operation/add", method = RequestMethod.POST)
    public Response addOperation(@PathVariable(value = "menuId") Integer menuId, String name, String code) {
        Integer id = sysMenuService.addOperation(menuId, name, code);
        Response response = Response.buildSuccess(id);
        return response;
    }


    /**
     * 更新功能
     * @param id
     * @param name
     * @param code
     * @return
     */
    @RequestMapping(value = "{menuId}/operation/edit", method = RequestMethod.POST)
    public Response editOperation(Integer id, String name, String code) {
        sysMenuService.updateOperation(id, name, code);
        return Response.success();
    }
}
