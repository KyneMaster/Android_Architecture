package com.duobang.user.core.org;

public class OrganizationInfo {

    /**
     * id : 9ca08e73-7833-4b81-bcf6-320c7fbf1bf8
     * name : 济南周一
     * discription : 文化东路88号
     * type : 0
     * ownerId : 2a765ed2-b219-44bd-8069-1cc33053758a
     * parentId :
     * state : 0
     */

    private String id;
    private String name;
    private String discription;
    private int type;
    private String ownerId;
    private String parentId;
    private int state;
    private boolean check = false;
    private boolean isEdit = false;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }
}
