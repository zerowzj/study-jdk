package study.io.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class SocketServer {

    private static final int PORT = 8800;

    private static ServerSocket server;

    public static void main(String[] args) {
        try {
            server = new ServerSocket(PORT);
            Socket socket = server.accept();

            log.info("fsafsdafsdafsafd");
            InputStream is = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String msg = reader.readLine();
            log.info("===> {}", msg);
            while (true) {
                OutputStream os = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(os);
                writer.write(String.format("get client msg: {}", msg));
                writer.flush();

                writer.close();
                os.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
