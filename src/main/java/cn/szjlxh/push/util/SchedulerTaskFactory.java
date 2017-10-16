package cn.szjlxh.push.util;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

/**
 * Created by mirsery on 23/03/2017.
 */
public class SchedulerTaskFactory {

    private static HashedWheelTimer instance;

    private static synchronized HashedWheelTimer getInstance() {
        if (instance == null) {
            instance = new HashedWheelTimer();
        }
        return instance;
    }

    public static Timeout processTask(TimerTask task, long delay, TimeUnit timeUnit) {
        return getInstance().newTimeout(task,delay,timeUnit);
    }

}
