package es.boalis.hera.wrapper.server;

import java.io.InputStream;
import java.net.Socket;

public class Worker implements Runnable{
    private final Socket socket;
    private final StorageDao dao;

    public Worker(Socket socket, StorageDao dao) {
        this.socket = socket;
        this.dao = dao;
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
                // set
                dao.insert(beanParser);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }
}
