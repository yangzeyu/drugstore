package com.jsmscp.dr.service.impl;

import com.jsmscp.dr.dto.OperationDto;
import com.jsmscp.dr.entity.SysMenuOperation;
import com.jsmscp.dr.mapper.SysMenuOperationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.dto.MenuDto;
import com.jsmscp.dr.entity.SysMenu;
import com.jsmscp.dr.mapper.SysMenuMapper;
import com.jsmscp.dr.service.SysMenuService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
public class SysMenuServiceImpl implements SysMenuService {

    private SysMenuMapper sysMenuMapper;

    private SysMenuOperationMapper sysMenuOperationMapper;

    @Autowired
    public SysMenuServiceImpl(SysMenuMapper sysMenuMapper) {
        this.sysMenuMapper = sysMenuMapper;
    }


    /**
     * 查询菜单列表
     * @param key
     * @param pageNo
     * @param parentId
     * @return
     */
    @Override
    public List<SysMenu> list(String key, Integer pageNo, Integer parentId) {
        Byte pageSize = Constant.DEFAULT_PAGE_SIZE;
        pageNo = (pageNo - 1) * pageSize;
        List<SysMenu> list = sysMenuMapper.list(key, parentId, pageNo, pageSize, Constant.IS_DELETED_NO);

        return list;
    }

    /**
     * 新增菜单
     * @param name
     * @param icon
     * @param code
     * @param url
     * @param parentId
     * @param num
     * @return
     */
    @Override
    public Integer addMenu(String name, String icon, String code, String url, Integer parentId, Integer num) {
        SysMenu menu = new SysMenu();
        menu.setName(name);
        menu.setCode(code);
        menu.setCreateAt(new Date());
        menu.setIcon(icon);
        menu.setNum(num);
        menu.setParentId(parentId);
        menu.setUrl(url);
        sysMenuMapper.insertSelective(menu);
        return menu.getId();
    }

    /**
     * 更新菜单
     * @param id
     * @param name
     * @param code
     * @param icon
     * @param url
     * @param num
     * @param parentId
     */
    @Override
    public void updateMenu(Integer id, String name, String code, String icon, String url, Integer num,
                           Integer parentId) {
        SysMenu menu = new SysMenu();
        menu.setId(id);
        menu.setName(name);
        menu.setCode(code);
        menu.setIcon(icon);
        menu.setUrl(url);
        menu.setNum(num);
        menu.setParentId(parentId);
        sysMenuMapper.updateByPrimaryKeySelective(menu);
    }

    /**
     * 查询单个菜单
     * @param menuId
     * @return
     */
    @Override
    public SysMenu findById(Integer menuId) {
        SysMenu menu = sysMenuMapper.findOne(menuId);
        return menu;
    }

    /**
     * 删除菜单(假删除)
     * @param id
     * @param isDeletedYes
     */
    @Override
    public void updateIsDeleted(Integer id, byte isDeletedYes) {
        SysMenu menu = new SysMenu();
        menu.setId(id);
        menu.setIsDeleted(isDeletedYes);
        menu.setUpdateAt(new Date());
        sysMenuMapper.updateByPrimaryKeySelective(menu);
    }

    /**
     * 列表查询菜单类表数量
     * @param key
     * @param parentId
     * @return
     */
    @Override
    public HashMap<String, Object> findTotal(String key, Integer parentId) {
        Byte pageSize = Constant.DEFAULT_PAGE_SIZE;
        HashMap<String, Object> map = new HashMap<>();
        int count = sysMenuMapper.findCount(key, parentId, Constant.IS_DELETED_NO);
        int total = count / pageSize;
        if (count % pageSize > 0) {
            total += 1;
        }
        map.put("count", count);
        map.put("total", total);
        return map;
    }

    /**
     * 查询所有一级菜单
     * @param isDeleted
     * @param parentId
     * @return
     */
    @Override
    public List<SysMenu> findLevelOneMenu(byte isDeleted, byte parentId) {
        List<SysMenu> menus = sysMenuMapper.findLevelOneMenu(parentId, isDeleted);
        return menus;
    }

    /**
     * 查询所有菜单
     * @param isDeletedNo
     * @return
     */
    @Override
    public List<MenuDto> findAllMenu(Byte isDeletedNo) {
        List<SysMenu> menus = sysMenuMapper.findAllMenu(isDeletedNo);
        List<MenuDto> list = new ArrayList<>();
        for (SysMenu menu : menus) {
            if (menu.getParentId().equals(Constant.SYS_MENU_TOP_PARENT_ID)) {
                Integer topId = menu.getId();
                MenuDto dto = new MenuDto();
                convertMenu(dto, menu);
                for (SysMenu sysMenu : menus) {
                    if (topId.equals(sysMenu.getParentId())) {
                        MenuDto subDto = new MenuDto();
                        convertMenu(subDto, sysMenu);
                        dto.addSubMenu(subDto);
                    }
                }
                list.add(dto);
            }
        }
        return list;
    }

    /**
     * 查询菜单树
     * @return
     */
    @Override
    public List<MenuDto> findAllMenuRoles() {
        List<SysMenu> menus = sysMenuMapper.findAllMenu(Constant.IS_DELETED_NO);
        List<SysMenuOperation> operations = sysMenuOperationMapper.findAllOperation();
        List<MenuDto> list = new ArrayList<>();
        for (SysMenu menu : menus) {
            if (menu.getParentId().equals(Constant.SYS_MENU_TOP_PARENT_ID)) {
                Integer topId = menu.getId();
                MenuDto dto = new MenuDto();
                convertMenu(dto, menu);
                for (SysMenu sysMenu : menus) {
                    if (topId.equals(sysMenu.getParentId())) {
                        MenuDto subDto = new MenuDto();
                        convertMenu(subDto, sysMenu);
                        dto.addSubMenu(subDto);
                        for (SysMenuOperation operation : operations) {
                            if (sysMenu.getId().equals(operation.getMenuId())) {
                                convertOperation(subDto, operation);
                            }
                        }
                    }
                }
                list.add(dto);
            }
        }
        return list;
    }

    /**
     * 查询所有页面按钮
     * @param menuId
     * @param pageNo
     * @return
     */
    @Override
    public List<SysMenuOperation> findAllOperations(Integer menuId, Integer pageNo) {
        Byte pageSize = Constant.DEFAULT_PAGE_SIZE;
        pageNo = (pageNo - 1) * pageSize;
        return sysMenuOperationMapper.findMenuOperation(pageNo, pageSize, menuId);
    }

    /**
     * 查询所有页面按钮数量
     * @param menuId
     * @return
     */
    @Override
    public HashMap<String, Object> findOperationTotal(Integer menuId) {
        Byte pageSize = Constant.DEFAULT_PAGE_SIZE;
        HashMap<String, Object> map = new HashMap<>();
        int count = sysMenuOperationMapper.findOperationCount(menuId);
        int total = count / pageSize;
        if (count % pageSize > 0) {
            total += 1;
        }
        map.put("count", count);
        map.put("total", total);
        return map;
    }

    /**
     * 根据页面按钮id查询页面按钮
     * @param operationId
     * @return
     */
    @Override
    public SysMenuOperation findByOperationId(Integer operationId) {
        return sysMenuOperationMapper.selectByPrimaryKey(operationId);
    }

    /**
     * 删除页面按钮
     * @param operationId
     */
    @Override
    public void deleteOperation(Integer operationId) {
        sysMenuOperationMapper.deleteByPrimaryKey(operationId);
    }

    /**
     * 添加页面按钮
     * @param menuId
     * @param name
     * @param code
     * @return
     */
    @Override
    public Integer addOperation(Integer menuId, String name, String code) {
        SysMenuOperation sysMenuOperation = new SysMenuOperation();
        sysMenuOperation.setMenuId(menuId);
        sysMenuOperation.setName(name);
        sysMenuOperation.setCode(code);
        sysMenuOperation.setCreateAt(new Date());
        sysMenuOperation.setUpdateAt(new Date());
        sysMenuOperationMapper.insert(sysMenuOperation);
        return sysMenuOperation.getId();
    }

    /**
     * 更新页面按钮信息
     * @param id
     * @param name
     * @param code
     */
    @Override
    public void updateOperation(Integer id, String name, String code) {
        SysMenuOperation sysMenuOperation = sysMenuOperationMapper.selectByPrimaryKey(id);
        sysMenuOperation.setName(name);
        sysMenuOperation.setCode(code);
        sysMenuOperation.setUpdateAt(new Date());
        sysMenuOperationMapper.updateByPrimaryKey(sysMenuOperation);
    }

    /**
     * 整合页面按钮和菜单按钮
     * @param dto
     * @param operation
     */
    private void convertOperation(MenuDto dto, SysMenuOperation operation) {
        OperationDto d = new OperationDto();
        d.setId(operation.getId());
        d.setMenuId(operation.getMenuId());
        d.setCode(operation.getCode());
        d.setName(operation.getName());
        dto.addOperation(d);
    }

    /**
     * 处理菜单信息
     * @param dto
     * @param menu
     */
    private void convertMenu(MenuDto dto, SysMenu menu) {
        dto.setId(menu.getId());
        dto.setName(menu.getName());
        dto.setIcon(menu.getIcon());
        dto.setParentId(menu.getParentId());
        dto.setUrl(menu.getUrl());
        dto.setCode(menu.getCode());
    }

    @Autowired
    public void setSysMenuOperationMapper(SysMenuOperationMapper sysMenuOperationMapper) {
        this.sysMenuOperationMapper = sysMenuOperationMapper;
    }
}
