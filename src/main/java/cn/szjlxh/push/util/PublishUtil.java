package cn.szjlxh.push.util;

import cn.szjlxh.push.listener.Callback;
import cn.szjlxh.push.listener.PublishListener;

import java.util.concurrent.ConcurrentHashMap;

public class PublishUtil {

    private static int msgId = 0;

    public static int getMsgId() {
        msgId = msgId + 1;

        if (msgId > 1000)
            msgId = 0;

        return msgId;
    }

    private static ConcurrentHashMap<Integer, Callback> publishListenerConcurrentHashMap = new ConcurrentHashMap<Integer, Callback>();

    public static void addPublishList(PublishListener publishListener) {
        publishListenerConcurrentHashMap.put(publishListener.getId(),publishListener.getCallback());
    }

    public static Callback getCallBack(int i) {
        return publishListenerConcurrentHashMap.get(i);
    }

    public static void removeCallBack(int i) {
        publishListenerConcurrentHashMap.remove(i);
    }

}
