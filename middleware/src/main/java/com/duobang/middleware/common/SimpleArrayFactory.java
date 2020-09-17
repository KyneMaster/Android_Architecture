package com.duobang.middleware.common;

import java.util.ArrayList;
import java.util.List;

public class SimpleArrayFactory {

    /**
     * 创建模型状态列表
     *
     * @return
     */
    public static List<String> createModelStateList(){
        List<String> list = new ArrayList<>();
        list.add("建模中");
        list.add("进行中");
        list.add("已完成");
        return list;
    }

    /**
     * 创建会议类型列表
     *
     * @return
     */
    public static List<String> createMeetingTypeList(){
        List<String> list = new ArrayList<>();
        list.add("通用会议");
        list.add("周例会");
        list.add("月例会");
        return list;
    }

    /**
     * 创建审批请假类型列表
     *
     * @return
     */
    public static List<String> createLeaveApprovals(){
        List<String> list = new ArrayList<>();
        list.add("年假");
        list.add("事假");
        list.add("病假");
        list.add("调休");
        list.add("婚假");
        list.add("产假");
        list.add("陪产假");
        list.add("丧假");
        list.add("哺乳假");
        return list;
    }

    public static List<String> createLeaveApprovalKeys(){
        List<String> list = new ArrayList<>();
        list.add("Annual");
        list.add("Personal");
        list.add("Sick");
        list.add("Compensatory");
        list.add("Marriage");
        list.add("Maternity");
        list.add("Paternity");
        list.add("Bereavement");
        list.add("Breastfeeding");
        return list;
    }

    /**
     * 创建审批报销类型列表
     *
     * @return
     */
    public static List<String> createExpenseApprovals(){
        List<String> list = new ArrayList<>();
        list.add("差旅费");
        list.add("住宿费");
        list.add("交通费");
        list.add("招待费");
        list.add("团建费");
        list.add("通讯费");
        list.add("其他");
        return list;
    }

    public static List<String> createExpenseApprovalKeys(){
        List<String> list = new ArrayList<>();
        list.add("Travel");
        list.add("Lodging");
        list.add("Traffic");
        list.add("Entertain");
        list.add("League");
        list.add("Communication");
        list.add("Other");
        return list;
    }

    public static List<String> createReportLabels(){
        List<String> list = new ArrayList<>();
        list.add("日报");
        list.add("周报");
        list.add("月报");
        list.add("年报");
        return list;
    }
}

