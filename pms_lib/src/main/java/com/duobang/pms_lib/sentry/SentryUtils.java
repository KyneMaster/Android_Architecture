package com.duobang.pms_lib.sentry;

import android.content.Context;

import io.sentry.Sentry;
import io.sentry.android.AndroidSentryClientFactory;
import io.sentry.event.Event;
import io.sentry.event.EventBuilder;
import io.sentry.event.interfaces.ExceptionInterface;

public class SentryUtils {
    //初始化sentry
    /**
     * Use the Sentry DSN (client key) from the Project Settings page on Sentry
     * String sentryDsn = "https://publicKey:secretKey@host:port/1?options";
     * String sentryDsn = "http://8c4a32c102b04f929f21356d7188c7c5:40945f7672654e949f572c98bf942fb2@10.10.19.203:9000/2";
     */
    public static void init(Context context, String sentryDsn) {
        Sentry.init(sentryDsn, new AndroidSentryClientFactory(context));
    }

    //主动发送Throwable消息
    public static void sendSentryExcepiton(Throwable throwable) {
        Sentry.capture(throwable);
    }

    //主动发送Event消息
    public static void sendSentryExcepiton(Event throwable) {
        Sentry.capture(throwable);
    }

    //主动发送EventBuilder消息
    public static void sendSentryExcepiton(EventBuilder throwable) {
        Sentry.capture(throwable);
    }

    public static void sendSentryExcepiton(String logger, Throwable throwable) {
        SentryUtils.sendSentryExcepiton(new EventBuilder().withMessage("try catch msg").withLevel(Event.Level.WARNING).withLogger(logger).withSentryInterface(new ExceptionInterface(throwable)));
    }
}
