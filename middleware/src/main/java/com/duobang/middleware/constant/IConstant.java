package com.duobang.middleware.constant;

public interface IConstant {

    /**
     * 评论
     */
    String COMMENT = "commentJson";

    interface PHOTO {
        /* 展示 */ int SHOW = 0;
        /* 编辑 */ int EDIT = 1;
    }

    interface FILE {
        /* 展示 */ int SHOW = 0;
        /* 编辑 */ int EDIT = 1;

        /* 文档 */ int WORD = 1;
        /* pdf */ int PDF = 2;
        /* 表格 */ int EXCEL = 3;
        /* 文本 */ int TXT = 4;
        /* 其他 */ int OTHER = 5;
    }
}
