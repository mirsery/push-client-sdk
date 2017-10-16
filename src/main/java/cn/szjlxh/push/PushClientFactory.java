package cn.szjlxh.push;

public class PushClientFactory {

    private static PushClient client = null;


    private PushClientFactory() {

    }

    /**
     * 加锁保证多线程的安全
     */
    synchronized public static PushClient getInstanceSynchronized(String url, int port, long timeOut) {
        if (client == null) {
            client = new PushClient(url, port, timeOut);
        }
        return client;
    }

}
