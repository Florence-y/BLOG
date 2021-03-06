package controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author DELL
 */
@ServerEndpoint("/WebSocket/{username}/{toSomeOne}")
public class ChatServer {
    private static Map<String,ChatServer> clients = new ConcurrentHashMap<>();
    String name;
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     *
     */
    private Session session;

    /**
     * 连接建立成功调用的方法
     *
     * @param session
     *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam(value="username") String userName, Session session) {
        name=userName;
        System.out.println(userName);
        this.session = session;
        //加入map中
        clients.put(userName, this);
        System.out.println(userName+"连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam(value="username") String userName) {
        clients.remove(userName);
        System.out.println(userName+"关闭连接！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     *            客户端发送过来的消息
     * @param session
     *            可选的参数
     */
    @OnMessage
    public void onMessage(@PathParam(value="username") String userId, @PathParam(value = "toSomeOne") String someone,String message, Session session) {
        System.out.println("来自客户端"+userId+"发送给"+someone+"的消息:" + message);
        //群发消息
        for (Map.Entry<String,ChatServer> entry : clients.entrySet()) {
            //没找到合适的
            if (!entry.getKey().equals(someone)){
                continue;
            }
            ChatServer item = entry.getValue();
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(@PathParam(value="userId") String userName, Session session, Throwable error) {
        System.out.println(userName+"发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
//        this.session.getBasicRemote().sendText(message);
         this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return clients.size();
    }
}
