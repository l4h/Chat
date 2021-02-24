package Java2.Lesson7;

import Java2.Lesson7.authenticaation.AuthEntry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private final Server server;
    private final Socket clientSocket;
    private final DataInputStream in;
    private final DataOutputStream out;
    private String nickname;

    public ClientHandler(Server server, Socket clientSocket) throws IOException {
        this.server = server;
        this.clientSocket = clientSocket;
        in = new DataInputStream(clientSocket.getInputStream());
        out = new DataOutputStream(clientSocket.getOutputStream());
        doAuth();
        receiveMessage();
    }


    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveMessage() {
        try {
            while (true) {
                String message = in.readUTF();
                if (message.startsWith("/w ")) {
                    String[] sptlited = message.split("\\s", 3);
                    if (sptlited.length >= 3) {
                        server.sendPrivateMessage(sptlited[2], this.nickname, sptlited[1]);
                    } else {
                        sendMessage("wrong format for private message");
                    }
                } else {
                    server.broadcast(message, this);
                }
            }
        } catch (IOException e) {
            System.out.println("Can't receive message");
        }
    }

    public void doAuth() throws IOException {
        while (true) {
            sendMessage("Please auth");
            String authString = in.readUTF();
            if (authString.startsWith("-auth")) {
                String[] creds = authString.split("\\s");
                if (creds.length < 3)
                    sendMessage("auth cmd: \"-auth login password\"");
                else {
                    AuthEntry authEntry = server.getAuthService().findUserByCred(new AuthEntry(creds[1], creds[2]));
                    if (authEntry != null && !server.isLoggedIn(this)) {
                        nickname = authEntry.getNickname();
                        sendMessage("CMD: AUTH IS OK\n Welcome to chat");
                        server.broadcast(nickname + " is logged in", this);
                        server.addClient(this);
                        break;
                    } else {
                        sendMessage("User not found");
                    }
                }
            } else {
                sendMessage("auth cmd: \"-auth login password\"");
            }
        }
    }

    public String getNickname() {
        return nickname;
    }

}
