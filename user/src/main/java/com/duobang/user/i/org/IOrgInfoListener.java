package com.duobang.user.i.org;

import com.duobang.user.core.org.OrganizationInfo;

public interface IOrgInfoListener {

    void onLoadOrgInfo(OrganizationInfo info);

    void onFailure(String message);
}
