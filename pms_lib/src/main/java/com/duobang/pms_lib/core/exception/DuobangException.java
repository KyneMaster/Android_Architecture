package com.duobang.pms_lib.core.exception;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

public class DuobangException extends Exception implements Parcelable {
    public static final Creator<DuobangException> CREATOR = new Creator<DuobangException>() {
        @Override
        public DuobangException createFromParcel(Parcel source) {
            return new DuobangException(source);
        }

        @Override
        public DuobangException[] newArray(int size) {
            return new DuobangException[size];
        }
    };
    private static final long serialVersionUID = 8617797484724096031L;
    private int code;
    private String message;

    public DuobangException(int code) {
        this(code, null);
    }

    public DuobangException(int code, String message) {
        super();
        this.code = code;
        this.message = message;
        generateMessage();
    }

    protected DuobangException(Parcel in) {
        readFromParcel(in);
    }

    private void generateMessage() {
        message = DuobangExceptionMessage.getMessage(code, message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public boolean isExceptionCauseByApp(Context context) {
        switch (code) {
            case DuobangExceptionMessage.EXCEPTION_SERVICE_FORMAT_ERROR:
            case DuobangExceptionMessage.EXCEPTION_SERVICE_NETWORK_FORMAT_ERROR:
            case DuobangExceptionMessage.MORE_EXCEPTION_SERVICE_INFO_CONTENT_NULL_ERROR:
                Toast.makeText(context, getMessage(), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
        throw new RuntimeException("你不可以这么调用");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.message);
    }

    public void readFromParcel(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
    }
}
