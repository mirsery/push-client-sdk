package cn.szjlxh.push.message;

public class PushMsg {
    private String channelId;
    private int type = 2;
    private PushMessage data;
    private int id;

    public PushMsg(String channelId, PushMessage data) {
        this.channelId = channelId;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public PushMessage getData() {
        return data;
    }

    public void setData(PushMessage data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}