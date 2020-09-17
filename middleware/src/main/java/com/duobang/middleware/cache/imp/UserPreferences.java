package com.duobang.middleware.cache.imp;

import com.duobang.middleware.constant.ISpConstant;
import com.duobang.middleware.i.cache.IUserPreferences;
import com.duobang.pms_lib.core.sp.DefaultPreference;

public class UserPreferences extends DefaultPreference implements IUserPreferences {

    private static final String TAG = "UserPreferences";

    public UserPreferences() {
        super();
    }

    @Override
    public String getPreferenceName() {
        return TAG;
    }

    @Override
    public String getUserId() {
        return getPreferences().getString(ISpConstant.USER.USER_ID, null);
    }

    @Override
    public void saveUserId(String userId) {
        getPreferences().putString(ISpConstant.USER.USER_ID, userId).commit();
    }

    @Override
    public String getUserName() {
        return getPreferences().getString(ISpConstant.USER.USER_NAME, null);
    }

    @Override
    public void saveUserName(String username) {
        getPreferences().putString(ISpConstant.USER.USER_NAME, username).commit();
    }

    @Override
    public String getNickName() {
        return getPreferences().getString(ISpConstant.USER.USER_NICK_NAME, null);
    }

    @Override
    public void saveNickName(String username) {
        getPreferences().putString(ISpConstant.USER.USER_NICK_NAME, username).commit();
    }

    @Override
    public String getUserAvatar() {
        return getPreferences().getString(ISpConstant.USER.USER_AVATAR, null);
    }

    @Override
    public void saveUserAvatar(String avatar) {
        getPreferences().putString(ISpConstant.USER.USER_AVATAR, avatar).commit();
    }

    @Override
    public String getUserPhone() {
        return getPreferences().getString(ISpConstant.USER.USER_PHONE, null);
    }

    @Override
    public void saveUserPhone(String phone) {
        getPreferences().putString(ISpConstant.USER.USER_PHONE, phone).commit();
    }

    @Override
    public int getUserState() {
        return getPreferences().getInt(ISpConstant.USER.USER_STATE, -1);
    }

    @Override
    public void saveUserState(int phone) {
        getPreferences().putInt(ISpConstant.USER.USER_STATE, phone).commit();
    }

    @Override
    public String getUserOrgId() {
        return getPreferences().getString(ISpConstant.USER.USER_ORG_ID, null);
    }

    @Override
    public void saveUserOrgId(String orgId) {
        getPreferences().putString(ISpConstant.USER.USER_ORG_ID, orgId).commit();
    }

    @Override
    public String getUserOrgName() {
        return getPreferences().getString(ISpConstant.USER.USER_ORG_NAME, null);
    }

    @Override
    public void saveUserOrgName(String orgName) {
        getPreferences().putString(ISpConstant.USER.USER_ORG_NAME, orgName).commit();
    }

    @Override
    public int getUserOrgState() {
        return getPreferences().getInt(ISpConstant.USER.USER_ORG_STATE, -1);
    }

    @Override
    public void saveUserOrgState(int orgState) {
        getPreferences().putInt(ISpConstant.USER.USER_ORG_STATE, orgState).commit();
    }

    @Override
    public int getUserOrgType() {
        return getPreferences().getInt(ISpConstant.USER.USER_ORG_TYPE, -1);
    }

    @Override
    public void saveUserOrgType(int orgType) {
        getPreferences().putInt(ISpConstant.USER.USER_ORG_TYPE, orgType).commit();
    }

    @Override
    public boolean isJoinOrg() {
        return getUserOrgId() != null;
    }

    @Override
    public void clear() {
        super.clear();
    }
}
