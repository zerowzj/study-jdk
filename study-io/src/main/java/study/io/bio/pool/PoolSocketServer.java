package study.io.bio.pool;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class PoolSocketServer {

    private static final int PORT = 8800;

    private static ServerSocket server;

    public void start() {
        try {
            server = new ServerSocket(PORT);
            server.accept();
        } catch (Exception ex) {
        }
    }

    public static void main(String[] args) {
        try {
            server = new ServerSocket(PORT);
            log.info("server starting at port {}", PORT);
            Socket socket = server.accept();

            log.info("get request");
            InputStream is = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String msg;
            while (!((msg = reader.readLine()) == null)) {
                log.info("===> {}", msg);
                OutputStream os = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(os);
                writer.write(String.format("get client msg: %s", msg));
                writer.flush();

                writer.close();
                os.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
