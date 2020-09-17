package com.duobang.user.i.org;

import com.duobang.user.core.org.OrgWrapper;

public interface IOrgWrapperListener {

    void onOrgGroupUserWrapper(OrgWrapper wrapper);

    void onFailure(String message);
}
