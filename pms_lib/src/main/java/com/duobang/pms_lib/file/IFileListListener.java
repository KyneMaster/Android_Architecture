package com.duobang.pms_lib.file;

import java.util.List;

public interface IFileListListener {

    void onFileSuccess(List<DuobangFile> files);

    void onFailure(String message);
}
