package cn.szjlxh.push.message;

public class PushMsg {
    private String channelId;
    private String data;
    private int id;

    public PushMsg(String channelId, String data) {
        this.channelId = channelId;
        this.data = data;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{id:\"" + id + "\",channelId:\"" + channelId + "\",data:\"" + data + "\"}";
    }
}
