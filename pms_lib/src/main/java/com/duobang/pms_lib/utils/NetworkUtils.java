package com.duobang.pms_lib.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.RequiresPermission;

public class NetworkUtils {
    private static final String TAG = "NetworkUtils";

    private NetworkUtils() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " cannot be instantiated");
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Network network = connectivityManager.getActiveNetwork();
                activeNetworkInfo = connectivityManager.getNetworkInfo(network);
            } else {
                activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            }
        }

        return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Network network = connectivityManager.getActiveNetwork();
                activeNetworkInfo = connectivityManager.getNetworkInfo(network);
            } else {
                activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            }
        }

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private static boolean isNetworkAvailable(Context context, int type) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (Network network : connectivityManager.getAllNetworks()) {

                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
                if (networkInfo != null && networkInfo.getType() == type && networkInfo.isAvailable()) {
                    return true;
                }
            }
        } else {
            for (NetworkInfo networkInfo : connectivityManager.getAllNetworkInfo()) {
                if (networkInfo != null && networkInfo.getType() == type && networkInfo.isAvailable()) {
                    return true;
                }
            }
        }

        return false;
    }


    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private static boolean isNetworkConnected(Context context, int type) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (Network network : connectivityManager.getAllNetworks()) {

                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
                if (networkInfo != null && networkInfo.getType() == type && networkInfo.isConnected()) {
                    return true;
                }
            }
        } else {
            for (NetworkInfo networkInfo : connectivityManager.getAllNetworkInfo()) {
                if (networkInfo != null && networkInfo.getType() == type && networkInfo.isConnected()) {
                    return true;
                }
            }
        }

        return false;
    }


    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isWifiAvailable(Context context) {

        return isNetworkAvailable(context, ConnectivityManager.TYPE_WIFI);
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isWifiConnected(Context context) {

        return isNetworkConnected(context, ConnectivityManager.TYPE_WIFI);
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isMobileAvailable(Context context) {

        return isNetworkAvailable(context, ConnectivityManager.TYPE_MOBILE);
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isMobileConnected(Context context) {

        return isNetworkConnected(context, ConnectivityManager.TYPE_MOBILE);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isVpnAvailable(Context context) {

        return isNetworkAvailable(context, ConnectivityManager.TYPE_VPN);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isVpnConnected(Context context) {

        return isNetworkConnected(context, ConnectivityManager.TYPE_VPN);
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isBluetoothAvailable(Context context) {

        return isNetworkAvailable(context, ConnectivityManager.TYPE_BLUETOOTH);
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isBluetoothConnected(Context context) {

        return isNetworkConnected(context, ConnectivityManager.TYPE_BLUETOOTH);
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static int getNetworkType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return -1;
        }

        NetworkInfo activeNetworkInfo;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            activeNetworkInfo = connectivityManager.getNetworkInfo(network);
        } else {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }

        if (activeNetworkInfo != null) {
            return activeNetworkInfo.getType();
        }

        return -1;
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static String getNetworkTypeName(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return "UNKNOWN";
        }

        NetworkInfo activeNetworkInfo;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            activeNetworkInfo = connectivityManager.getNetworkInfo(network);
        } else {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }

        if (activeNetworkInfo != null) {
            return activeNetworkInfo.getTypeName();
        }

        return "UNKNOWN";
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static int getNetworkSubtype(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return -1;
        }

        NetworkInfo activeNetworkInfo;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            activeNetworkInfo = connectivityManager.getNetworkInfo(network);
        } else {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }

        if (activeNetworkInfo != null) {
            return activeNetworkInfo.getSubtype();
        }

        return -1;
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static String getNetworkSubtypeName(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return "UNKNOWN";
        }

        NetworkInfo activeNetworkInfo;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            activeNetworkInfo = connectivityManager.getNetworkInfo(network);
        } else {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }

        if (activeNetworkInfo != null) {
            return activeNetworkInfo.getSubtypeName();
        }

        return "UNKNOWN";
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static NetworkInfo.State getNetworkState(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return NetworkInfo.State.UNKNOWN;
        }

        NetworkInfo activeNetworkInfo;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            activeNetworkInfo = connectivityManager.getNetworkInfo(network);
        } else {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }

        if (activeNetworkInfo != null) {
            return activeNetworkInfo.getState();
        }

        return NetworkInfo.State.UNKNOWN;
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static String getNetworkExtraInfo(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return "UNKNOWN";
        }

        NetworkInfo activeNetworkInfo;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            activeNetworkInfo = connectivityManager.getNetworkInfo(network);
        } else {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }

        if (activeNetworkInfo != null) {
            return activeNetworkInfo.getExtraInfo();
        }

        return "UNKNOWN";
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return null;
        }

        NetworkInfo activeNetworkInfo = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            activeNetworkInfo = connectivityManager.getNetworkInfo(network);
        } else {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }

        return activeNetworkInfo;
    }
}
