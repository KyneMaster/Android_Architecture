package com.duobang.middleware.constant;

public interface IPmsConstant {

    /**
     * 组织状态
     */
    interface ORG_STATE {
        /* 可用 */ int AVAILABLE = 0;
        /* 归档 */ int FINISHED = 1;
        /* 禁用 */ int DISABLE = 2;
        /* 已删除 */ int DELETED = 3;
    }

    /**
     * 组织类型
     */
    interface ORG_TYPE {
        /* 公司 —> 项目 */ int COMPANY = 0;
        /* 项目 -> 工程 */ int PROJECT = 1;
    }

    /**
     * 查询页类型
     */
    interface SEARCH_TYPE {
        String KEY = "searchType";
        /* 工程 */ int STRUCTURE = 0;
        /* 联系人 */ int CONTACTS = 1;
    }

    /**
     * 单位工程
     */
    interface STRUCTURE {
        String ID = "structureId";
        String NAME = "structureName";
    }

    /**
     * 模型
     */
    interface MODEL {
        String ID = "modelId";
        String NAME = "modelName";
        /* 劳务队伍 */ String LABOR_TEAMS = "teams";
    }

    /**
     * 模型状态
     */
    interface MODEL_STATE {
        /* 初始状态 */ int INIT = 0;
        /* 进行中 */ int PROCESSING = 1;
        /* 已完成 */ int FINISH = 2;
    }

    /**
     * 记录
     */
    interface RECORD {
        /* 记录模版ID */ String TEMPLATE_ID = "templateId";
        /* 记录模版名称 */ String TEMPLATE_NAME = "templateName";
    }

    /**
     * 记录组件配置类型
     */
    interface FIELD_TYPE {
        /* 长文本 */ String TEXT = "text";
        /* 短文本 */ String STRING = "string";
        /* 图片 */ String IMAGE = "image";
        /* 文件 */ String FILE = "file";
        /* 日期 */ String DATE = "date";
        /* 时间 */ String TIME = "time";
        /* 日期+时间 */ String DATETIME = "datatime";
        /* 数字 */ String NUMBER = "number";
        /* 进度（工程量） */ String METAQ = "metaQ";
        /* 材料 */ String MATERIAL = "material";
        /* 工序 */ String PROCEDURE = "procedure";
        /* 多劳务队伍 */ String LABORS = "laborTeams";
        /* 劳务队伍 */ String LABOR = "laborTeam";
    }

    /**
     * 统计
     */
    interface STAT_DIM {
        String KEY = "key";
        /* 工程量 */ String QUANTITY = "Quantity";
        /* 产值 */ String OUTPUT = "Output";
        /* 材料 */ String MATERIAL = "Material";
        /* 劳务 */ String LABOR = "Labor";
        /* 全部 */ String ALL = "All";
    }

    /**
     * 总览
     */
    interface DASHBOARD{
        /* 重新加载 */ int RELOAD_TYPE = 0;
        /* 继续加载 */ int ADDLOAD_TYPE = 1;
    }
}
