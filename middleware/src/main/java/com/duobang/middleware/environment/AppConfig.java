package com.duobang.middleware.environment;

import android.os.Environment;

import com.duobang.pms_lib.environment.DebugEnv;

import java.io.File;

public class AppConfig extends DebugEnv {

    public static final String CONTRACT_URL = "https://pms.duobangbox.com/eula";

    public static final String APPLICATION_ID = "com.duobang.pms";

    public static final String PROVIDER_AUTHORITY = APPLICATION_ID + ".file_provider";

    public static final String pathDir = Environment.getExternalStorageDirectory().getPath();

    public final static String UPDATE_APP_KEY = "5eead6c623389f4934e55983";

    public final static String UPDATE_APP_TOKEN = "f8c95b29f1fbeae16ca0ec89a937425b";

    /**
     * 
     *
     * @return
     */
    public static String getPathDir() {
        File file = new File(pathDir + "/pms");
        if (!file.exists()) {
            if (!file.mkdir()) {
                return pathDir;
            }
        }
        return file.getPath();
    }
}
