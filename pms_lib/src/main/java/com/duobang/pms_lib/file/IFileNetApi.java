package com.duobang.pms_lib.file;

import com.duobang.pms_lib.core.network.DuobangResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IFileNetApi {

    /**
     * 图片批量上传
     *
     * @param body
     * @return List<String>
     */
    @POST("api/file/image/list")
    Observable<DuobangResponse<List<String>>> uploadPhotoList(@Body RequestBody body);

    /**
     * 单个图片上传
     *
     * @param body
     * @return String
     */
    @POST("api/file/image")
    Observable<DuobangResponse<String>> uploadPhoto(@Body RequestBody body);

    /**
     * 文件批量上传
     *
     * @param body
     * @return List<String>
     */
    @POST("api/file/flat/list")
    Observable<DuobangResponse<List<DuobangFile>>> uploadFileList(@Body RequestBody body);

    /**
     * 单个文件上传
     *
     * @param body
     * @return String
     */
    @POST("api/file/flat")
    Observable<DuobangResponse<DuobangFile>> uploadFile(@Body RequestBody body);


}
