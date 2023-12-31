package es.boalis.hera.wrapper.server;

public class Server {
    public static void main(String ...args) throws Exception {
        var hera = HeraImpl.build().withPort(8080)
                .withAddress("127.0.0.1")
                .buildServer();

    }
}
