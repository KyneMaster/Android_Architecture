package com.duobang.user.i.org;

import com.duobang.user.core.org.Organization;

public interface IPersonOrgListener {

    void onLoadPersonOrg(Organization org);

    void onFailure(String message);
}
