package es.boalis.hera.wrapper.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Worker implements Runnable{
    private final Socket socket;

    public Worker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream in = this.socket.getInputStream();
            var parser = new Parser(in);
            while(true) {
                var beanParser = parser.parseInput();
                if (beanParser == null){
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }
}
