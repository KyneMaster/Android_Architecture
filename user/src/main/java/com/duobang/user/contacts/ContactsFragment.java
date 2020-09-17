package com.duobang.user.contacts;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.duobang.middleware.constant.IPmsConstant;
import com.duobang.middleware.fragment.BaseFragment;
import com.duobang.middleware.router.AppRoute;
import com.duobang.user.R;
import com.duobang.user.contacts.contract.ContactsContract;
import com.duobang.user.contacts.imp.OrgStructureActivity;
import com.duobang.user.contacts.presenter.ContactsPresenter;
import com.duobang.user.core.org.OrganizationInfo;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ContactsFragment extends BaseFragment<ContactsPresenter, ContactsContract.View> implements ContactsContract.View{

    private RecyclerView mRecyclerView;

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void initView() {
        ImageView search = findViewById(R.id.search_contacts);
        search.setOnClickListener(getOnClickListener());
        TextView friend = findViewById(R.id.friend_contacts);
        friend.setOnClickListener(getOnClickListener());
        TextView group = findViewById(R.id.org_group_contacts);
        group.setOnClickListener(getOnClickListener());
        findViewById(R.id.org_structure_contacts).setOnClickListener(getOnClickListener());
        //TODO 隐藏无内容组件
        search.setVisibility(View.INVISIBLE);
        friend.setVisibility(View.GONE);
        group.setVisibility(View.GONE);
    }

    @Override
    public ContactsPresenter onCreatePresenter() {
        return new ContactsPresenter();
    }

    @Override
    protected void onStartLoadData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_contacts){
            AppRoute.openSearchView(getActivity(), IPmsConstant.SEARCH_TYPE.CONTACTS);
        } else if (v.getId() == R.id.friend_contacts){
            openFriendView();
        } else if (v.getId() == R.id.org_group_contacts){
            openOrgGroupView();
        } else if (v.getId() == R.id.org_structure_contacts){
            openOrgStructureView();
        }
    }

    private void openOrgStructureView() {
        startActivity(new Intent(getActivity(), OrgStructureActivity.class));
    }

    private void openOrgGroupView() {

    }

    private void openFriendView() {

    }

    @Override
    public void setupOrganizationView(List<OrganizationInfo> organizations) {

    }
}
