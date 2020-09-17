package com.duobang.user.invitationCode.presenter;

import com.duobang.middleware.cache.PreferenceManager;
import com.duobang.user.core.org.OrganizationInfo;
import com.duobang.user.core.org.imp.OrganizationNetWork;
import com.duobang.user.i.org.IOrgInfoListener;
import com.duobang.user.invitationCode.contract.InvitationContract;
import com.duobang.pms_lib.framework.BasePresenter;
import com.duobang.pms_lib.utils.MessageUtils;

public class InvitationPresenter extends BasePresenter<InvitationContract.View> implements InvitationContract.Presenter{

    @Override
    protected void onStart() {
        checkViewAttached();
        joinOrg();
    }

    @Override
    public void joinOrg() {
        OrganizationNetWork.getInstance().joinOrganization(getView().getInvitationCode(), new IOrgInfoListener() {
            @Override
            public void onLoadOrgInfo(OrganizationInfo info) {
                saveOrgInfo(info);
                getView().startMainView();
            }

            @Override
            public void onFailure(String message) {
                MessageUtils.shortToast(message);
            }
        });
    }

    private void saveOrgInfo(OrganizationInfo info) {
        if (info == null){
            return;
        }
        PreferenceManager.getInstance().getUserPreferences().saveUserOrgId(info.getId());
        PreferenceManager.getInstance().getUserPreferences().saveUserOrgName(info.getName());
        PreferenceManager.getInstance().getUserPreferences().saveUserOrgState(info.getState());
        PreferenceManager.getInstance().getUserPreferences().saveUserOrgType(info.getType());
    }
}
