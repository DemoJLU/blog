package com.danxiaochong.blog.pojo.system;

import java.util.List;

public class MenuNode implements Comparable {

    private int id;

    private String name;

    private String code;

    private int order;

    private List<MenuNode> children;

    public MenuNode() {

    }

    public MenuNode(int id, String name, String code, int order) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<MenuNode> getChildren() {
        return children;
    }

    public void setChildren(List<MenuNode> children) {
        this.children = children;
    }

    @Override
    public int compareTo(Object o) {
        MenuNode m = (MenuNode) o;
        if (this.order == m.getOrder()) {
            return this.id - m.getId();
        }
        return this.order - m.getOrder();
    }

}
