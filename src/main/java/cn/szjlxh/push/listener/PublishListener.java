package cn.szjlxh.push.listener;


import cn.szjlxh.push.message.ResponseMsg;
import cn.szjlxh.push.util.PublishUtil;
import cn.szjlxh.push.util.SchedulerTaskFactory;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

public class PublishListener {

    private Callback callback;
    private int id;
    private Timeout timeout;

    public PublishListener(final Callback callback, int msgId, long timeOut) {

        this.callback = callback;
        this.id = msgId;

        this.timeout = SchedulerTaskFactory.processTask(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                Callback timeOutCall = getCallback();
                ResponseMsg responseMsg = new ResponseMsg();
                responseMsg.setId(id);
                responseMsg.setMsg("请求超时");
                responseMsg.setStatus("408");
                timeOutCall.onError(responseMsg);
                PublishUtil.removeCallBack(id);
            }
        }, timeOut, TimeUnit.SECONDS);

    }

    public boolean canelTimeOut(){

        return timeout.cancel();
    }

    public Callback getCallback() {
        return callback;
    }

    public int getId() {
        return id;
    }
}
