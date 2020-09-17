package com.duobang.user.i.org;

import com.duobang.pms_lib.core.network.DuobangResponse;
import com.duobang.user.core.login.User;
import com.duobang.user.core.org.OrgWrapper;
import com.duobang.user.core.org.Organization;
import com.duobang.user.core.org.OrganizationInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IOrganizationNetApi {

    /**
     * 获取自己所在组织
     *
     * @return DuobangResponse<Organization>
     */
    @GET("api/account/v1/org/my/org/list")
    Observable<DuobangResponse<Organization>> loadPersonOrg();

    /**
     * 通过邀请码加入组织
     *
     * @param code invitationCode
     * @return Response
     * {@link #loadOrgInvitationCode()}
     */
    @POST("api/account/v1/org/invitation-code/{code}")
    Observable<DuobangResponse<OrganizationInfo>> joinOrgByInvitationCode(@Path("code") String code);

    /**
     * 获取组织邀请码
     *
     * @return DuobangResponse<String>
     */
    @GET("api/account/v1/org/my/org/list")
    Observable<DuobangResponse<String>> loadOrgInvitationCode();

    /**
     * 获取组织下全部人员，包括部门
     *
     * @param orgId 组织ID
     * @param hasRole 是否需要角色
     * @return DuobangResponse<OrgWrapper>
     */
    @GET("api/account/v1/org/{orgId}/user-group")
    Observable<DuobangResponse<OrgWrapper>> getOrgGroupUserWrapper(@Path("orgId") String orgId, @Query("hasRole") boolean hasRole);

    /**
     * 获取部门下人员
     *
     * @param groupId
     * @return
     */
    @GET("api/account/v1/group/{groupId}")
    Observable<DuobangResponse<List<User>>> getOrgGroupUsers(@Path("groupId") String groupId);

    /**
     * 切换主组织
     *
     * @param orgId
     * @return
     */
    @PUT("/api/account/v1/org/my/home/org/{orgId}")
    Observable<DuobangResponse<OrganizationInfo>> updateHomeOrg(@Path("orgId") String orgId);

}
