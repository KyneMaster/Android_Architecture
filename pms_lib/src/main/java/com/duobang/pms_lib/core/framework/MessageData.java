package com.duobang.pms_lib.core.framework;

public class MessageData {

    private Object obj;
    private int what;
    private int arg1;
    private int arg2;

    public MessageData() {
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {

        this.obj = obj;
    }

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public int getArg1() {
        return arg1;
    }

    public void setArg1(int arg1) {
        this.arg1 = arg1;
    }

    public int getArg2() {
        return arg2;
    }

    public void setArg2(int arg2) {
        this.arg2 = arg2;
    }

}
