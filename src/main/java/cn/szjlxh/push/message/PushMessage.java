package cn.szjlxh.push.message;


public class PushMessage {
    private String type;
    private Object data;

    public PushMessage(String type, Object data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PushMessage{" +
                "type='" + type + '\'' +
                ", data=" + data +
                '}';
    }
}

