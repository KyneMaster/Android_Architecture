package com.duobang.pms_lib.i.permission;

public interface PermissionCallBack {
    /**
     * 弹窗权限申请，点击允许返回操作
     */
    void granted();

    /**
     * 检测权限已经成功返回
     */
    void grantSuccess();

    /**
     * 权限申请被拒、失败返回
     *
     * @param onError
     */
    void grantFail(String onError);

}
