package cn.szjlxh.push.message;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class PushMsgSerializer implements JsonSerializer<PushMsg> {
    @Override
    public JsonElement serialize(PushMsg src, Type typeOfSrc, JsonSerializationContext context) {

        JsonObject object = new JsonObject();
        object.add("id", context.serialize(src.getId()));
        object.add("channelId", context.serialize(src.getChannelId()));
        object.add("type", context.serialize(src.getType()));
        object.add("data", context.serialize(src.getData()));
        return object;
    }
}
