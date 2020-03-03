package study.io.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    private static ServerSocket server;

    public static void main(String[] args) {
        try {
            server = new ServerSocket();
            Socket socket = server.accept();

            InputStream is = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            OutputStream os = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(os);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
