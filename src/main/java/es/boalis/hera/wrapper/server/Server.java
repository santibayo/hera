package es.boalis.hera.wrapper.server;

import es.boalis.hera.wrapper.server.daos.ConsoleStorageDao;
import es.boalis.hera.wrapper.server.daos.FileStorageDao;

public class Server {
    public static void main(String ...args) throws Exception {
        var dao =new  FileStorageDao();
        dao.setCount(5);
        dao.setFileName("/Users/santi/IdeaProjects/Hera/sample.json");
        dao.start();
        var hera = HeraImpl.build().withPort(8080)
                .withAddress("127.0.0.1")
                .withDao(dao)
                .buildServer();


    }
}
