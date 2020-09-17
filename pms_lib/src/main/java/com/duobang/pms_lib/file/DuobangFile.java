package com.duobang.pms_lib.file;

import java.util.Date;

public class DuobangFile {

    /**
     * createAt : 2020-09-02T10:14:09.606Z
     * name : output.json
     * ossPath : /flat/c5afab2d2cbe6a488c0f0de55604a3b8/output.json
     * size : 238
     * type : 1
     * subType : 3
     * userId : fb583d9a-a88f-46c2-b15d-9d13ed2a103a
     * id : 4af13361-8bcd-4fa1-9bd5-b09355a30705
     */

    private Date createAt;
    private String name;
    private String ossPath;
    private int size;
    private int type;
    private int subType;
    private String userId;
    private String id;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOssPath() {
        return ossPath;
    }

    public void setOssPath(String ossPath) {
        this.ossPath = ossPath;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSubType() {
        return subType;
    }

    public void setSubType(int subType) {
        this.subType = subType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
