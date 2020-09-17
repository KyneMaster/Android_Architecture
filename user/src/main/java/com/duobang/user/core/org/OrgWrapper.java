package com.duobang.user.core.org;

import com.duobang.user.core.login.User;

import java.util.List;

public class OrgWrapper {


    private OrganizationInfo org;
    private List<OrgGroup> groupList;
    /**
     *  用户缓存，取出后强转
     */
    private List<User> userList;

    public OrganizationInfo getOrg() {
        return org;
    }

    public void setOrg(OrganizationInfo org) {
        this.org = org;
    }

    public List<OrgGroup> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<OrgGroup> groupList) {
        this.groupList = groupList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
