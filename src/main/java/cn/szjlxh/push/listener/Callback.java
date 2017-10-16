package cn.szjlxh.push.listener;

import cn.szjlxh.push.message.ResponseMsg;

public interface Callback {

    /**推送成功时调用*/
    public void onSuccess(ResponseMsg reponseMsg);
    /**推送失败时调用*/
    public void onError(ResponseMsg reponseMsg);

}
