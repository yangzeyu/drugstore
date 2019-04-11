package com.jsmscp.dr.dto;


import java.util.ArrayList;
import java.util.List;

public class MenuDto {
    private Integer id;

    private String name;

    private String code;

    private Byte num;

    private String icon;

    private Integer parentId;

    private String url;

    private List<MenuDto> subMenus = new ArrayList<>();

    private List<OperationDto> operations;

    public void addSubMenu(MenuDto menu) {
        subMenus.add(menu);
    }

    public void addOperation(OperationDto o) {
        if (operations == null) {
            operations = new ArrayList<>();
        }
        operations.add(o);
    }

    public List<OperationDto> getOperations() {
        return operations;
    }

    public void setOperations(List<OperationDto> operations) {
        this.operations = operations;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Byte getNum() {
        return num;
    }

    public void setNum(Byte num) {
        this.num = num;
    }

    public List<MenuDto> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<MenuDto> subMenus) {
        this.subMenus = subMenus;
    }
}
