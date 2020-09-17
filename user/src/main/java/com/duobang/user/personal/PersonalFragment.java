package com.duobang.user.personal;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duobang.middleware.cache.PreferenceManager;
import com.duobang.middleware.common.AppImageLoader;
import com.duobang.middleware.constant.REQUEST_CODE;
import com.duobang.middleware.contract.DefaultPresenter;
import com.duobang.middleware.fragment.DefaultFragment;
import com.duobang.middleware.router.AppRoute;
import com.duobang.middleware.weight.AvatarView;
import com.duobang.pms_lib.single_click.SingleClick;
import com.duobang.user.R;

@Route(path = AppRoute.MAIN_PERSONAL)
public class PersonalFragment extends DefaultFragment {

    private AvatarView avatar;
    private TextView name;

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initView() {
        avatar = findViewById(R.id.user_icon_person);
        name = findViewById(R.id.user_name_person);
        findViewById(R.id.setting_person).setOnClickListener(getOnClickListener());
        avatar.setOnClickListener(getOnClickListener());
        name.setOnClickListener(getOnClickListener());
    }

    @Override
    protected void initData() {
        super.initData();
        setupView();
//        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_personal, AppRoute.newWorkbenchFragment()).commitAllowingStateLoss();
    }

    public void setupView() {
        String userAvatar = PreferenceManager.getInstance().getUserPreferences().getUserAvatar();
        String nickName = PreferenceManager.getInstance().getUserPreferences().getNickName();
        AppImageLoader.displayAvatar(userAvatar, nickName, avatar);
        name.setText(nickName);
    }

    @Override
    public DefaultPresenter onCreatePresenter() {
        return new DefaultPresenter();
    }

    @Override
    protected void onStartLoadData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.setting_person){
            AppRoute.openSettingView();
        } else if (v.getId() == R.id.user_icon_person){
            openPersonalView();
        } else if (v.getId() == R.id.user_name_person){
            openPersonalView();
        }
    }

    @SingleClick
    public void openPersonalView() {
        AppRoute.openPersonalView().navigation(getActivity(), REQUEST_CODE.CHANGE_PERSONAL);
    }
}
