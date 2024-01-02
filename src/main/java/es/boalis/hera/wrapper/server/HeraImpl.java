package es.boalis.hera.wrapper.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HeraImpl {
    private int port;
    private String addr;
    private boolean secure;
    private String[]  args;
    private StorageDao dao;
    private HeraImpl(){

    }
    public static HeraImpl build(){
       return new HeraImpl();

    }
    public HeraImpl withPort(int port){
        this.port = port;
        return this;
    }
    public HeraImpl withAddress(String address){
        this.addr = address;
        return this;
    }
    public HeraImpl withSecure(boolean secure){
        this.secure = secure;
        return this;
    }
    public HeraImpl withParms(String ... args){
        this.args = args;
        return this;
    }

    public HeraImpl withDao(StorageDao dao) {
        this.dao = dao;
        return this;
    }

    public HeraImpl buildServer()throws Exception{
        var server = this.buildServer(this.addr,this.port,this.secure,this.args);
        this.listen(server);
        return this;
    }
   private ServerSocket buildServer(String address,int port,boolean secure,String ... params)throws Exception{
       ServerSocket serverSocket = new ServerSocket(port);
       return serverSocket;
   }

   private void listen (ServerSocket server)throws Exception{
       ExecutorService threadPool = Executors.newFixedThreadPool(120);
       while(true){
           Socket socket = server.accept();
           var worker = new Worker(socket, dao);
           threadPool.submit(worker);
       }

   }
}
