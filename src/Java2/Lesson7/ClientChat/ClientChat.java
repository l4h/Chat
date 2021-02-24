package Java2.Lesson7.ClientChat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientChat {
    private final Socket socket;
    private final DataOutputStream out;
    private final DataInputStream in;
    private final Scanner sc;

    public ClientChat() {

        try {
            socket = new Socket("localhost", 3322);
            this.out = new DataOutputStream(socket.getOutputStream());
            this.in = new DataInputStream(socket.getInputStream());
            sc  = new Scanner(System.in);
            new Thread(() -> readMessages()).start();

            while(true){
                out.writeUTF(sc.nextLine());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void readMessages() {
        try {
            while (true)
                System.out.println(in.readUTF());
        } catch (IOException e) {
            throw new RuntimeException("SWW", e);
        }
    }
}
