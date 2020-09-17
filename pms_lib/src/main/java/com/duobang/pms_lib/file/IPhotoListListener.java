package com.duobang.pms_lib.file;

import java.util.List;

public interface IPhotoListListener {

    void onUploadListOk(List<String> ossPaths);

    void onFailure(String message);
}
