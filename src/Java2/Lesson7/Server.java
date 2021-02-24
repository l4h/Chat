package Java2.Lesson7;

import Java2.Lesson7.authenticaation.AuthenticationService;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private final AuthenticationService authService;
    private final ServerSocket serverSocket;
    private Set<ClientHandler> activeClients = new HashSet<ClientHandler>();

    public Server(int port) {
        authService = new AuthenticationService();
        try {
            serverSocket = new ServerSocket(port);
            doAcceptConnections();
        } catch (IOException e) {
            System.out.println("Не удалось запустить сервер  или принять соединение");
            throw new RuntimeException("SWW", e);
        }
    }

    private void doAcceptConnections() throws IOException {
        while (true) {
            System.out.println(Thread.currentThread().getName());
            System.out.println("Waiting for connections...");
            Socket newClient = serverSocket.accept();
            System.out.println("Client connected " + newClient);
            new Thread(() -> {
                try {
                    new ClientHandler(this, newClient);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            })
                    .start();
        }
    }


    public void broadcast(String message, ClientHandler source) {
        for (ClientHandler client : activeClients) {
            if (!client.equals(source))
                client.sendMessage(message);
        }
    }
    public void sendPrivateMessage(String message, String source, String dest){
        for (ClientHandler client:activeClients) {
            if(client.getNickname().equals(dest)){
                client.sendMessage("pm("+source+"): "+message);
            }
        }
    }

    public boolean isLoggedIn(ClientHandler cl) {
        return activeClients.contains(cl);
    }

    public void addClient(ClientHandler clientHandler) {
        activeClients.add(clientHandler);
    }

    public AuthenticationService getAuthService() {
        return authService;
    }
}
