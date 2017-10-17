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

    private static ConcurrentHashMap<Integer, PublishListener> publishListenerConcurrentHashMap = new ConcurrentHashMap<Integer, PublishListener>();

    public static void addPublishList(PublishListener publishListener) {
        publishListenerConcurrentHashMap.put(publishListener.getId(),publishListener);
    }

    public static Callback getCallBack(int i) {
        PublishListener publishListener = publishListenerConcurrentHashMap.get(i);
        if(publishListener != null){
            publishListener.canelTimeOut();
            return publishListener.getCallback();
        }
        return null;
    }

    public static void removeCallBack(int i) {
        publishListenerConcurrentHashMap.remove(i);
    }

}
