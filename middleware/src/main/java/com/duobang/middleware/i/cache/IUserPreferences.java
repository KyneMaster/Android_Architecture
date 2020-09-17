package com.duobang.middleware.i.cache;

/**
 * 用户相关信息缓存
 */
public interface IUserPreferences {

    String getUserId();

    void saveUserId(String userId);

    String getUserName();

    void saveUserName(String username);

    String getNickName();

    void saveNickName(String username);

    String getUserAvatar();

    void saveUserAvatar(String avatar);

    String getUserPhone();

    void saveUserPhone(String phone);

    int getUserState();

    void saveUserState(int phone);

    String getUserOrgId();

    void saveUserOrgId(String orgId);

    String getUserOrgName();

    void saveUserOrgName(String orgName);

    int getUserOrgState();

    void saveUserOrgState(int orgState);

    int getUserOrgType();

    void saveUserOrgType(int orgType);

    boolean isJoinOrg();

    void clear();
}
