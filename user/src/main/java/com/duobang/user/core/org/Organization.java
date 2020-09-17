package com.duobang.user.core.org;

import java.util.List;

public class Organization {

    /**
     * {
     * "homeOrgId": "9ca08e73-7833-4b81-bcf6-320c7fbf1bf8",
     * "orgList": [
     * {
     * "id": "9ca08e73-7833-4b81-bcf6-320c7fbf1bf8",
     * "name": "济南周一",
     * "discription": "文化东路88号",
     * "type": 0,
     * "ownerId": "2a765ed2-b219-44bd-8069-1cc33053758a",
     * "parentId": "",
     * "state": 0
     * }
     * ]
     * }
     */
    private String homeOrgId;
    private List<OrganizationInfo> orgList;

    public String getHomeOrgId() {
        return homeOrgId;
    }

    public void setHomeOrgId(String homeOrgId) {
        this.homeOrgId = homeOrgId;
    }

    public List<OrganizationInfo> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<OrganizationInfo> orgList) {
        this.orgList = orgList;
    }

    /**
     * 获取主组织实体
     *
     * @return OrganizationInfo
     */
    public OrganizationInfo getHomeOrgInfo() {
        if (orgList != null) {
            for (int i = 0; i < orgList.size(); i++) {
                if (orgList.get(i).getId().equals(homeOrgId))
                    return orgList.get(i);
            }
        }
        return null;
    }

    /**
     * 设置主组织
     */
    public void setHomeOrg(){
        if (orgList != null){
            for (int i = 0; i < orgList.size(); i++) {
                if (orgList.get(i).getId().equals(homeOrgId)){
                    orgList.get(i).setCheck(true);
                }
            }
        }
    }
}
